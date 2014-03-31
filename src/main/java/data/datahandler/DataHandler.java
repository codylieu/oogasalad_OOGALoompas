package main.java.data.datahandler;

import java.io.FileNotFoundException;
import java.io.IOException;

import main.java.data.jsonhandler.JSONDeserializer;
import main.java.data.jsonhandler.JSONSerializer;

/**
 * An Abstract class for taking blueprints
 * from Authoring or Engine and turning
 * them into 
 * 
 * @author Jimmy Fang
 *
 */

public abstract class DataHandler {
	protected DataBundle myDataBundle;
	private JSONSerializer mySerializer;
	private JSONDeserializer myParser;
	
	public DataHandler(){
		myDataBundle = new DataBundle();
		mySerializer = new JSONSerializer();
		myParser = new JSONDeserializer();
	}
	
	/**
	 * Saves the current DataBundle with the provided filename
	 * @param filename
	 * @throws FileNotFoundException 
	 */
	
	public void serializeDataBundleToJSON(String filename) throws FileNotFoundException	{
		mySerializer.write(filename, this);
	}
	
	/**
	 * Fills the DataBundle with information retrieved from the file with the
	 * provided filename
	 * @param filename
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public DataBundle deserializeJSONToDataBundle(String filename) throws IOException, ClassNotFoundException	{
		myParser.readFile(filename);
		return null;
	}
}
