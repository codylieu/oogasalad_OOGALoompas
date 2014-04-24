package main.java.adapters;

import java.util.List;
import java.util.Map;
import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import main.java.schema.tdobjects.monsters.*;

public class SimpleMonsterSchemaAdapter extends TypeAdapter<SimpleMonsterSchema>{
	
	@Override
	public SimpleMonsterSchema read(JsonReader reader) throws IOException {

		return null;
	}

	@Override
	public void write(JsonWriter writer, SimpleMonsterSchema obj) throws IOException {
		Map<String,Object> objInfo = obj.getAttributesMap();
		for(String str: objInfo.keySet()){
			
		}
	}

}
