package vbagamedebugger.games.pokemon;

public class Tile {
	public boolean traversable;
	public int tileset;
	public int tilesetAddress;

	public Tile(int tileset, int tilesetAddress, boolean traversable) {
		this.traversable = traversable;
		this.tileset = tileset;
		this.tilesetAddress = tilesetAddress;
	}

	@Override
	public String toString() {
		return "";
	}
}