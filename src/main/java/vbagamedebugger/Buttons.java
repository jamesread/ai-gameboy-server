package vbagamedebugger;

import java.util.Random;

public enum Buttons {
	A(0x0001), B(0x0002), RIGHT(0x0010), LEFT(0x0020), UP(0x0040), DOWN(0x0080);

	public static Buttons random() {
		return randomFrom(Buttons.values());
	}

	public static Buttons randomDirection() {
		return randomFrom(Buttons.UP, Buttons.DOWN, Buttons.LEFT, Buttons.RIGHT);
	}

	private static Buttons randomFrom(Buttons... buttons) {
		Random rnd = new Random();
		return buttons[rnd.nextInt(buttons.length)];

	}

	public final int mask;

	private Buttons(int mask) {
		this.mask = mask;
	}
}
