package vbagamedebugger;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vbagamedebugger.games.pokemon.model.PokeCharset;

import com.aurellem.gb.Gb;

public class WindowWatch extends JFrame {
	private final JTextArea txtWatch = new JTextArea();
	private final int start;
	private final int stop;
	private final JSlider sliWidth = new JSlider(1, 64);

	private final JTextArea txtOffsets = new JTextArea();

	private final JButton btnRefresh = new JButton("Refresh");
	private final JCheckBox chkAuto = new JCheckBox("Auto?", true);
	private final JLabel lblWidth = new JLabel("");

	private final JButton btnWidth1 = new JButton("w:1");
	private final JButton btnWidth16 = new JButton("w:16");

	private boolean autoupdate = false;
	private final JCheckBox chkDecodePoke = new JCheckBox("Decode poke chars?", false);

	private Thread autoUpdater;

	private int[] dmp;

	public WindowWatch(int start, int stop) {
		this.start = start;
		this.stop = stop;

		this.setupLayout();
	}

	public WindowWatch(int start, int stop, String string) {
		this(start, stop);
		this.setTitle("watch for " + string);
	}

	public WindowWatch(int[] dmp) {
		this.start = 0x0;
		this.stop = 0xffff - 1;
		this.dmp = dmp;
		this.btnRefresh.setEnabled(false);
		this.chkAuto.getModel().setSelected(false);
		this.chkAuto.setEnabled(false);

		this.setupLayout();
	}

	private void setupLayout() {
		this.setBounds(100, 100, 680, 640);
		this.setLocationRelativeTo(null);

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = Util.getNewGbc();
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.VERTICAL;

		gbc.gridx++;
		this.add(this.btnRefresh, gbc);
		this.btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WindowWatch.this.update();
			}
		});

		this.chkAuto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("chkAuto");
				if (WindowWatch.this.chkAuto.isSelected()) {
					WindowWatch.this.startAuto();
				} else {
					WindowWatch.this.stopAuto();
				}
			}
		});

		gbc.gridx++;

		this.add(this.chkAuto, gbc);

		gbc.gridx++;
		this.add(this.lblWidth, gbc);

		gbc.gridx++;
		this.add(this.sliWidth, gbc);
		this.sliWidth.setValue(1);
		this.sliWidth.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				WindowWatch.this.updateWidth();
				WindowWatch.this.update();
			}
		});

		gbc.gridx++;
		this.btnWidth1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WindowWatch.this.updateWidth(1);
			}
		});
		this.add(this.btnWidth1, gbc);

		gbc.gridx++;
		this.btnWidth16.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WindowWatch.this.updateWidth(16);
			}
		});
		this.add(this.btnWidth16, gbc);

		gbc.gridx++;
		this.add(this.chkDecodePoke, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(6, 6, 0, 6);
		gbc.weighty = 0;
		this.txtOffsets.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		this.txtOffsets.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		this.add(this.txtOffsets, gbc);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 6, 6, 6);
		gbc.gridy++;
		this.txtWatch.setEditable(false);
		this.txtWatch.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		this.add(new JScrollPane(this.txtWatch), gbc);

		this.setTitle("watch " + this.start + " to " + this.stop);

		this.sliWidth.setValue(16);
		this.updateWidth();
		this.update();

		this.setVisible(true);

		this.startAuto();
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);

		if (!b) {
			Main.unregisterWatchDialog(this);
		}
	}

	public void startAuto() {
		this.chkAuto.getModel().setSelected(true);
		this.btnRefresh.setEnabled(false);

		this.autoupdate = true;
		this.autoUpdater = new Thread() {
			@Override
			public void run() {

				while (WindowWatch.this.autoupdate) {
					WindowWatch.this.update();

					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		};
		this.autoUpdater.start();
	}

	private void stopAuto() {
		this.chkAuto.getModel().setSelected(false);
		this.btnRefresh.setEnabled(true);
		this.autoupdate = false;
	}

	public void update() {
		StringBuilder debug = new StringBuilder();

		final int width = this.sliWidth.getValue();
		int i = 0;
		for (int currentAddress = this.start; currentAddress <= this.stop; currentAddress++) {
			int value;

			if (this.dmp == null) {
				value = Gb.readMemory(currentAddress);
			} else {
				value = this.dmp[currentAddress];
			}

			if (((i % width) == 0)) {
				debug.append(String.format("\n%1$-8X | ", currentAddress));
			}

			if (value == -1) {
				debug.append("----");
			} else {
				if (width == 1) {
					debug.append(String.format("hex: 0x%1$-8X dec: 0d%1$-8s poke: %2$-8s", value, PokeCharset.charValue(value)));
				} else {
					debug.append(String.format("0x%1$-2X ", value));
				}
			}

			if ((width > 1) && this.chkDecodePoke.isSelected()) {
				debug.append(String.format("(poke: %1$s)    ", PokeCharset.charValue(value)));
			}

			i++;
		}

		this.txtWatch.setText(debug.toString().trim());
	}

	private void updateWidth() {
		this.lblWidth.setText("w: " + this.sliWidth.getValue());

		if (this.sliWidth.getValue() > 1) {
			this.chkDecodePoke.setEnabled(true);
		} else {
			this.chkDecodePoke.setEnabled(false);
		}

		StringBuilder offsetMarks = new StringBuilder();
		if (this.sliWidth.getValue() == 16) {

			for (int i = (this.start % 0x10); i < (this.sliWidth.getValue() + (this.start % 0x10)); i++) {
				offsetMarks.append(String.format(" ___%x", ((i > 0xf) ? i - 0x10 : i)));
			}
		} else if (this.sliWidth.getValue() == 1) {
			offsetMarks.append(" offsets dont work with width = 1");
		} else {
			for (int i = 0; i < this.sliWidth.getValue(); i++) {
				offsetMarks.append(String.format(" +%d  ", i));
			}
		}

		this.txtOffsets.setText("          " + offsetMarks);
	}

	private void updateWidth(int w) {
		this.sliWidth.setValue(w);
		this.updateWidth();
	}
}
