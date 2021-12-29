package vbagamedebugger.games.pokemon.model.red;

import java.util.Vector;

import vbagamedebugger.GbHelper;
import vbagamedebugger.Main;
import vbagamedebugger.Util;
import vbagamedebugger.games.pokemon.model.InventorySlot;
import vbagamedebugger.games.pokemon.model.Pokemon;
import vbagamedebugger.games.pokemon.model.State;
import vbagamedebugger.games.pokemon.model.World;

import com.aurellem.gb.Gb;

public class GameState implements Runnable, vbagamedebugger.games.pokemon.model.GameState {
	private State state = State.UNKNOWN;
	String txt0 = "";
	String txt1 = "";
	String txt2 = "";
	String txt3 = "";
	String txt4 = "";
	String txt5 = "";
	String txt6 = "";
	String txt7 = "";
	String txt8 = "";
	String txt9 = "";
	private String onScreenText = "";

	private String playerName = "";

	private String lastOpponentName = "";
	private int cordX = 0;

	private int cordY = 0;
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;

	private int frames = 0;

	private final int money = 0;

	private boolean inBattle = false;

	private final Vector<InventorySlot> inventoryItems = new Vector<InventorySlot>();

	int mapTileset = 0;

	public int mapHeightBlocks = 0;

	public int mapWidthBlocks = 0;

	private final Vector<Pokemon> encounterablePokemon = new Vector<Pokemon>();

	public int mapHeightCoords = 0;

	public int mapWidthCoords = 0;
	

	@Override
	public State getState() {
		return this.state;
	}

