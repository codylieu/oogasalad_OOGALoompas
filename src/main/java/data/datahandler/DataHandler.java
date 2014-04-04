package main.java.data.datahandler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.google.gson.Gson;

/**
 * An Abstract class for taking blueprints
 * from Authoring or Engine and turning
 * them into 
 * 
 * @author Jimmy Fang
 *
 */

public abstract class DataHandler {

	private Gson myGson;

	public DataHandler(){
		myGson = new Gson();
	}

	/**
	 * Saves the current DataBundle with the provided filename
	 * @param filename
	 * @throws IOException 
	 */
	public void saveBundle(DataBundle dataBundle, String fileName) throws IOException	{

		//JSON
		/*PrintWriter output = new PrintWriter(new File("src/main/resources/" + fileName + ".txt"));
		String objectAsJson = myGson.toJson(dataBundle);
		output.println(objectAsJson);
		output.close();*/

		//Generic Java serialization
		FileOutputStream fileOut = new FileOutputStream(fileName);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(dataBundle);
		out.close();
		fileOut.close();
	}

	/**
	 * Fills the DataBundle with information retrieved from the file with the
	 * provided filename
	 * @param filename
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public DataBundle loadBundle(String fileName) throws IOException, ClassNotFoundException {
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			DataBundle toReturn = (DataBundle) in.readObject();
			in.close();
			fileIn.close();
			return toReturn;
		}
		catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
