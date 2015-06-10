package vbagamedebugger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowTilesetGraphics extends JFrame {
	private BufferedImage sourceImage;

	final int scaleFactor = 8;

	private final JPanel panButtons = new JPanel(new FlowLayout());

	public WindowTilesetGraphics(final BufferedImage sourceImage) {
		this.sourceImage = sourceImage;
		this.setTitle("tileset view");
		this.setSize(sourceImage.getWidth() * this.scaleFactor, sourceImage.getHeight() * this.scaleFactor);
		this.setVisible(true);

		this.panButtons.add(new XButton("Refresh", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WindowTilesetGraphics.this.updateTileset();
			}
		}));

		this.add(this.panButtons, BorderLayout.NORTH);
		this.addRenderer();

		this.updateTileset();
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

				WindowTilesetGraphics.this.updateTileset();
			}
		});

		this.add(renderer, BorderLayout.CENTER);
	}

	private void updateTileset() {
		System.out.println("reloading tileset and repainting");

		this.sourceImage = null;
		// this.repaint();
		throw new RuntimeException("lol");
	}
}
