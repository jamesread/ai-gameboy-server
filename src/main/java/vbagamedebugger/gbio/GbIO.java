package vbagamedebugger.gbio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import vbagamedebugger.games.pokemon.model.PokeCharset;

import com.aurellem.gb.Gb;

import java.util.Vector;

public class GbIO {
	interface Listener {
		public void onEmulatorStarted();
	}

	private static int keymask = 0;

	private static int[] dumpBlock(int start, int fin) {
		int[] dmp = new int[fin - start];
		for (int currentAddress = start; currentAddress < fin; currentAddress++) {
			dmp[currentAddress - start] = Gb.readMemory(currentAddress);
		}

		return dmp;
	}

	public static int dumpBlockInt2(int address) {
		int[] byteval = dumpBlock(address, address + 2);

		return (byteval[0] << 0) | (byteval[1] << 8);
	}

	public static int dumpBlockInt3(int address) {
		int[] byteval = dumpBlock(address, address + 3);

		return (byteval[0] << 0) | (byteval[1] << 8) | (byteval[2] << 16);
	}

	public static String dumpBlockString(int start, int fin) {
		String s = "";
		int[] dmp = dumpBlock(start, fin);

		for (int element : dmp) {
			CharSequence charValue = PokeCharset.charValue(element);

			if (charValue.equals("<NUL>")) {
				break;
			} else {
				s += charValue;
			}
		}

		return s;
	}

	public static void press(GbButtons... buttons) {
		for (GbButtons button : buttons) {
			System.out.println("Button:" + button);

			keymask |= button.mask;
		}

		try {
			Thread.sleep(1000);
		} catch (Exception e) {

		}
	}

	private String romPath = "";

	private int[] snapshot;

	public int[] getMemCmp() {
		int[] diff = new int[0xffff];
		Arrays.fill(diff, 0);

		int[] current = this.memDump();

		for (int i = 0; i < current.length; i++) {
			if (current[i] != this.snapshot[i]) {
				diff[i] = current[i];
			}
		}

		return diff;
	}

	public int[] memDump() {
		int[] dmp = new int[0xffff];

		System.out.println("dmp start");
		for (int currentAddress = 0x0; currentAddress < 0xffff; currentAddress++) {
			dmp[currentAddress] = Gb.readMemory(currentAddress);
		}
		System.out.println("dmp fin");

		return dmp;
	}

	public void memSnapshot() {
		this.snapshot = this.memDump();
	}

	public GbRomReader rom;
	public GbRamReader ram; 

	public void startEmulator(final String romPath) throws FileNotFoundException {
		this.romPath = romPath;

		this.rom = new GbRomReader(romPath);
		
		Thread t = new Thread() {
			@Override
			public void run() {
				Gb.startEmulator(romPath);

				System.out.println("Rom size: " + Gb.getROMSize());

				while (true) { // FIXME
					try {
						if (keymask != 0) {
							Gb.step(keymask);
							Gb.step(keymask);
							Gb.step(keymask);
							Gb.step(keymask);

							keymask = 0;

							Gb.step(keymask);
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
	}

	public void shutdown() {
		Gb.shutdown();
	}
 }
