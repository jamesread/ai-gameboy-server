package vbagamedebugger;

import java.awt.Color;
import java.util.HashMap;

import vbagamedebugger.Util;

public class TilesetDatabase {
	private final HashMap<Integer, Color> tilesetColors = new HashMap<Integer, Color>();
	public HashMap<Integer, Tileset> tilesets = new HashMap<Integer, Tileset>();

	private int max;

	private Color generateColor(int base) {
		if (base > this.max) {
			this.max = base;
			System.out.println("max: " + this.max);
		}

		return new Color(Util.rnd.nextInt(255), Util.rnd.nextInt(255), Util.rnd.nextInt(255));
	}

	public Color getColor(int tileset) {
		if (!this.tilesetColors.containsKey(tileset)) {
			this.tilesetColors.put(tileset, this.generateColor(tileset));
		}

		return this.tilesetColors.get(tileset);
	}
}
