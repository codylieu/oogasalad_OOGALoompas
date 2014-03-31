package main.java.data.datahandler;

import java.io.FileNotFoundException;
import java.io.IOException;

import main.java.author.model.GameBlueprint;
import main.java.data.jsonhandler.JSONParser;
import main.java.data.jsonhandler.JSONSerializer;

/**
 * 
 * 
 * @author Jimmy Fang
 *
 */

public  class AuthorDataHandler extends DataHandler {

	public AuthorDataHandler(){
		super();
	}

	/**
	 * Exposed API method for authoring environment to
	 * take a blueprint and file location and serialize
	 * into a JSON file.
	 * @param currentBlueprint
	 * @param filePath
	 * @throws FileNotFoundException
	 */

	public void serializeAuthoringData(GameBlueprint currentBlueprint, String filePath) throws FileNotFoundException {
		myDataBundle = translateBluePrintToDataBundle(currentBlueprint);
		serializeDataBundleToJSON(filePath);
	}
	
	/**
	 * 
	 * @param filePath
	 * @return translate a JSON file into a GameBlueprint
	 * so that authoring environment can allow user to 
	 * load half creating environments and modify
	 * them
	 */
	
	public GameBlueprint deserializeFromJSON(String filePath){
		//deserialize from json into a data bundle
		//translate from data bundle into game blueprint
		// return blueprint
		return null;
	}
	
	/**
	 * Translates a blueprint passed in from the authoring environment
	 * into a DataBundle
	 * @param currentBlueprint
	 * @return translated DataBundle representing entire game state 
	 */
	
	private DataBundle translateBluePrintToDataBundle(GameBlueprint currentBlueprint){
		return null;
	}

	/**
	 * Translates a data bundle to a blueprint so that
	 * authoring environment can load a previously
	 * saved authoring state and make modifications
	 * @return translated Data Bundle in the form of a GameBlueprint understandable by Authoring Environment
	 */
	
	private GameBlueprint translateDataBundleToBlueprint(){
		return null;
	}



}
