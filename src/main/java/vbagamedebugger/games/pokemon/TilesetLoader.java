package vbagamedebugger.games.pokemon;

import vbagamedebugger.TileBitmap;

public class TilesetLoader {
	public TilesetLoader() {
	}

	public TileBitmap load() {
		TileBitmap bitmap = new TileBitmap();

		for (int i = 0; i < 80; i++) {
			bitmap.renderTile(0x9000 + (i * 0xf));
		}

		return bitmap;
	}
}
