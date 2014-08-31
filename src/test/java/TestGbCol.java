import org.junit.Assert;
import org.junit.Test;

import vbagamedebugger.TilesetBitmap;

public class TestGbCol {
	@Test
	public void testColors() {
		Assert.assertEquals(0x000000, TilesetBitmap.gbcol(0, 01, 01));
		Assert.assertEquals(0x0000ff, TilesetBitmap.gbcol(0, 00, 01));
		Assert.assertEquals(0xff0000, TilesetBitmap.gbcol(0, 01, 00));
		Assert.assertEquals(0x00ff00, TilesetBitmap.gbcol(0, 00, 00));

		Assert.assertEquals(0x000000, TilesetBitmap.gbcol(1, 0b00000010, 0b00000010));
		Assert.assertEquals(0x0000ff, TilesetBitmap.gbcol(1, 0b00000000, 0b00000010));
		Assert.assertEquals(0xff0000, TilesetBitmap.gbcol(1, 0b00000010, 0b00000000));
		Assert.assertEquals(0x00ff00, TilesetBitmap.gbcol(1, 0b00000000, 0b00000000));
	}
}
