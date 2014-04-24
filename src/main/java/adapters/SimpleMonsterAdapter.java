package main.java.adapters;

import java.util.List;
import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import main.java.engine.objects.monster.*;

public class SimpleMonsterAdapter extends TypeAdapter<SimpleMonster>{

	@Override
	public SimpleMonster read(JsonReader reader) throws IOException {

		return null;
	}

	@Override
	public void write(JsonWriter writer, SimpleMonster obj) throws IOException {
		List<String> objInfo = obj.getInfo();
		for(String str: objInfo){
			
		}
	}

}
