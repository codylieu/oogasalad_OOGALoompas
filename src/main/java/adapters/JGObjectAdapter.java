package main.java.adapters;

import java.io.IOException;
import java.lang.reflect.Type;

import jgame.JGObject;
import main.java.engine.objects.TDObject;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;


public class JGObjectAdapter extends TypeAdapter<JGObject> {
	
	@Override
	public JGObject read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		String objInfo = reader.nextString();
		String[] parts = objInfo.split(",");
		String name = parts[0];
		double x = Double.parseDouble(parts[1]);
		double y = Double.parseDouble(parts[2]);
		int colid = Integer.parseInt(parts[3]);
		String graphic = parts[4];
		double xspeed = Double.parseDouble(parts[5]);
		double yspeed = Double.parseDouble(parts[6]);
		int expiry = Integer.parseInt(parts[7]);
		
		return new JGObject(name,true,x,y,colid,graphic,xspeed,yspeed,expiry);
	}

	@Override
	public void write(JsonWriter writer, JGObject obj) throws IOException {
		if (obj == null) {
			writer.nullValue();
			return;
		}
		String objInfo = obj.getName() + "," + 
				obj.x + "," + 
				obj.y + "," +
				obj.colid + "," +
				obj.getGraphic() + "," +
				obj.xspeed + "," +
				obj.yspeed + "," + 
				obj.expiry;
		writer.value(objInfo);
	}


}
