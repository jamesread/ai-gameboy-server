import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import vbagamedebugger.games.pokemon.Map;
import vbagamedebugger.games.pokemon.red.pathFinder.Cell;
import vbagamedebugger.games.pokemon.red.pathFinder.PathFinder;

public class PathFindingTest {
	@Test
	public void test() {
		Map map = new Map(5, 8);
		map.fillWithBlock(2);

		System.out.println(map);

		PathFinder pf = new PathFinder(map);
		pf.findPath(map.getBlock(0, 0).tl, map.getBlock(5, 5).br);
		pf.markCellRepresentation();

		Vector<Cell> path = pf.getPath();
		//Assert.assertEquals(5, path.size());

		System.out.println(map);
	}
}
