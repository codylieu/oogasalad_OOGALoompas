package main.java.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import main.java.schema.GameBlueprint;
import main.java.schema.GameSchema;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONHandler {

	private static String FILE_PATH = "src/main/resources/";
	private Gson myGson;
	
	public JSONHandler(){
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.setPrettyPrinting();
	    myGson = gsonBuilder.create();
  		
  	}
	/**
	 * Method to write the information of an object into a text file
	 * named after the filename String
	 * @param filename
	 * @param d
	 * @throws FileNotFoundException
	 */
	public String serializeObjectToJSON(Object obj, String filename) throws FileNotFoundException	{

		File outputFile = new File(FILE_PATH + filename + ".json");
		PrintWriter output = new PrintWriter(outputFile);
		String json = myGson.toJson(obj);
		System.out.println(json);
		output.println(json);
		output.close();
		return json;
		
	}
	
	/**
	 * 
	 * Takes a JSON file and creates the object
	 * from it, will mostly be used to load gameblueprints
	 * 
	 * @param filepath
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Object deserializeObjectFromJSON(Object obj, String filename) throws IOException	{
		BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH + filename + ".json"));
		String json = "";
		String line = null;
		while ((line = reader.readLine()) != null) {
		    json += line;
		}
		return new Gson().fromJson(json, obj.getClass());
	}
	
}
