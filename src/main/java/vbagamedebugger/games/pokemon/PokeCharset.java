package vbagamedebugger.games.pokemon;

import java.util.HashMap;

public class PokeCharset {
	private static HashMap<Integer, CharSequence> charset = new HashMap<>();

	static {
		int idx = 0;

		// http://bulbapedia.bulbagarden.net/wiki/Talk:Pok%C3%A9mon_data_structure_in_Generation_I

		charset.put(0x50, "<NUL>");
		charset.put(0x76, "<sm-hiragana-A>");
		charset.put(0x77, "<sm-hiragana-E>");
		charset.put(0x78, "<sm-hiragana-O>");
		charset.put(0x79, "\u250C");
		charset.put(0x7A, "\u2550");
		charset.put(0x7B, "\u2510");
		charset.put(0x7C, "\u2016");
		charset.put(0x7D, "\u251C");
		charset.put(0x7E, "\u2534");
		charset.put(0x7f, " ");
		charset.put(0xe1, "<pk>");
		charset.put(0xe2, "<mn>");
		charset.put(0xe3, "-");
		charset.put(0xe4, "'r");
		charset.put(0xe5, "'m");
		charset.put(0xe6, "?");
		charset.put(0xe7, "!");
		charset.put(0xe8, ".");
		charset.put(0xe9, "<sm-katakana-A>");
		charset.put(0xea, "<sm-katakana-U>");
		charset.put(0xeb, "<sm-katakana-I>");
		charset.put(0xec, "\u25B7");
		charset.put(0xed, "\u25B6");
		charset.put(0xee, "\u25BC");
		charset.put(0xef, "<male>");
		charset.put(0xf0, "<poke$>");
		charset.put(0xf1, "<x>");
		charset.put(0xf2, ".");
		charset.put(0xf3, "/");
		charset.put(0xf4, ",");
		charset.put(0xf5, "<female>");
		charset.put(189, "\u0161"); // apostrophie s
		charset.put(816, "\u00E9"); // poke ézzzzz

		idx = 0;
		for (int i = 0xa0; i < 0xb9; i++) {
			charset.put(i, "" + (char) ('a' + idx));
			idx++;
		}

		idx = 0;
		for (int i = 0x80; i < 0x99; i++) {
			charset.put(i, "" + (char) ('A' + idx));
			idx++;
		}

		idx = 0;
		for (int i = 0xf6; i < 0xff; i++) {
			charset.put(i, "" + (char) ('0' + idx));
			idx++;
		}
	}

	public static CharSequence charValue(int i) {
		if (i < 0x50) {
			return "#";
		}

		if (charset.containsKey(i)) {
			return charset.get(i);
		} else {
			return "<dec:" + i + ">";
		}
	}

	private static int intValueForChar(char c) {
		return c + 63;
	}

}
