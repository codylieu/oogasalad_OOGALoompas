package main.java.data.jsonhandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.data.datahandler.DataBundle;

import com.google.gson.Gson;

/**
 * 
 * @author In-Young Jo
 *
 */
public class JSONSerializer {
	
	private Gson myGson;
	
	public JSONSerializer(){
		myGson = new Gson();
	}
	
	/**
	 * Method to write the information of a DataBundle into a text file
	 * named after the filename String
	 * @param filename
	 * @param d
	 * @throws FileNotFoundException
	 */
	public void write(String filename, DataBundle d) throws FileNotFoundException	{
		File outputFile = new File("src/main/resources/" + filename + ".txt");
		PrintWriter output = new PrintWriter(outputFile);
		Map<Class<?>, List<Object>> map = d.getDataMap();
		String j = myGson.toJson(map);
		System.out.println(j);
		output.println(j);
		output.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		DataBundle d = new DataBundle();
		TestGameObject t1  = new TestGameObject(1,2);
		TestGameObject t2 = new TestGameObject(3,4);
		d.add(t1);
		d.add(t2);
		JSONSerializer j = new JSONSerializer();
		j.write("blah",d);
		List<TestGameObject> tlist = new ArrayList<TestGameObject>();
		for(Object o : d.getList(t1))	{
			tlist.add((TestGameObject) o);
		}
		System.out.println(tlist);
	}
	
}
