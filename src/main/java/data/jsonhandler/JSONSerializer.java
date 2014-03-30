package java.data.jsonhandler;

import java.data.datahandler.DataBundle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class JSONSerializer {
	
	private Gson myGson;
	
	public JSONSerializer(){
		myGson = new Gson();
	}
	
	public void write(String filename, DataBundle d) throws FileNotFoundException	{
		File outputFile = new File("resources/" + filename + ".json");
		PrintWriter output = new PrintWriter(outputFile);
		Map<Class<?>, List<Object>> map = d.getDataMap();
		String j = myGson.toJson(map);
		output.println(j);
		System.out.println(j);
		output.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		DataBundle d = new DataBundle();
		d.add("a");
		d.add(1);
		List<String> l = new ArrayList<String>();
		l.add("b");
		l.add("c");
		d.add(l);
		JSONSerializer j = new JSONSerializer();
		j.write("this",d);
	}
	
}
