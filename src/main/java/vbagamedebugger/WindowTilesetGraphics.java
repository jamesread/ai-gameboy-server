package vbagamedebugger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

import vbagamedebugger.games.pokemon.TilesetLoader;

public class WindowTilesetGraphics extends JFrame {
	private BufferedImage sourceImage;

	final int scaleFactor = 8;

	public WindowTilesetGraphics(final BufferedImage sourceImage) {
		this.sourceImage = sourceImage;
		this.setTitle("tileset view");
		this.setSize(sourceImage.getWidth() * this.scaleFactor, sourceImage.getHeight() * this.scaleFactor);
		this.setVisible(true);

		this.addRenderer();

	}

	private void addRenderer() {

		final AffineTransform scaler = new AffineTransform();
		scaler.scale(this.scaleFactor, this.scaleFactor);

		final AffineTransformOp scalerOp = new AffineTransformOp(scaler, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		final BufferedImage renderImage = new BufferedImage(WindowTilesetGraphics.this.sourceImage.getWidth() * this.scaleFactor, WindowTilesetGraphics.this.sourceImage.getHeight() * this.scaleFactor, BufferedImage.TYPE_INT_RGB);

		JComponent renderer = new JComponent() {

			@Override
			public void paint(Graphics g) {
				super.paint(g);

				scalerOp.filter(WindowTilesetGraphics.this.sourceImage, renderImage);

				g.setColor(Color.BLUE);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.drawImage(renderImage, 0, 0, null);
			}
		};

		renderer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				System.out.println("repainting");
				WindowTilesetGraphics.this.sourceImage = new TilesetLoader().load();
				WindowTilesetGraphics.this.repaint();
			}
		});

		this.add(renderer, BorderLayout.CENTER);
	}
}
