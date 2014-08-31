package vbagamedebugger;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Util {
	public static GridBagConstraints getNewGbc() {
		return new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(6, 6, 6, 6), 0, 0);
	}

	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
