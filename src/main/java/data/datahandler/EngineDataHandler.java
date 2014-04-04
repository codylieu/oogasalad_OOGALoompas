package main.java.data.datahandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import main.java.engine.GameState;

/**
 * Data handler specific to the Engine. Allows them
 * to translate their specific game state into a Data Bundle and
 * JSON file. Separate from AuthoringDataHandler
 * because blueprint will involve game state factors authoring
 * will not/does not need to deal with.
 * @author Jimmy Fang
 * @author In-Young Jo
 *
 */

public class EngineDataHandler extends DataHandler {

	public EngineDataHandler(){
		super();
	}	
	
	/**
	 * Method for engine to
	 * take a game state and file location and serialize
	 * into output stream.
	 * @param currentBlueprint
	 * @param filePath
	 * @throws IOException 
	 */
	
	public void saveBundle(DataBundle currentGameState, String filePath) throws IOException {
		saveBundle(currentGameState, filePath);
	}
	
	/**
	 * 
	 * @param filePath
	 * @return translate output stream into game state
	 * for the engine to update
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	
	public GameState loadState(String filePath) throws ClassNotFoundException, IOException {
		return loadBundle(filePath).getGameState();
	}
	
}
