package vbagamedebugger;

import java.io.IOException;
import java.util.HashMap;

import vbagamedebugger.gbio.GbRomReader;

public class Tileset {
	public HashMap<Integer, TileBitmap> tiles = new HashMap<Integer, TileBitmap>();
	private final int pointerToCollisionData;
	private final int pointerToTileGfx;
	private final int pointerToBlocks;
	private final int id;
	private final int bank;

	public Tileset(int id, int bank, int pointerToBlocks, int pointerToTileGfx, int pointerToCollisionData) {
		this.id = id;
		this.bank = bank;
		this.pointerToBlocks = pointerToBlocks;
		this.pointerToTileGfx = pointerToTileGfx;
		this.pointerToCollisionData = pointerToCollisionData;
	}

	public void loadBitmaps(GbRomReader reader) throws IOException {
		int base = (((this.bank - 1) * 0x4000)) + this.pointerToTileGfx;

		System.out.println(String.format("Loading GFX for tileset " + this.id + " from %x", base));

		for (int i = 0; i < 16; i++) {

		}
	}

	public void loadBlocksets() {
		int base = (((this.bank - 1) * 0x4000)) + this.pointerToBlocks;

		System.out.println(String.format("Loading BLK for tileset " + this.id + " from %x", base));
	}
}
