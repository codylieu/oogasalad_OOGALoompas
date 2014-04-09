package main.java.data.datahandler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.java.engine.GameState;
import main.java.schema.GameBlueprint;

import com.google.gson.Gson;

/**
 * @author Jimmy Fang
 *
 */

public class DataHandler {

	/*private Gson myGson;

	public DataHandler(){
		myGson = new Gson();
	}*/
	
	/**
	 * 
	 * @param currentGameState
	 * @param filePath
	 * @throws IOException
	 */
	public boolean saveState(GameState currentGameState, String filePath) throws IOException {
		return saveObjectToFile(currentGameState, filePath);
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public GameState loadState(String filePath) throws ClassNotFoundException, IOException {
		Object unserializedObject = loadObjectFromFile(filePath);

		if (unserializedObject instanceof GameState) {
			return (GameState) loadObjectFromFile(filePath);
		}
		throw new ClassNotFoundException("Not a GameState");
	}

	/**
	 * 
	 * @param blueprint to save
	 * @param filePath to save blueprint to
	 */
	public boolean saveBlueprint(GameBlueprint blueprint, String filePath) {
		DataBundle bundleToSave = new DataBundle();
		bundleToSave.setBlueprint(blueprint);
		//Zip resources folder
		//Add zipped resoures folder to data bundle
		return saveObjectToFile(bundleToSave, filePath);
	}

	/**
	 * 
	 * @param filePath
	 * @return unserialized gameblueprint
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public GameBlueprint loadBlueprint(String filePath) throws ClassNotFoundException, IOException {
		Object unserializedObject = loadObjectFromFile(filePath);

		if (unserializedObject instanceof DataBundle) {
			DataBundle bundle = ((DataBundle) loadObjectFromFile(filePath));
			//Get zipped resource folder from data bundle
			//unzip and put resources in src/main/resources
			return bundle.getBlueprint();
		}
		throw new ClassNotFoundException("Not a data bundle");
	}

	/**
	 * 
	 * @param object Object to serialize
	 * @param fileName File to save serialized object to
	 * @return whether the object was successfully saved
	 */
	private boolean saveObjectToFile(Object object, String fileName) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
			fileOut.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param fileName Name of file containing serialized object
	 * @return Unserialized object
	 */
	private Object loadObjectFromFile(String fileName) {
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object toReturn = in.readObject();
			in.close();
			fileIn.close();
			return toReturn;
		}
		catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
}
