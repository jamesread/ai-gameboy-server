package vbagamedebugger.gbio;

import com.aurellem.gb.Gb;

public class GbRamReader {
	@Deprecated
	public int dumpByte(int addr) {
		return Gb.readMemory(addr);
	}

	public int readByte(int addr) {
		return Gb.readMemory(addr);
	}
	
	public boolean readBoolean(int addr) {
		return Gb.readMemory(addr) == 1;
	}
}
