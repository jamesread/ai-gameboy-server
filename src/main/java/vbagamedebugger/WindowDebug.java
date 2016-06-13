package vbagamedebugger;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import vbagamedebugger.DebugPresetModel.DebugPreset;

public class WindowDebug extends JFrame {
	private final JButton btnMemSnapshot = new JButton("snapshot");
	private final JButton btnNewWatch = new JButton("Custom watch...");

	private final JButton btnShowTileset = new JButton("Tileset");

	private final JButton btnExit = new JButton("Exit");

	private final JButton btnMap = new JButton("Map");

	private final JButton btnMemCmp = new JButton("cmp");
	private final JButton btnMemDumpFull = new JButton("dmp full");
	private final JButton btnMemDumpDiff = new JButton("dmp diff");

	private final JPanel panMemoryControls = new JPanel();
	private final JPanel panControls = new JPanel();

	private final JList<DebugPresetModel> lstDebugPresets = new JList<DebugPresetModel>(new DebugPresetModel());

	public WindowDebug() {
		this.pack();
		this.setTitle("Debug");
		this.setLocationRelativeTo(null);

		this.setupComponents();
	}

	private void setupComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, 1, new Insets(6, 6, 6, 6), 0, 0);
		gbc.gridx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;

		this.panMemoryControls.setBorder(BorderFactory.createTitledBorder("Memory"));

		this.panMemoryControls.add(this.btnMemSnapshot);
		this.btnMemSnapshot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.gbac.memSnapshot();
			}
		});

		this.panMemoryControls.add(this.btnMemCmp);
		this.btnMemCmp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.gbac.memCmp();
			}
		});

		this.btnMemDumpFull.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int dump[] = Main.gbac.memDump();

				try {
					File f = new File("mem.dmp");
					FileWriter fw = new FileWriter(f);

					for (int bite : dump) {
						fw.write(bite);
					}

					fw.close();

					JOptionPane.showMessageDialog(null, "dumped to file: " + f.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		this.panMemoryControls.add(this.btnMemDumpFull);
		
		this.btnMemDumpDiff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int dump[] = Main.gbac.getMemCmp();

				try {
					File f = new File("mem.dmp");
					FileWriter fw = new FileWriter(f);

					for (int bite : dump) {
						fw.write(bite);
					}

					fw.close();

					JOptionPane.showMessageDialog(null, "dumped to file: " + f.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		});
		this.panMemoryControls.add(this.btnMemDumpDiff);

		this.add(this.panMemoryControls, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy++;
		this.add(new JLabel("Watches"), gbc);
		
		gbc.gridy++;
		this.add(new JScrollPane(this.lstDebugPresets), gbc);
		
		gbc.gridwidth = 1; 
		gbc.gridy++;
		add(new XButton("Load preset", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = lstDebugPresets.getSelectedIndex();
				DebugPreset preset = ((DebugPresetModel)lstDebugPresets.getModel()).get(selected);
				
				new WindowWatch(preset.offset, preset.end, preset.name); 
			}  
		}), gbc); 
		 
		gbc.gridx++;
		this.add(this.btnNewWatch, gbc);
		this.btnNewWatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.newWatchDialog(Main.promptInt("start?"), Main.promptInt("stop?"));
			}
		}); 
		
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbc.gridx = 0; 
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
				new WindowTilesetGraphics(new TileBitmap());
			}
		});
		this.add(this.btnShowTileset, gbc);

		gbc.gridy++;
		this.btnMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new WindowMapView();
			}
		});
		this.add(this.btnMap, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 1;
		gbc.gridy++;
		gbc.gridwidth = 1;

		this.panControls.setBorder(BorderFactory.createTitledBorder("Controls"));
		this.add(this.panControls);

		this.panControls.add(new XButton("\u25b2", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GbaController.press(Buttons.UP);
			}

		}), gbc);

		this.panControls.add(new XButton("\u25bc", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GbaController.press(Buttons.DOWN);
			}
		}), gbc);

		this.panControls.add(new XButton("\u25c0", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GbaController.press(Buttons.LEFT);
			}
		}), gbc);

		this.panControls.add(new XButton("\u25b6", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GbaController.press(Buttons.RIGHT);
			}
		}), gbc);

		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.BOTH;
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
			this.setLocationRelativeTo(null);
		}
	}

}
