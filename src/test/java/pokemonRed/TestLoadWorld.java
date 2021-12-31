package pokemonRed;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JDialog;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import vbagamedebugger.ComponentMap;
import vbagamedebugger.gbio.GbRomReader;
import vbagamedebugger.games.pokemon.model.Map;
import vbagamedebugger.games.pokemon.model.TilesetLoader;
import vbagamedebugger.games.pokemon.model.World;
import vbagamedebugger.games.pokemon.model.World.Direction;
import vbagamedebugger.games.pokemon.model.red.MemoryHelpers;

public class TestLoadWorld {
	private static GbRomReader reader;
	private static World world;

	public static void main(String[] args) throws Exception {
		setup();

		TilesetLoader loader = new TilesetLoader(reader);
		loader.load();

		(new TestLoadWorld()).testLoadPalletTown();

		JDialog jd = new JDialog();
		jd.setTitle("view map");
		jd.add(new ComponentMap(world.getMaps().firstElement()));
		jd.setBounds(0, 0, 640, 640);
		jd.setLocationRelativeTo(null);
		jd.setVisible(true);
	}

	@BeforeClass
	public static void setup() throws FileNotFoundException {
		Assume.assumeTrue(new File("rom.gb").exists());

		TestLoadWorld.reader = new GbRomReader("rom.gb");
		world = new World(TestLoadWorld.reader);
	}

	@Test
	public void testLoadCeladonCity() throws Exception {
		Map celadonCity = world.loadMap(MemoryHelpers.ROM_MAP_CELADON_CITY);

		Assert.assertNotNull(celadonCity);
	}

	@Test
	public void testLoadPalletTown() throws Exception { 
		Map palletTown = world.loadMap(MemoryHelpers.ROM_MAP_PALLET_TOWN);
		Assert.assertNotNull(palletTown);

		Assert.assertEquals(20, palletTown.getWidth());
		Assert.assertEquals(18, palletTown.getHeight());  

		System.out.println("Connections:" + palletTown.getConnections());

		Assert.assertFalse(palletTown.hasConnection(Direction.EAST));
		Assert.assertTrue(palletTown.hasConnection(Direction.SOUTH));
		Assert.assertFalse(palletTown.hasConnection(Direction.WEST));
		Assert.assertTrue(palletTown.hasConnection(Direction.NORTH));
	}
}
