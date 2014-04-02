package main.java.data.datahandler;

import java.io.FileNotFoundException;
import java.io.IOException;

import main.java.author.model.GameBlueprint;
import main.java.data.jsonhandler.JSONDeserializer;
import main.java.data.jsonhandler.JSONSerializer;

/**
 * Data handler specific to the Engine. Allows them
 * to translate their specific Blueprint into a Data Bundle and
 * JSON file. Separate from AuthoringDataHandler
 * because blueprint will involve game state factors authoring
 * will not/does not need to deal with.
 * @author jimmy fang
 *
 */

public class EngineDataHandler extends DataHandler {

	public EngineDataHandler(){
		super();
	}
	
	public void serializeAuthoringData(EngineBlueprint currentBlueprint, String filePath) throws FileNotFoundException {
		myDataBundle = translateBluePrintToDataBundle(currentBlueprint);
		serializeDataBundleToJSON(filePath);
	}
	
	/**
	 * @param filePath
	 * @return translate a JSON file into a EngineBlueprint
	 * so that authoring environment can allow user to 
	 * load half creating environments and modify
	 * them
	 */
	
	public EngineBlueprint deserializeFromJSON(String filePath){
		//deserialize from json into a data bundle
		//translate from data bundle into game blueprint
		// return blueprint
		return null;
	}
	
	/**
	 * Translates a blueprint passed in from the engine
	 * into a DataBundle
	 * @param currentBlueprint
	 * @return translated DataBundle representing game state
	 *  
	 */
	
	private DataBundle translateBluePrintToDataBundle(EngineBlueprint currentBlueprint){
		return null;
	}
	
	/**
	 * Translates a data bundle to a blueprint so that
	 * engine environment can load a previously
	 * saved authoring state and make modifications
	 * @return translated Data Bundle in the form of a EngineBlueprint understandable by the Engine
	 */
	
	private EngineBlueprint translateDataBundleToBlueprint(){
		return null;
	}
	
}
