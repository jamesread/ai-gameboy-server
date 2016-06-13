package pokemonRed;

import java.io.FileNotFoundException;

import javax.swing.JDialog;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import vbagamedebugger.ComponentMap;
import vbagamedebugger.RomReader;
import vbagamedebugger.games.pokemon.Map;
import vbagamedebugger.games.pokemon.TilesetLoader;
import vbagamedebugger.games.pokemon.World;
import vbagamedebugger.games.pokemon.World.Direction;

public class TestLoadWorld {
	private static RomReader reader;
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
		TestLoadWorld.reader = new RomReader("rom.gb");
		world = new World(TestLoadWorld.reader);
	}

	@Test
	public void testLoadCeladonCity() throws Exception {
		Map celadonCity = world.loadMap(0x18000);

		Assert.assertNotNull(celadonCity);
	}

	@Test
	public void testLoadPalletTown() throws Exception { 
		Map palletTown = world.loadMap(0x182a1);

		Assert.assertEquals(20, palletTown.getWidth());
		Assert.assertEquals(18, palletTown.getHeight());  
		Assert.assertFalse(palletTown.hasConnection(Direction.EAST));
		Assert.assertFalse(palletTown.hasConnection(Direction.SOUTH));
		Assert.assertFalse(palletTown.hasConnection(Direction.WEST));
		Assert.assertTrue(palletTown.hasConnection(Direction.NORTH));
		
		Assert.assertNotNull(palletTown);
	}

}
