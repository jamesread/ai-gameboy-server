package vbagamedebugger;

import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import vbagamedebugger.games.pokemon.red.GameState;

import com.aurellem.gb.Gb;
import com.beust.jcommander.JCommander;

public class Main {

	private static WindowDebug wndDebug = new WindowDebug();

	private static final Vector<WindowWatch> watchDialogs = new Vector<WindowWatch>();

	private static int keymask = 0;

	public static boolean run = true;

	public static Random rnd = new Random();

	public static final GameState gameState = new GameState();

	private static final Args args = new Args();

	private static int lastPromptedInt = 0;

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new JCommander(Main.args, null, args);

		System.load(System.getProperty("user.dir") + "/lib/libvba.so");
		Thread t = new Thread() {
			@Override
			public void run() {
				Gb.startEmulator(Main.args.romPath);

				System.out.println("Rom size: " + Gb.getROMSize());

				Main.onEmulatorStarted();

				while (Main.run) {
					try {
						if (Main.keymask != 0) {
							Gb.step(Main.keymask);
							Gb.step(Main.keymask);
							Gb.step(Main.keymask);
							Gb.step(Main.keymask);

							Main.keymask = 0;

							Gb.step(Main.keymask);
						} else {
							Gb.step();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		};
		t.start();

		Main.wndDebug.setVisible(true);
	}

	public static void newWatchDialog(int start, int stop) {
		Main.watchDialogs.add(new WindowWatch(start, stop));
	}

	private static void onEmulatorStarted() {
		new Thread(Main.gameState, "gameStateupdater").start();

		if (Main.args.listener) {
			new Thread(new TcpControlListener(), "tcpControlListener").start();
		}

		if (Main.args.startMapCol) {
			new WindowMapCol();
		}

		if (Main.args.startBot) {
			new WindowBotWatcher();
		}
	}

	public static void press(Buttons... buttons) {
		for (Buttons button : buttons) {
			System.out.println("Button:" + button);

			Main.keymask |= button.mask;
		}

		try {
			Thread.sleep(1000);
		} catch (Exception e) {

		}
	}

	public static int promptInt(String string) {
		String valStr = JOptionPane.showInputDialog(string);

		int valInt;
		boolean addition = false;

		if (valStr.startsWith("+")) {
			addition = true;
			valStr = valStr.replace("+", "");
		}

		if (valStr.startsWith("0x")) {
			valStr = valStr.replace("0x", "");
			valInt = Integer.parseInt(valStr, 16);
		} else {
			valInt = Integer.parseInt(valStr, 10);
		}

		if (addition) {
			valInt = Main.lastPromptedInt + valInt;
		}

		Main.lastPromptedInt = valInt;

		return valInt;
	}

	public static void shutdown() {
		System.out.println("shutdown request ");

		Main.run = false;
		Gb.shutdown();
		System.out.println("shutdown finished request");
		System.exit(0);
	}

	public static void unregisterWatchDialog(WindowWatch watchDialog) {
		Main.watchDialogs.remove(watchDialog);
	}
}
