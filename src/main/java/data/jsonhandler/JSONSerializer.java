package java.data.jsonhandler;

import java.data.datahandler.DataBundle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class JSONSerializer {

	public JSONSerializer(){
		
	}
	
	public void write(String filename, DataBundle d) throws FileNotFoundException	{
		File outputFile = new File("resources/" + filename + ".json");
		PrintWriter output = new PrintWriter(outputFile);
		output.println("{");
		for(Class<?> c : d.getDataMap().keySet())	{
			output.println('"' + c.toString() + '"' + ":{");
			List<Object> list = d.getList(c);
		}
	}
	
}
