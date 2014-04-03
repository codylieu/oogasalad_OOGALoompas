package main.java.data.datahandler;

import java.io.FileNotFoundException;
import java.io.IOException;

import main.java.schema.GameBlueprint;

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
	
	/**
	 * Method for authoring environment to
	 * take a blueprint and file location and serialize
	 * into a JSON file.
	 * @param currentBlueprint
	 * @param filePath
	 * @throws IOException 
	 */
	
	public void saveBundle(DataBundle currentGameState, String filePath) throws IOException {
		saveBundle(currentGameState, filePath);
	}
	
}
