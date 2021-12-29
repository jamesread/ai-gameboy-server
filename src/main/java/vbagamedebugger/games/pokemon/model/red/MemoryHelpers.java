package vbagamedebugger.games.pokemon.model.red;

public class MemoryHelpers {
	private static final int TXT_LINE_CHARS_WIDTH = 20;

	public final static int LINE9_TXT_SRT = 0x9d00;
	public final static int LINE9_TXT_FIN = LINE9_TXT_SRT + TXT_LINE_CHARS_WIDTH;

	public final static int LINE8_TXT_SRT = 0x9d20;
	public final static int LINE8_TXT_FIN = LINE8_TXT_SRT + TXT_LINE_CHARS_WIDTH;

	public final static int LINE7_TXT_SRT = 0x9d40;
	public final static int LINE7_TXT_FIN = LINE7_TXT_SRT + TXT_LINE_CHARS_WIDTH;

	public final static int LINE6_TXT_SRT = 0x9d60;
	public final static int LINE6_TXT_FIN = LINE6_TXT_SRT + TXT_LINE_CHARS_WIDTH;

	public final static int LINE5_TXT_SRT = 0x9d80;
	public final static int LINE5_TXT_FIN = LINE5_TXT_SRT + TXT_LINE_CHARS_WIDTH;

	public final static int LINE4_TXT_SRT = 0x9da0;
	public final static int LINE4_TXT_FIN = LINE4_TXT_SRT + TXT_LINE_CHARS_WIDTH;

	public final static int LINE3_TXT_SRT = 0x9dc0;
	public final static int LINE3_TXT_FIN = LINE3_TXT_SRT + TXT_LINE_CHARS_WIDTH;

	public final static int LINE2_TXT_SRT = 0x9de0;
	public final static int LINE2_TXT_FIN = LINE2_TXT_SRT + TXT_LINE_CHARS_WIDTH;

	public final static int LINE1_TXT_SRT = 0x9e00;
	public final static int LINE1_TXT_FIN = LINE1_TXT_SRT + TXT_LINE_CHARS_WIDTH;

	public final static int LINE0_TXT_SRT = 0x9e20;
	public final static int LINE0_TXT_FIN = LINE0_TXT_SRT + TXT_LINE_CHARS_WIDTH;

	public final static int PLAYER_NAME_START = 53592;
	public final static int PLAYER_NAME_FIN = PLAYER_NAME_START + 7;

	public static final int MAP_TILESET = 0xd367;

	public static final int MAP_HEIGHT_BLOCKS = 0xd368;
	public static final int MAP_WIDTH_BLOCKS = 0xd369;

	// https://bulbapedia.bulbagarden.net/wiki/User:Tiddlywinks/Map_header_data_structure_in_Generation_I
	public static final int ROM_MAP_PALLET_TOWN = 0x182A1;
	public static final int ROM_MAP_CELADON_CITY = 0x18000;
}
