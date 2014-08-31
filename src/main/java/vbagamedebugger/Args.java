package vbagamedebugger;

import com.beust.jcommander.Parameter;

public class Args {
	@Parameter(names = "--startBot")
	public boolean startBot;

	@Parameter(names = "--loadState")
	public int loadState;

	@Parameter(names = "--listener")
	public boolean listener = false;

	@Parameter(names = "--romPath", required = true)
	protected String romPath;

	@Parameter(names = "--mapCol")
	public boolean startMapCol;
}
