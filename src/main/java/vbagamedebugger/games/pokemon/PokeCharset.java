package vbagamedebugger.games.pokemon;

import java.util.HashMap;

public class PokeCharset {
	private static HashMap<Integer, CharSequence> charset = new HashMap<>();

	static {
		int idx = 0;

		// http://bulbapedia.bulbagarden.net/wiki/Talk:Pok%C3%A9mon_data_structure_in_Generation_I

		PokeCharset.charset.put(0x50, "<NUL>");
		PokeCharset.charset.put(0x76, "<sm-hiragana-A>");
		PokeCharset.charset.put(0x77, "<sm-hiragana-E>");
		PokeCharset.charset.put(0x78, "<sm-hiragana-O>");
		PokeCharset.charset.put(0x79, "\u250C");
		PokeCharset.charset.put(0x7A, "\u2550");
		PokeCharset.charset.put(0x7B, "\u2510");
		PokeCharset.charset.put(0x7C, "\u2016");
		PokeCharset.charset.put(0x7D, "\u251C");
		PokeCharset.charset.put(0x7E, "\u2534");
		PokeCharset.charset.put(0x7f, " ");
		PokeCharset.charset.put(0xe1, "<pk>");
		PokeCharset.charset.put(0xe2, "<mn>");
		PokeCharset.charset.put(0xe3, "-");
		PokeCharset.charset.put(0xe4, "'r");
		PokeCharset.charset.put(0xe5, "'m");
		PokeCharset.charset.put(0xe6, "?");
		PokeCharset.charset.put(0xe7, "!");
		PokeCharset.charset.put(0xe8, ".");
		PokeCharset.charset.put(0xe9, "<sm-katakana-A>");
		PokeCharset.charset.put(0xea, "<sm-katakana-U>");
		PokeCharset.charset.put(0xeb, "<sm-katakana-I>");
		PokeCharset.charset.put(0xec, "\u25B7");
		PokeCharset.charset.put(0xed, "\u25B6");
		PokeCharset.charset.put(0xee, "\u25BC");
		PokeCharset.charset.put(0xef, "<male>");
		PokeCharset.charset.put(0xf0, "<poke$>");
		PokeCharset.charset.put(0xf1, "<x>");
		PokeCharset.charset.put(0xf2, ".");
		PokeCharset.charset.put(0xf3, "/");
		PokeCharset.charset.put(0xf4, ",");
		PokeCharset.charset.put(0xf5, "<female>");
		PokeCharset.charset.put(189, "\u0161"); // apostrophie s
		PokeCharset.charset.put(186, "\u00E9"); // poke ézzzzz
		PokeCharset.charset.put(156, ":");
		PokeCharset.charset.put(190, "zu0162"); // apostrophie T

		idx = 0;
		for (int i = 0xa0; i < 0xb9; i++) {
			PokeCharset.charset.put(i, "" + (char) ('a' + idx));
			idx++;
		}

		idx = 0;
		for (int i = 0x80; i < 0x99; i++) {
			PokeCharset.charset.put(i, "" + (char) ('A' + idx));
			idx++;
		}

		idx = 0;
		for (int i = 0xf6; i < 0xff; i++) {
			PokeCharset.charset.put(i, "" + (char) ('0' + idx));
			idx++;
		}
	}

	public static CharSequence charValue(int i) {
		if (i < 0x50) {
			return "#";
		}

		if (PokeCharset.charset.containsKey(i)) {
			return PokeCharset.charset.get(i);
		} else {
			return "<dec:" + i + ">";
		}
	}

	private static int intValueForChar(char c) {
		return c + 63;
	}

}
