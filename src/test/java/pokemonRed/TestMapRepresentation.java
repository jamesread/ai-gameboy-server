package pokemonRed;

import org.junit.Test;

import vbagamedebugger.games.pokemon.model.Block;
import vbagamedebugger.games.pokemon.model.Map;

public class TestMapRepresentation {
	@Test
	public void testMapString() {
		Map map = new Map(4, 4);
		map.fillWithBlock(0);
	
		map.setBlock(new Block(0, 0, '0')); 
		map.setBlock(new Block(1, 1, '1'));
		map.setBlock(new Block(2, 2, '2')); 
		  
		System.out.println(map); 
	}
}
