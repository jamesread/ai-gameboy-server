package vbagamedebugger.gbio;

import java.util.Random;

public enum GbButtons {
	A(0x0001), B(0x0002), RIGHT(0x0010), LEFT(0x0020), UP(0x0040), DOWN(0x0080);

	public static GbButtons random() {
		return randomFrom(GbButtons.values());
	}

	public static GbButtons randomDirection() {
		return randomFrom(GbButtons.UP, GbButtons.DOWN, GbButtons.LEFT, GbButtons.RIGHT);
	}

	private static GbButtons randomFrom(GbButtons... buttons) {
		Random rnd = new Random();
		return buttons[rnd.nextInt(buttons.length)];

	}

	public final int mask;

	private GbButtons(int mask) {
		this.mask = mask;
	}
}
