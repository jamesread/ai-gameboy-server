package vbagamedebugger;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.aurellem.gb.Gb;

public class TileBitmap extends BufferedImage {

	public static int gbcol(int index, int b1, int b2) {
		boolean one = ((b1 >> index) & 1) != 0;
		boolean two = ((b2 >> index) & 1) != 0;

		if (one && two) {
			return 0x000000;
		} else if (one && !two) {
			return 0xB888F8;
		} else if (!one && two) {
			return 0x58B8F8;
		} else if (!one && !two) {
			return 0xffffff;
		} else {
			return 0x0f0f0f;
		}
	}

	private final Graphics g;

	int tileCount = 0;

	public TileBitmap() {
		super(16 * 10, 5 * 10, BufferedImage.TYPE_INT_RGB);

		this.g = this.createGraphics();
		this.g.setColor(Color.ORANGE);
		this.g.fillRect(0, 0, this.getWidth(), this.getHeight());

	}

	public void renderTile(final int base) {
		for (int i = 0; i < 8; i++) {
			int b1 = Gb.readMemory(base + (i * 2) + 0 + this.tileCount);
			int b2 = Gb.readMemory(base + (i * 2) + 1 + this.tileCount);

			System.out.println(String.format("reading 2 bytes starting from: %x = %x %x  %d:%d", base + ((i * 2) + this.tileCount), b1, b2, 0, 0));

			this.setRGB(7, 0, TileBitmap.gbcol(0, b1, b2));
			this.setRGB(6, 0, TileBitmap.gbcol(1, b1, b2));
			this.setRGB(5, 0, TileBitmap.gbcol(2, b1, b2));
			this.setRGB(4, 0, TileBitmap.gbcol(3, b1, b2));
			this.setRGB(3, 0, TileBitmap.gbcol(4, b1, b2));
			this.setRGB(2, 0, TileBitmap.gbcol(5, b1, b2));
			this.setRGB(1, 0, TileBitmap.gbcol(6, b1, b2));
			this.setRGB(0, 0, TileBitmap.gbcol(7, b1, b2));
		}

		System.out.println("tile rendered: " + this.tileCount);

		this.tileCount++;
	}
}
