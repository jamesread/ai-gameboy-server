package vbagamedebugger;

import java.io.IOException;
import java.util.HashMap;

import vbagamedebugger.RomReader.GbByte;
import vbagamedebugger.RomReader.GbPointer;

public class Tileset {
	public HashMap<Integer, TileBitmap> tiles = new HashMap<Integer, TileBitmap>();
	private final GbPointer pointerToCollisionData;
	private final GbPointer pointerToTileGfx;
	private final GbPointer pointerToBlocks;
	private final int id;
	private final GbByte bank;

	public Tileset(int id, GbByte bank, GbPointer pointerToBlocks, GbPointer pointerToTileGfx, GbPointer pointerToCollisionData) {
		this.id = id;
		this.bank = bank;
		this.pointerToBlocks = pointerToBlocks;
		this.pointerToTileGfx = pointerToTileGfx;
		this.pointerToCollisionData = pointerToCollisionData;
	}

	public void loadBitmaps(RomReader reader) throws IOException {
		int base = (((this.bank.value - 1) * 0x4000)) + this.pointerToTileGfx.value;

		System.out.println(String.format("Loading GFX for tileset " + this.id + " from %x", base));

		for (int i = 0; i < 16; i++) {

		}
	}

	public void loadBlocksets() {
		int base = (((this.bank.value - 1) * 0x4000)) + this.pointerToBlocks.value;

		System.out.println(String.format("Loading BLK for tileset " + this.id + " from %x", base));
	}
}
