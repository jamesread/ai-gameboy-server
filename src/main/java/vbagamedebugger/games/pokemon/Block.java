package vbagamedebugger.games.pokemon;

public class Block {
	public int tileCollection;
	public int blockDefinitionAddress;

	public Tile tl, tr, bl, br;

	public Block(int tileset, int blockDefinitionAddress) {
		this.tileCollection = tileset;
		this.blockDefinitionAddress = blockDefinitionAddress;
	}
}
