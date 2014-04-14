package main.java.data.datahandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class JSONHandler {

	private Gson myGson;
	private DataBundle myData;
	
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
		String j = myGson.toJson(d);
		System.out.println(j);
		output.println(j);
		output.close();
	}

	/*
	 * EVERYTHING BELOW THIS HAS TO DO WITH PARSING. THIS NEEDS WORK 
	 */
	
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
				//TODO: Need to gather info about blueprint and state and 
				// add to data bundle.
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
