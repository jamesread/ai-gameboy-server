package vbagamedebugger.games.pokemon.model;

public enum Items {
	ANTIDOTE(0x0b), POKEBALL(0x4), POTION(0x14), UNKNOWN(0x0);

	public static Items lookup(int type) {
		for (Items i : values()) {
			if (i.id == type) {
				return i;
			}
		}

		System.out.println("Unknown item: " + type);

		return UNKNOWN;
	}

	private final int id;

	private Items(int id) {
		this.id = id;
	}

}
