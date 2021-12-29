package vbagamedebugger.games.pokemon.model;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import vbagamedebugger.games.pokemon.model.World.Direction;
import vbagamedebugger.games.pokemon.model.red.pathFinder.Cell;

public class Map {

	private final Vector<Block> contents;
	private final int w, h;
	public int tileset;
	private List<Direction> connections = new Vector<Direction>(); 

	public Map(int w, int h) {
		this.w = w;
		this.h = h;
		this.contents = new Vector<Block>(w * h);
	}

	public Map(int w, int h, Vector<Direction> connections) {
		this(w, h);
		this.connections = connections; 
	}

	private int coordToIndex(int x, int y) {
		return (x * this.w) + y;
	}

	public void fillWithBlock(int blockId) {
		for (int x = 0; x < this.w; x++) {
			for (int y = 0; y < this.h; y++) {
				this.contents.add(new Block(blockId, x, y));
			}
		}
	}
	 
	public boolean hasConnection(Direction d) {
		return connections.contains(d); 
	}

	public List<Direction> getConnections() {
		return this.connections;
	}

	public Block getBlock(int x, int y) {
		int i = (x * this.w) + y;

		return this.contents.get(i);
	}

	public Vector<Block> getBlocks() {
		return this.contents;
	}

	public Vector<Cell> getCells() {
		Vector<Cell> cells = new Vector<Cell>();

		for (Block b : this.contents) {
			cells.add(b.tl);
			cells.add(b.tr);
			cells.add(b.bl);
			cells.add(b.br);
		}

		return cells;
	}

	public int getHeight() {
		return this.h;
	}

	public int getWidth() {
		return this.w;
	}
	
	public void setBlock(Block block) {
		this.setBlock(block.getX(), block.getY(), block); 
	} 

	public void setBlock(int x, int y, Block block) {
		this.contents.set(this.coordToIndex(x, y), block);
	}

	@Override
	public String toString() {
		int len = (((this.w * 2) * (this.h * 2)) + ((this.h * 2)));
		System.out.println(String.format("w: %d, h: %d, len: %d", this.w, this.h, len));

		char[] resa = new char[len];
		Arrays.fill(resa, '_');

		for (int i = 0; i < (this.h * 2); i++) {
			int index = ((i + 1) * (this.w * 2));
			index += i;
			resa[index] = '\n';
		}

		int tlindex = 0;
		for (int row = 0; row < this.h; row++) {
			for (int col = 0; col < this.w; col++) {
				int tl, tr = 0, bl = 0, br = 0;

				tl = tlindex;
				tr = tl + 1;

				bl = tlindex + ((this.w * 2) + 1);
				br = bl + 1;

				Block b = this.getBlock(row, col);

				resa[tl] = b.tl.representation;
				resa[tr] = b.tr.representation;
				resa[bl] = b.bl.representation;
				resa[br] = b.br.representation;

				tlindex += 2;
			}

			tlindex += 1;
			tlindex += (this.w * 2) + 1;
		}

		return new String(resa);
	}

}
