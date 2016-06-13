package vbagamedebugger;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import vbagamedebugger.games.pokemon.Block;
import vbagamedebugger.games.pokemon.Map;

public class ComponentMap extends Component {
	private final TilesetDatabase tsdb = new TilesetDatabase();
	private Map map;

	public ComponentMap() {
		this.map = new Map(0, 0);
		this.map.fillWithBlock(0);

		this.go();
	}

	public ComponentMap(Map map) {
		this.map = map;

		this.go();
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

		g.drawImage(block.tl.getImage(), (x * colWidth) + (colWidth / 3), (y * rowWidth) + (rowWidth / 3), tileWidth, tileHeight, null);

		g.setColor(Color.BLACK);
		g.drawString(String.format("id: %1$x", block.id), (x * colWidth), (y * rowWidth) + (14));
		g.drawString(String.format("%1$x", block.tileCollection), (x * colWidth), (y * rowWidth) + 25);
	}

	private void go() {
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

	@Override
	public void paint(Graphics g) {
		if ((this.map.getWidth() == 0) || (this.map.getHeight() == 0)) {
			g.setColor(Color.RED);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			return;
		}

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.BLACK);

		int colWidth = this.getWidth() / this.map.getWidth();
		for (int i = 0; i < this.map.getWidth(); i++) {
			g.drawLine(i * colWidth, 0, i * colWidth, this.getHeight());
		}

		int rowWidth = this.getHeight() / this.map.getHeight();
		for (int i = 0; i < this.map.getHeight(); i++) {
			g.drawLine(0, i * rowWidth, this.getWidth(), i * rowWidth);
		}

		((Graphics2D) g).setStroke(new BasicStroke(3));
		
		for (Block block : this.map.getBlocks()) {
			this.drawBlock(g, block.getX(), block.getY(), colWidth, rowWidth, block);
		}
	}

	public void setMap(Map map2) {
		this.map = map2;
	}
}
