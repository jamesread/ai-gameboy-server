package vbagamedebugger.games.pokemon;

import java.io.IOException;

import vbagamedebugger.GsonDebugger;
import vbagamedebugger.RomReader;
import vbagamedebugger.RomReader.GbByte;
import vbagamedebugger.RomReader.GbPointer;
import vbagamedebugger.TileBitmap;
import vbagamedebugger.Tileset;
import vbagamedebugger.TilesetDatabase;

public class TilesetLoader {
	private final RomReader reader;

	public TilesetLoader(RomReader reader) throws Exception {
		this.reader = reader;
	}

	public TilesetDatabase load() throws IOException {
		TilesetDatabase tsdb = new TilesetDatabase();

		int addr = 0xc7be;
		this.reader.seek(addr);

		for (int i = 0; i <= 23; i++) {
			GbByte bank = this.reader.readGbByte();

			GbPointer pointerToBlocks = this.reader.readGbPointer();
			GbPointer pointerToTileGfx = this.reader.readGbPointer();
			GbPointer pointerToCollisionData = this.reader.readGbPointer();

			this.reader.skipBytes(5);

			Tileset t = new Tileset(i, bank, pointerToBlocks, pointerToTileGfx, pointerToCollisionData);
			t.loadBitmaps(this.reader);
			t.loadBlocksets();

			System.out.println(GsonDebugger.toJson(t));

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
