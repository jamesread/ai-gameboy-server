package vbagamedebugger;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import vbagamedebugger.games.pokemon.Block;

public class ComponentMap extends Component {
	private final TilesetDatabase tsdb = new TilesetDatabase();

	public ComponentMap() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ComponentMap.this.repaint();
			}
		});

		new Thread("colmapupdater") {
			@Override
			public void run() {
				while (this.isAlive() || Main.run) {
					ComponentMap.this.repaint();
					Util.sleep(500);
				}

			};
		}.start();
	}

	private void drawBlock(Graphics g, int x, int y, int colWidth, int rowWidth, Block block) {
		x = x * 2;
		y = y * 2;
		g.setColor(Color.BLACK);

		g.setColor(this.tsdb.getColor(block.tileCollection));
		int tileWidth = colWidth / 2;
		int tileHeight = rowWidth / 2;
		g.fillRect((x * colWidth) + (colWidth / 3), (y * rowWidth) + (rowWidth / 3), tileWidth, tileHeight);
		g.fillRect(((x + 1) * colWidth) + (colWidth / 3), (y * rowWidth) + (rowWidth / 3), tileWidth, tileHeight);
		g.fillRect((x * colWidth) + (colWidth / 3), ((y + 1) * rowWidth) + (rowWidth / 3), tileWidth, tileHeight);
		g.fillRect(((x + 1) * colWidth) + (colWidth / 3), ((y + 1) * rowWidth) + (rowWidth / 3), tileWidth, tileHeight);

		g.setColor(Color.BLACK);
		g.drawString(String.format("%1$x", block.blockDefinitionAddress), (x * colWidth), (y * rowWidth) + (14));
		g.drawString(String.format("%1$x", block.tileCollection), (x * colWidth), (y * rowWidth) + 25);
	}

	@Override
	public void paint(Graphics g) {
		if ((Main.gameState.mapWidthCoords == 0) || (Main.gameState.mapHeightCoords == 0)) {
			g.setColor(Color.RED);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			return;
		}

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.BLACK);

		int colWidth = this.getWidth() / Main.gameState.mapWidthCoords;
		for (int i = 0; i < Main.gameState.mapWidthCoords; i++) {
			g.drawLine(i * colWidth, 0, i * colWidth, this.getHeight());
		}

		int rowWidth = this.getHeight() / Main.gameState.mapHeightCoords;
		for (int i = 0; i < Main.gameState.mapHeightCoords; i++) {
			g.drawLine(0, i * rowWidth, this.getWidth(), i * rowWidth);
		}

		((Graphics2D) g).setStroke(new BasicStroke(3));

		int curx = 0;
		int cury = 0;
		for (Block[] row : Main.gameState.getMap()) {
			for (Block block : row) {
				if (block != null) {
					this.drawBlock(g, curx, cury, colWidth, rowWidth, block);
				}
				curx++;
			}

			curx = 0;
			cury++;
		}

	}
}
