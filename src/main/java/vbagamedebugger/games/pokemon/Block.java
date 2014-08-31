package vbagamedebugger.games.pokemon;

public class Block {
	public int tileset;
	public int blockAddress;

	public Tile tl, tr, bl, br;

	public Block(int tileset, int blockAddress) {
		this.tileset = tileset;
		this.blockAddress = blockAddress;
	}
}
