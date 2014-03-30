package main.java.data.jsonhandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.stream.JsonReader;

import main.java.data.datahandler.DataBundle;

/**
 * 
 * @author In-Young Jo
 *
 */
public class JSONParser {

	private DataBundle myData;

	public JSONParser()	{
		myData = new DataBundle();
	}

	/**
	 * Method to read a file with the given name and output a DataBundle based
	 * on the information provided in the file.
	 * @param filename
	 * @return
	 * @throws IOException
	 * @author in-youngjo
	 */
	public DataBundle readFile(String filename) throws IOException{
		String fileString = "";
		Scanner s = new Scanner(new File("src/main/resources/" + filename + ".txt"));
		while(s.hasNext())	{
			fileString += s.next();
		}
		s.close();
		InputStream stream = new ByteArrayInputStream(fileString.getBytes("UTF-8"));
		myData = readJsonStream(stream);
		return myData;
	}

	/**
	 * Helper method for readFile. Adds all the lists of objects obtained from
	 * the txt file to the data bundle
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public DataBundle readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		DataBundle output = new DataBundle();
		try {
			while (reader.hasNext()) {
				String className = reader.nextString();
				output.add(readObjectList(className, reader));
			}
			return output;
		} finally {
			reader.close();
		}
	}

	/**
	 * Helper method for readJsonStream. Outputs the list of objects of 
	 * the class specified by the className passed to the method.
	 * @param className
	 * @param reader
	 * @return
	 * @throws IOException 
	 */
	public List<Object> readObjectList(String className, JsonReader reader) throws IOException	{
		List<Object> list = new ArrayList<Object>();
		reader.beginArray();
		while (reader.hasNext()) {
			list.add(readObject(className, reader));
		}
		reader.endArray();
		return list;
	}

	/**
	 * Helper method for readObjectList. Outputs object of the class specified
	 * by the className passed to the method.
	 * @param className
	 * @param reader
	 * @return
	 * @throws IOException 
	 */
	public Object readObject(String className, JsonReader reader) throws IOException	{
		Object obj = new Object();
		reader.beginObject();
		
		reader.endObject();
		return obj;
	}
	
	/**
	 * Method to use reflect to create objects based on className and the 
	 * parameterList passed to the method.
	 * @param className
	 * @param parameterList
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object reflectionHelper(String className, List<Object> parameterList) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(parameterList.getClass());
		Object output = constructor.newInstance(parameterList);
		return output;
	}

}
