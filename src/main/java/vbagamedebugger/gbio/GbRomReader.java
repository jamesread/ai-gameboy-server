package vbagamedebugger.gbio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GbRomReader {
	private final String path;

	private final RandomAccessFile reader;

	public GbRomReader(String path) throws FileNotFoundException {
		this.path = path;

		this.reader = new RandomAccessFile(new File(path), "r");
	}

	public byte[] read(int start, int len) throws IOException {
		byte[] ret = new byte[len];

		this.reader.read(ret, start, len);

		return ret;
	}

	public int readByte() throws IOException {
		return this.reader.readUnsignedByte();
	}

	public int readByte(int addr) throws IOException {
		this.reader.seek(addr);

		return this.readByte();
	}

	public int readPointer() throws IOException {
		int b1 = this.reader.readUnsignedByte();
		int b2 = this.reader.readUnsignedByte();

		return new Integer((b2 << 8) | b1);
	}

	public int readPointer(int address) throws IOException {
		this.reader.seek(address);
		return this.readPointer();
	}

	public void seek(int i) throws IOException {
		this.reader.seek(i);
	}

	public void skipBytes(int i) throws IOException {
		this.reader.skipBytes(i);
	}

	public long getCurrentAddress() throws IOException {
		return this.reader.getFilePointer();
	}
}
