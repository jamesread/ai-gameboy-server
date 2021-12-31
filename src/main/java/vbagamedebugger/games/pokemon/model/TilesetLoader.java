package vbagamedebugger.games.pokemon.model;

import java.io.IOException;

import vbagamedebugger.gbio.GbRomReader;
import vbagamedebugger.TileBitmap;
import vbagamedebugger.Tileset;
import vbagamedebugger.TilesetDatabase;

public class TilesetLoader {
	private final GbRomReader reader;

	public TilesetLoader(GbRomReader reader) throws Exception {
		this.reader = reader;
	}

	public TilesetDatabase load() throws IOException {
		TilesetDatabase tsdb = new TilesetDatabase();

		int addr = 0xc7be;
		this.reader.seek(addr);

		for (int i = 0; i <= 23; i++) {
			int bank = this.reader.readByte();

			int pointerToBlocks = this.reader.readPointer();
			int pointerToTileGfx = this.reader.readPointer();
			int pointerToCollisionData = this.reader.readPointer();

			this.reader.skipBytes(5);

			Tileset t = new Tileset(i, bank, pointerToBlocks, pointerToTileGfx, pointerToCollisionData);
			t.loadBitmaps(this.reader);
			t.loadBlocksets();

			tsdb.tilesets.put(i, t);
		}

		return tsdb;
	}

	public TileBitmap loadTileBitmap() {
		TileBitmap tileBitmap = new TileBitmap();

		for (int i = 0; i < 80; i++) {
			tileBitmap.renderTile(0x9000 + (i * 0xf));
		}

		return tileBitmap;
	}
}
