package vbagamedebugger.games.pokemon.model;

import java.io.IOException;
import java.util.BitSet;
import java.util.Vector;

import vbagamedebugger.gbio.GbRomReader;

public class World {
	public enum Direction {
		NORTH, EAST, SOUTH, WEST
	}

	public Vector<Map> maps = new Vector<Map>();

	private final GbRomReader reader;

	public World(GbRomReader reader) {
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
		int tileset = this.reader.readByte(addr);
		int h = this.reader.readByte();
		int w = this.reader.readByte();

		// object (skip)
		this.reader.skipBytes(2); // pointer to map
		this.reader.skipBytes(2); // pointer to text pointers
		this.reader.skipBytes(2); // pointer to maps script

		System.out.printf("Loading map connections from address: %x\n", this.reader.getCurrentAddress());
		Vector<Direction> connections = new Vector<Direction>();
		int connectionByte = this.reader.readByte();
		BitSet connectionBitSet = BitSet.valueOf(new long[]{connectionByte});

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

		System.out.printf("Connections at read: %d = %s\n", connectionByte, connections);


		for (int i = 0; i < connections.size(); i++) {
			this.reader.skipBytes(11);
		}

		System.out.println(String.format("Finished reading map header. I'm at: %x", addr));

		Map map = new Map(w * 2, h * 2, connections);
		map.tileset = tileset;
		map.fillWithBlock(0);
 
		this.reader.seek(addr);

		for (int x = 0; x < (w * 2); x++) {
			for (int y = 0; y < (h * 2); y++) {
				int bid = this.reader.readByte(addr);

		//		System.out.println(String.format("addr: %x bid: %x ", addr, bid.value));
				map.setBlock(y, x, new Block(bid, y, x));
				addr++;
			}
		}

		//System.out.println(map);
		this.maps.add(map);

		return map;
	}
}
