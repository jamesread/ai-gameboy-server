package vbagamedebugger;

import java.awt.Color;
import java.util.HashMap;

public class TilesetDatabase {
	private final HashMap<Integer, Color> tilesetColors = new HashMap<Integer, Color>();

	private int max;

	private Color generateColor(int base) {
		if (base > this.max) {
			this.max = base;
			System.out.println("max: " + this.max);
		}

		return new Color(Main.rnd.nextInt(255), Main.rnd.nextInt(255), Main.rnd.nextInt(255));
	}

	public Color getColor(int tileset) {
		if (!this.tilesetColors.containsKey(tileset)) {
			this.tilesetColors.put(tileset, this.generateColor(tileset));
		}

		return this.tilesetColors.get(tileset);
	}
}
