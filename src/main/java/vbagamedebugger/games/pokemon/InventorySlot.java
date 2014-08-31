package vbagamedebugger.games.pokemon;


public class InventorySlot {
	public int type;

	public int quanity;

	public InventorySlot(int type, int quantity) {
		this.type = type;
		this.quanity = quantity;
	}

	@Override
	public String toString() {
		return Items.lookup(this.type) + " x" + this.quanity;
	}
}
