package vbagamedebugger.games.pokemon;

import java.io.IOException;
import java.util.BitSet;
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

	/**
	 * https://bulbapedia.bulbagarden.net/wiki/User:Tiddlywinks/Map_header_data_structure_in_Generation_I#Map_height_and_width
	 */
	public Map loadMap(int addr) throws IOException {
		System.out.printf("Loading map from ROM address: %x\n", addr);

		// header
		GbByte tileset = this.reader.readGbByte(addr);
		GbByte h = this.reader.readGbByte();
		GbByte w = this.reader.readGbByte();

		// object (skip)
		this.reader.skipBytes(2); // pointer to map
		this.reader.skipBytes(2); // pointer to text pointers
		this.reader.skipBytes(2); // pointer to maps script

		System.out.printf("Loading map connections from address: %x\n", this.reader.getCurrentAddress());
		Vector<Direction> connections = new Vector<Direction>();
		GbByte connectionByte = this.reader.readGbByte();
		BitSet connectionBitSet = BitSet.valueOf(new long[]{connectionByte.value});

		if (connectionBitSet.get(3)) {
			connections.add(Direction.NORTH);
		}

		if (connectionBitSet.get(2)) {
			connections.add(Direction.SOUTH);
		}

		if (connectionBitSet.get(1)) {
			connections.add(Direction.WEST);
		}

		if (connectionBitSet.get(0)) {
			connections.add(Direction.EAST);
		}

		System.out.printf("Connections at read: %d = %s\n", connectionByte.value, connections);


		for (int i = 0; i < connections.size(); i++) {
			this.reader.skipBytes(11);
		}

		System.out.println(String.format("Finished reading map header. I'm at: %x", addr));

		Map map = new Map(w.value * 2, h.value * 2, connections);
		map.tileset = tileset.value;
		map.fillWithBlock(0);
 
		this.reader.seek(addr);

		for (int x = 0; x < (w.value * 2); x++) {
			for (int y = 0; y < (h.value * 2); y++) {
				GbByte bid = this.reader.readGbByte(addr);

		//		System.out.println(String.format("addr: %x bid: %x ", addr, bid.value));
				map.setBlock(y, x, new Block(bid.value, y, x));
				addr++;
			}
		}

		//System.out.println(map);
		this.maps.add(map);

		return map;
	}
}