	@Override
	public void run() {
		try {
			this.updateInit();

			while (Main.run) {
				this.update();
				Util.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		String ret = "";
		ret += "State: " + this.state + "\n";
		ret += "txt9: " + this.txt9 + "\n";
		ret += "txt8: " + this.txt8 + "\n";
		ret += "txt7: " + this.txt7 + "\n";
		ret += "txt6: " + this.txt6 + "\n";
		ret += "txt5: " + this.txt5 + "\n";
		ret += "txt4: " + this.txt4 + "\n";
		ret += "txt3: " + this.txt3 + "\n";
		ret += "txt2: " + this.txt2 + "\n";
		ret += "txt1: " + this.txt1 + "\n";
		ret += "txt0: " + this.txt0 + "\n";

		ret += "Inventory Items: " + this.inventoryItems + "\n";
		ret += "Encountable Pokemon: " + this.encounterablePokemon + "\n";

		ret += "Name: " + this.playerName + "\n";
		ret += "Coords: " + this.cordX + ":" + this.cordY + "\n";
		ret += "Last opponent name: " + this.lastOpponentName + "\n";
		ret += "Game time: " + this.hours + ":" + this.minutes + ":" + this.seconds + ":" + this.frames + "\n";
		ret += "Money: " + this.money + "\n";
		ret += "Map: " + this.mapWidthBlocks + ":" + this.mapHeightBlocks + " (blocks), " + this.mapWidthCoords + ":" + this.mapHeightCoords + " (coords), tileset: " + this.mapTileset + "\n";

		return ret;
	}

	private void update() {
		this.state = State.FREEROAM;
		this.playerName = GbHelper.dumpBlockString(MemoryHelpers.PLAYER_NAME_START, MemoryHelpers.PLAYER_NAME_FIN);
		this.lastOpponentName = GbHelper.dumpBlockString(0xcfda, 0xcfe5);

		this.cordY = GbHelper.dumpByte(0xd361);
		this.cordX = GbHelper.dumpByte(0xd362);

		this.hours = GbHelper.dumpBlockInt2(0xda40);
		this.minutes = Gb.readMemory(0xda42);
		this.seconds = Gb.readMemory(0xda43);
		this.frames = Gb.readMemory(0xda44);

		this.inBattle = false;
		if (GbHelper.readBoolean(0xd056)) {
			this.state = State.BATTLE_MAIN;
			this.inBattle = true;
		}

		if (GbHelper.readBoolean(0xcfc3)) {
			this.state = State.READING_SIGN;
		}  
 
		this.updateOnScreenText();
		this.updateInventoryItems();
		this.updateMap();
		this.updateEncounterablePokemon();
	}

	private void updateBlockTileContents(int blockId) {
		for (int baseAddress = 0x0; baseAddress < 0; baseAddress++) {
			Main.gbac.readRom(baseAddress);
		}
	}

	private void updateEncounterablePokemon() {
		int baseAddress = 0xd888;

		this.encounterablePokemon.clear();

		for (int i = 0; i < 10; i++) {
			final int level = Gb.readMemory(baseAddress + (i * 2));
			final int pokemon = Gb.readMemory(baseAddress + (i * 2) + 1);

			if (pokemon == 0) {
				break;
			}

			this.encounterablePokemon.add(new Pokemon(pokemon, level));
		}
	}

	public void updateInit() {
		this.updateBlockTileContents(1);
	}

	private void updateInventoryItems() {
		this.inventoryItems.clear();

		int baseItemAddress = 0xd31d;
		for (int i = 0; i < Gb.readMemory(0xd31c); i++) {

			InventorySlot slot = new InventorySlot(Gb.readMemory(baseItemAddress + (i * 2)), Gb.readMemory(baseItemAddress + (i * 2) + 1));
			this.inventoryItems.add(slot);
		}
	}

	private void updateMap() {
		this.mapTileset = Gb.readMemory(MemoryHelpers.MAP_TILESET);
		this.mapHeightBlocks = Gb.readMemory(MemoryHelpers.MAP_HEIGHT_BLOCKS);
		this.mapWidthBlocks = Gb.readMemory(MemoryHelpers.MAP_WIDTH_BLOCKS);

		this.mapHeightCoords = this.mapHeightBlocks * 2;
		this.mapWidthCoords = this.mapWidthBlocks * 2;
	}

	private void updateOnScreenText() {
		this.txt0 = GbHelper.dumpBlockString(MemoryHelpers.LINE0_TXT_SRT, MemoryHelpers.LINE0_TXT_FIN);
		this.txt1 = GbHelper.dumpBlockString(MemoryHelpers.LINE1_TXT_SRT, MemoryHelpers.LINE1_TXT_FIN);
		this.txt2 = GbHelper.dumpBlockString(MemoryHelpers.LINE2_TXT_SRT, MemoryHelpers.LINE2_TXT_FIN);
		this.txt3 = GbHelper.dumpBlockString(MemoryHelpers.LINE3_TXT_SRT, MemoryHelpers.LINE3_TXT_FIN);
		this.txt4 = GbHelper.dumpBlockString(MemoryHelpers.LINE4_TXT_SRT, MemoryHelpers.LINE4_TXT_FIN);
		this.txt5 = GbHelper.dumpBlockString(MemoryHelpers.LINE5_TXT_SRT, MemoryHelpers.LINE5_TXT_FIN);
		this.txt6 = GbHelper.dumpBlockString(MemoryHelpers.LINE6_TXT_SRT, MemoryHelpers.LINE6_TXT_FIN);
		this.txt7 = GbHelper.dumpBlockString(MemoryHelpers.LINE7_TXT_SRT, MemoryHelpers.LINE7_TXT_FIN);
		this.txt8 = GbHelper.dumpBlockString(MemoryHelpers.LINE8_TXT_SRT, MemoryHelpers.LINE8_TXT_FIN);
		this.txt9 = GbHelper.dumpBlockString(MemoryHelpers.LINE9_TXT_SRT, MemoryHelpers.LINE9_TXT_FIN);

		this.onScreenText = this.txt1 + this.txt2 + this.txt3 + this.txt4 + this.txt5 + this.txt6 + this.txt7;

		if (this.inBattle) {
			if (this.txt1.contains("to level")) {
				this.state = State.UNKNOWN_TEXT;
				return;
			}

			if (this.txt3.contains("gained") && this.txt1.contains("EXP.")) {
				this.state = State.ACK_EXPERIENCE;
				return;
			}

			if (this.txt8.contains("TYPE/")) {
				this.state = State.MOVE_SELECT;
				return;
			}

			if (this.onScreenText.contains("Choose")) {
				this.state = State.CHOOSE_POKEMON;
				return;
			}

			if (this.onScreenText.contains("appeared")) {
				this.state = State.WILD_APPEARED;
			}
		}
	}

}
