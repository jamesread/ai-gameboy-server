package vbagamedebugger.games.pokemon;

import vbagamedebugger.TilesetBitmap;

public class TilesetLoader {
	public TilesetLoader() {
	}

	public TilesetBitmap load() {
		TilesetBitmap bitmap = new TilesetBitmap();

		for (int i = 0; i < 80; i++) {
			bitmap.renderTile(0x9000 + (i * 0xf));
		}

		return bitmap;
	}
}
