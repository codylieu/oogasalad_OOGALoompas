package main.java.data;

import java.io.IOException;
import java.lang.reflect.Type;

import main.java.engine.objects.TDObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import jgame.JGObject;


public class TDObjectAdapter extends TypeAdapter<TDObject> {

	@Override
	public TDObject read(JsonReader reader) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(JsonWriter writer, TDObject obj) throws IOException {
		// TODO Auto-generated method stub
		if (obj == null) {
			writer.nullValue();
			return;
		}
	}


}
