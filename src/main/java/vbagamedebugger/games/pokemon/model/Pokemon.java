package vbagamedebugger.games.pokemon.model;

import java.util.HashMap;

public class Pokemon {
	int id;
	int level = 0;

	public static HashMap<Integer, String> pokemonNames = new HashMap<Integer, String>();

	static {
		pokemonNames.put(0x03, "Nidoran female");
		pokemonNames.put(0x05, "Spearew");
		pokemonNames.put(15, "Nidoran male");
		pokemonNames.put(0x39, "Mankey");
		pokemonNames.put(0x54, "Pikachu");
		pokemonNames.put(0x24, "Pidgey");
		pokemonNames.put(0xa5, "Rattata");
	}

	public Pokemon(int id) {
		this.id = id;
	}

	public Pokemon(int pokemon, int level) {
		this(pokemon);
		this.level = level;
	}

	@Override
	public String toString() {
		String name = "Unknown type: " + String.format("0x%1$x", this.id);

		if (Pokemon.pokemonNames.containsKey(this.id)) {
			name = Pokemon.pokemonNames.get(this.id);
		}

		if (this.level > 0) {
			return name + " lvl:" + this.level;
		} else {
			return name;
		}
	}
}
