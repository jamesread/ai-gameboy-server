package vbagamedebugger;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

public class WindowDebug extends JFrame {
	private final JButton btnMemSnapshot = new JButton("snapshot");
	private final JButton btnNewWatch = new JButton("Watch...");

	private final JButton btnShowTileset = new JButton("Tileset");

	private final GbaController gbac = new GbaController();

	private final JButton btnExit = new JButton("Exit");

	private final JButton btnMemCmp = new JButton("cmp");

	public WindowDebug() {
		this.pack();
		this.setTitle("Debug");
		this.setLocationRelativeTo(null);

		this.setupComponents();
	}

	private void setupComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, 1, new Insets(6, 6, 6, 6), 0, 0);
		gbc.gridwidth = 1;

		this.add(this.btnMemSnapshot, gbc);
		this.btnMemSnapshot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WindowDebug.this.gbac.memSnapshot();
			}
		});

		gbc.gridx++;
		this.add(this.btnMemCmp, gbc);
		this.btnMemCmp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WindowDebug.this.gbac.memCmp();
			}
		});

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy++;
		this.add(this.btnNewWatch, gbc);
		this.btnNewWatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.newWatchDialog(Main.promptInt("start?"), Main.promptInt("stop?"));
			}
		});

		gbc.gridy++;
		this.add(new XButton("Watch prefix strings", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowWatch(0x1823, 0x184a, "prefix strings");
			}
		}), gbc);

		gbc.gridy++;
		this.add(new XButton("Watch for items", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowWatch(0x45b7, 0x491e, "Watch for items");
			}
		}), gbc);

		gbc.gridy++;
		this.add(new XButton("Watch text", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowWatch(0x9dc1, 0x9dc1 + 90, "Watch text");
			}
		}), gbc);

		gbc.gridy++;
		this.add(new XButton("New Bot", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowBotWatcher();
			}
		}), gbc);

		gbc.gridy++;
		this.btnShowTileset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new WindowTilesetGraphics(new TilesetBitmap());
			}
		});
		this.add(this.btnShowTileset, gbc);

		gbc.gridy++;
		gbc.gridwidth = 1;
		this.add(new XButton("up", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.press(Buttons.UP);
			}

		}), gbc);

		gbc.gridx++;
		this.add(new XButton("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.press(Buttons.DOWN);
			}
		}), gbc);

		gbc.gridx++;
		this.add(new XButton("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.press(Buttons.LEFT);
			}
		}), gbc);

		gbc.gridx++;
		this.add(new XButton("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.press(Buttons.RIGHT);
			}
		}), gbc);

		gbc.gridx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;

		gbc.gridy++;
		this.btnExit.setBackground(Color.RED);
		this.btnExit.setForeground(Color.WHITE);
		this.add(this.btnExit, gbc);
		this.btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.shutdown();
			}
		});
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);

		if (!b) {
			Main.shutdown();
		} else {
			this.pack();
		}
	}

}
