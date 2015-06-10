package vbagamedebugger;

import com.beust.jcommander.Parameter;

public class Args {
	@Parameter(names = "--startBot")
	public boolean startBot = true;

	@Parameter(names = "--loadState")
	public int loadState;

	@Parameter(names = "--listener")
	public boolean listener = false;

	@Parameter(names = "--romPath", required = false)
	protected String romPath = "rom.gb";

	@Parameter(names = "--mapCol")
	public boolean startMapCol;
}
