package vbagamedebugger.games.pokemon.model.red.pathFinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import vbagamedebugger.games.pokemon.model.Block;

public class Cell {

	public char representation = '.';
	private final Block b;
	public int x;
	public int y;

	public Cell(Block b) {
		this.b = b;
	}

	public Image getImage() {
		BufferedImage bi = new BufferedImage(32, 32, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = bi.getGraphics();
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		g.setColor(Color.RED);
		g.drawLine(0, bi.getHeight() / 2, bi.getWidth(), bi.getHeight() / 2);

		return bi;
	}
}
