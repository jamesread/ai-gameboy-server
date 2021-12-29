package vbagamedebugger.games.pokemon.model;

public class Tile {
	public int tileset;
	public int tilesetAddress;

	public Tile(int id) {

	}

	public Tile(int tileset, int tilesetAddress) {
		this.tileset = tileset;
		this.tilesetAddress = tilesetAddress;
	}

	@Override
	public String toString() {
		return "";
	}
}
