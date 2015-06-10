package vbagamedebugger.games.pokemon;

import java.io.IOException;
import java.util.Vector;

import vbagamedebugger.RomReader;
import vbagamedebugger.RomReader.GbByte;

public class World {
	public enum Direction {
		NORTH, EAST, SOUTH, WEST
	}

	public Vector<Map> maps = new Vector<Map>();

	private final RomReader reader;

	public World(RomReader reader) {
		this.reader = reader;
	}

	public Vector<Map> getMaps() {
		return this.maps;
	}

	public void load() throws IOException {

	}

	public Map loadMap(int addr) throws IOException {
		// header
		GbByte tileset = this.reader.readGbByte(addr);
		GbByte h = this.reader.readGbByte();
		GbByte w = this.reader.readGbByte();

		// object (skip)
		addr += 2; // pointer to map
		addr += 2; // pointer to text pointers
		addr += 2; // pointer to maps script

		Vector<Direction> connections = new Vector<Direction>();
		GbByte connectionByte = this.reader.readGbByte(addr);

		if (1 == (connectionByte.value & (1 << 3))) {
			connections.add(Direction.NORTH);
		}

		if (1 == (connectionByte.value & (1 << 2))) {
			connections.add(Direction.SOUTH);
		}

		if (1 == (connectionByte.value & (1 << 1))) {
			connections.add(Direction.WEST);
		}

		if (1 == (connectionByte.value & (1 << 0))) {
			connections.add(Direction.EAST);
		}

		for (int i = 0; i < connections.size(); i++) {
			addr += 11;
		}

		addr += 84;

		System.out.println(String.format("finished reading object, I'm at: %x", addr));

		Map map = new Map(w.value * 2, h.value * 2);
		map.tileset = tileset.value;
		map.fillWithBlock(0);

		System.out.println(map);

		this.reader.seek(addr);

		for (int x = 0; x < (w.value * 2); x++) {
			for (int y = 0; y < (h.value * 2); y++) {
				GbByte bid = this.reader.readGbByte(addr);

				System.out.println(String.format("addr: %x bid: %x ", addr, bid.value));
				map.setBlock(y, x, new Block(bid.value, y, x));
				addr++;
			}
		}

		System.out.println(map);
		this.maps.add(map);

		return map;
	}
}
