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

import main.java.engine.GameState;
import main.java.schema.GameBlueprint;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class JSONHandler {

	private Gson myGson;
	
	/**
	 * Method to write the information of an object into a text file
	 * named after the filename String
	 * @param filename
	 * @param d
	 * @throws FileNotFoundException
	 */
	public void serializetoJSON(String filename, Object obj) throws FileNotFoundException	{
		File outputFile = new File("src/main/resources/" + filename + ".txt");
		PrintWriter output = new PrintWriter(outputFile);
		String json = myGson.toJson(obj);
		System.out.println(json);
		output.println(json);
		output.close();
	}
	
	public GameBlueprint deserializeBlueprintFromJSON(String json){
		return new Gson().fromJson(json, (new GameBlueprint()).getClass());
	}
	
	public GameState deserializeStateFromJSON(String json)	{
		return new Gson().fromJson(json, (new GameState()).getClass());
	}
	
	/*
	 * IF DESERIALIZING IS REALLY AS EASY AS RUNNING THE TWO METHODS ABOVE, 
	 * THEN NONE OF THIS IS NEEDED
	 */
	
	/**
	 * Method to read a file with the given name and output a DataBundle based
	 * on the information provided in the file.
	 * @param filename
	 * @return
	 * @throws IOException
	 * @author in-youngjo
	 */
	public GameBlueprint readFile(String filename) throws IOException{
		GameBlueprint output = new GameBlueprint();
		String fileString = "";
		Scanner s = new Scanner(new File("src/main/resources/" + filename + ".txt"));
		while(s.hasNext())	{
			fileString += s.next();
		}
		s.close();
		InputStream stream = new ByteArrayInputStream(fileString.getBytes("UTF-8"));
		output = readJsonStream(stream);
		return output;
	}

	/**
	 * Helper method for readFile. Adds all the lists of objects obtained from
	 * the txt file to the data bundle
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public GameBlueprint readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		GameBlueprint output = new GameBlueprint();
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

	public static void main(String[] args)	{
		
	}


}
