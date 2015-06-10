package vbagamedebugger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RomReader {

	public static class GbByte {
		public final int value;

		public GbByte(int b) {
			this.value = b;
		}
	}

	public static class GbPointer {
		public final int value;

		public GbPointer(int value) {
			this.value = value;
		}
	}

	private final String path;

	private final RandomAccessFile reader;

	public RomReader(String path) throws FileNotFoundException {
		this.path = path;

		this.reader = new RandomAccessFile(new File(path), "r");
	}

	public byte[] read(int start, int len) throws IOException {
		byte[] ret = new byte[len];

		this.reader.read(ret, start, len);

		return ret;
	}

	public GbByte readGbByte() throws IOException {
		return new GbByte(this.reader.readUnsignedByte());
	}

	public GbByte readGbByte(int addr) throws IOException {
		this.reader.seek(addr);

		return this.readGbByte();
	}

	public GbPointer readGbPointer() throws IOException {
		int b1 = this.reader.readUnsignedByte();
		int b2 = this.reader.readUnsignedByte();

		return new GbPointer((b2 << 8) | b1);
	}

	public GbPointer readPointer(int address) throws IOException {
		this.reader.seek(address);
		return this.readGbPointer();
	}

	public void seek(int i) throws IOException {
		this.reader.seek(i);
	}

	public void skip(int i) throws IOException {
		this.reader.skipBytes(i);
	}

}
