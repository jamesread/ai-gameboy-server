package vbagamedebugger.games.pokemon.model.red.pathFinder;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import vbagamedebugger.games.pokemon.model.Map;

public class PathFinder {
	private final Vector<Cell> path = new Vector<Cell>();
	private final Map map;

	public PathFinder(Map map) {
		this.map = map;
	}

	private int dist(Cell start, Cell goal) {
		return 0;
	}

	public void findPath(Cell start, Cell goal) {
		Vector<Cell> closed = new Vector<Cell>();
		Vector<Cell> open = new Vector<Cell>();
		Vector<Cell> cameFrom = new Vector<Cell>();

		HashMap<Cell, Integer> gScore = new HashMap<Cell, Integer>();
		gScore.put(start, 0);

		HashMap<Cell, Integer> fScore = new HashMap<Cell, Integer>();
		fScore.put(start, gScore.get(start) + this.dist(start, goal));

		while (!open.isEmpty()) {
			Cell current = this.lowest(fScore);

			if (current == goal) {
				this.reconstructPath(cameFrom, goal);
				return;
			}

			open.remove(current);
			closed.add(current);

			for (Cell neighbor : this.getNeighbors(current)) {
				if (closed.contains(neighbor)) {
					continue;
				}

				int tentative_gScore = gScore.get(current) + this.dist(current, neighbor);

				if (!open.contains(neighbor) || (tentative_gScore < gScore.get(neighbor))) {
					// cameFrom.add(neighbor) = current;
					gScore.put(neighbor, tentative_gScore);
					fScore.put(neighbor, tentative_gScore + this.dist(neighbor, goal));

					if (open.contains(neighbor)) {
						open.add(neighbor);
					}
				}
			}
		}
	}

	private Vector<Cell> getNeighbors(Cell current) {
		return null;
	}

	public Vector<Cell> getPath() {
		return this.path;
	}

	private Cell lowest(HashMap<Cell, Integer> list) {
		int lowest = 999;
		Cell lowestCell = null;

		for (Entry<Cell, Integer> entry : list.entrySet()) {
			if (entry.getValue() < lowest) {
				lowest = entry.getValue();
				lowestCell = entry.getKey();
			}
		}

		return lowestCell;
	}

	public void markCellRepresentation() {
		for (Cell cell : this.path) {
			cell.representation = '+';
		}
	}

	public void markCells() {
		for (Cell cell : this.path) {

		}
	}

	private void reconstructPath(Vector<Cell> cameFrom, Cell current) {
		if (cameFrom.contains(current)) {
			// path.add()
		} else {
			this.path.add(current);
		}
	}
}
