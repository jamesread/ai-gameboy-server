package vbagamedebugger;

import java.util.Arrays;

import vbagamedebugger.games.pokemon.PokeCharset;

import com.aurellem.gb.Gb;

public class GbaController {

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

	public static int dumpByte(int address) {
		return Gb.readMemory(address);
	}

	private int[] snapshot;

	private void dumpImportantStringPrefixes() {
		int baseAddress = 0x01823;
		for (int i = 0; i < 100; i++) {
			int currentAddress = baseAddress + i;

			int v = Gb.readMemory(currentAddress);

			System.out.println(currentAddress + " = " + v);
		}
	}

	public void memCmp() {
		int[] diff = new int[0xffff];
		Arrays.fill(diff, -1);

		int[] current = this.memDump();

		for (int i = 0; i < current.length; i++) {
			if (current[i] != this.snapshot[i]) {
				diff[i] = current[i];
			}
		}

		new WindowWatch(diff);
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
}
