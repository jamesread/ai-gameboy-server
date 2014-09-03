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

	private int col = -1;
	private int row = 0;

	public TileBitmap() {
		super(16 * 10, 5 * 10, BufferedImage.TYPE_INT_RGB);

		this.g = this.createGraphics();
		this.g.setColor(Color.ORANGE);
		this.g.fillRect(0, 0, this.getWidth(), this.getHeight());

	}

	public void renderTile(final int base) {
		this.col++;

		if (this.col >= 16) {
			this.col = 0;
			this.row++;
		}

		int x = this.col * 9;
		int y = this.row * 9;

		for (int i = 0; i < 8; i++) {
			int b1 = Gb.readMemory(base + (i * 2) + 0 + this.tileCount);
			int b2 = Gb.readMemory(base + (i * 2) + 1 + this.tileCount);

			System.out.println(String.format("reading 2 bytes starting from: %x = %x %x  %d:%d", base + ((i * 2) + this.tileCount), b1, b2, x, y));

			this.setRGB(x + 7, y, TileBitmap.gbcol(0, b1, b2));
			this.setRGB(x + 6, y, TileBitmap.gbcol(1, b1, b2));
			this.setRGB(x + 5, y, TileBitmap.gbcol(2, b1, b2));
			this.setRGB(x + 4, y, TileBitmap.gbcol(3, b1, b2));
			this.setRGB(x + 3, y, TileBitmap.gbcol(4, b1, b2));
			this.setRGB(x + 2, y, TileBitmap.gbcol(5, b1, b2));
			this.setRGB(x + 1, y, TileBitmap.gbcol(6, b1, b2));
			this.setRGB(x + 0, y, TileBitmap.gbcol(7, b1, b2));

			y++;
		}

		System.out.println("tile rendered: " + this.tileCount);

		this.tileCount++;
	}
}
