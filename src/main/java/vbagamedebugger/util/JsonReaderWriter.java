package vbagamedebugger;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class JsonReaderWriter {
	private static final Gson gson;

	static {
		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();
	}

	public static String toJson(Object t) {
		return gson.toJson(t);
	}

}
