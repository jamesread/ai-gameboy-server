package vbagamedebugger;

import java.io.IOException;

import vbagamedebugger.RomReader.GbByte;
import vbagamedebugger.RomReader.GbPointer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class GsonDebugger {
	private static final Gson gson;

	static {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(GbPointer.class, new TypeAdapter<GbPointer>() {

			@Override
			public GbPointer read(JsonReader arg0) throws IOException {
				return new GbPointer(new Integer(arg0.nextString()));
			}

			@Override
			public void write(JsonWriter writer, GbPointer arg1) throws IOException {
				if (arg1 == null) {
					writer.nullValue();
				} else {
					writer.value(String.format("0x%x", arg1.value));
				}
			}
		});

		builder.registerTypeAdapter(GbByte.class, new TypeAdapter<GbByte>() {
			@Override
			public GbByte read(JsonReader arg0) throws IOException {
				return new GbByte(new Integer(arg0.nextString()));
			}

			@Override
			public void write(JsonWriter writer, GbByte arg1) throws IOException {
				if (arg1 == null) {
					writer.nullValue();
				} else {
					writer.value(String.format("0x%x", arg1.value));
				}
			}
		});

		gson = builder.create();
	}

	public static String toJson(Object t) {
		return gson.toJson(t);
	}

}
