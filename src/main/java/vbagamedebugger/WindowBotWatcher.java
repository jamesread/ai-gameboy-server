package vbagamedebugger;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import vbagamedebugger.games.pokemon.Bot;

public class WindowBotWatcher extends JFrame implements Runnable {
	private final Bot bot;

	private final JTextArea txtBot = new JTextArea();
	private final JTextArea txtGameState = new JTextArea();

	private final JCheckBox chkIdle = new JCheckBox("Idle?");

	public WindowBotWatcher() {
		this.bot = new Bot(Main.gameState);

		this.setLocationRelativeTo(null);
		this.setTitle("Bot watcher");
		this.setSize(640, 480);

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;

		this.txtBot.setEditable(false);
		this.txtBot.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		this.add(new JScrollPane(this.txtBot), gbc);

		gbc.gridy++;
		this.txtGameState.setEditable(false);
		this.txtGameState.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		this.add(new JScrollPane(this.txtGameState), gbc);

		gbc.gridy++;
		gbc.weighty = 0;
		this.add(this.chkIdle, gbc);
		this.chkIdle.setSelected(true);
		WindowBotWatcher.this.bot.setIdle(true);
		this.chkIdle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WindowBotWatcher.this.bot.setIdle(WindowBotWatcher.this.chkIdle.isSelected());
			}
		});

		this.setVisible(true);

		new Thread(this, "windowBotWatcher").start();
	}

	@Override
	public void run() {
		while (this.isVisible()) {
			this.txtBot.setText(this.bot.toString());
			this.txtGameState.setText(Main.gameState.toString());
			Util.sleep(200);
		}
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);

		if (!b) {
			this.bot.stop();
		}
	}
}
