package main.java.data.datahandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import main.java.engine.GameState;
import main.java.schema.GameBlueprint;

import com.google.gson.Gson;

/**
 * @author Jimmy Fang
 *
 */

public class DataHandler {
	private final static String FILE_PATH = "src/test/resources"; // change back to src/main/resources after implementation is done!
	private final static String TEST_FILE_PATH = "src/test/resources.replacement.tester";

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
	 * Saves a blueprint and current resources folder
	 * to the file path
	 * @param blueprint to save
	 * @param filePath to save blueprint to
	 */
	public boolean saveBlueprint(GameBlueprint blueprint, String filePath) {
		DataBundle bundleToSave = new DataBundle();
		bundleToSave.setBlueprint(blueprint);
		String zipFileLocation = filePath + "ZippedResources.zip";
		ZipFile myZippedResources = compress(FILE_PATH,zipFileLocation);
		//		bundleToSave.setResourcesFolder(myZippedResources);
		bundleToSave.setResourceFolderLocation(zipFileLocation);
		return saveObjectToFile(bundleToSave, filePath + "Bundle.ser");
	}

	/**
	 * Zips a file to a target location.
	 * Can set compression level.
	 * @param inputFile
	 * @param compressedFile
	 */

	private ZipFile compress(String inputFolder,String compressedFile) {
		try {
			ZipFile zipFile = new ZipFile(compressedFile);
			File inputFolderH = new File(inputFolder);
			ZipParameters parameters = new ZipParameters();

			// COMP_DEFLATE is for compression
			// COMp_STORE no compression
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

			// DEFLATE_LEVEL_ULTRA = maximum compression
			// DEFLATE_LEVEL_MAXIMUM
			// DEFLATE_LEVEL_NORMAL = normal compression
			// DEFLATE_LEVEL_FAST
			// DEFLATE_LEVEL_FASTEST = fastest compression
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);

			// folder compressed
			zipFile.addFolder(inputFolderH, parameters);
			return zipFile;

			//			long uncompressedSize = inputFileH.length();
			//			File outputFileH = new File(compressedFile);
			//			long comrpessedSize = outputFileH.length();

			//			double ratio = (double)comrpessedSize/(double)uncompressedSize;
			//			System.out.println("File compressed with compression ratio : "+ ratio);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Loads a serialized blueprint (in a databundle with blueprint + resources)
	 * Deletes current resources folder and replaces it with the zipped resources
	 * inside the databundle
	 * @param filePath
	 * @return unserialized gameblueprint
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws ZipException 
	 */
	public GameBlueprint loadBlueprint(String filePath) throws ClassNotFoundException, IOException, ZipException {
		Object unserializedObject = loadObjectFromFile(filePath);

		if (unserializedObject instanceof DataBundle) {
			DataBundle bundle = ((DataBundle) loadObjectFromFile(filePath));
			//ZipFile myZippedResources = bundle.getZippedResourcesFolder();
			String myZippedResourcesLocation = bundle.getZippedResourcesFolderLocation();
			File myDir = new File(TEST_FILE_PATH);
			deleteDirectory(myDir);
			ZipFile myZippedResourcesFolder = new ZipFile(myZippedResourcesLocation);
			//unzip and put resources in src/main/resources
			decompress(myZippedResourcesFolder,TEST_FILE_PATH); 
			return bundle.getBlueprint();
		}
		throw new ClassNotFoundException("Not a data bundle");
	}

	/**
	 * Deletes a directory
	 * @param dir
	 * @return
	 */
	public static boolean deleteDirectory(File dir) {
	    if(! dir.exists() || !dir.isDirectory())    {
	        return false;
	    }

	    String[] files = dir.list();
	    for(int i = 0, len = files.length; i < len; i++)    {
	        File f = new File(dir, files[i]);
	        if(f.isDirectory()) {
	            deleteDirectory(f);
	        }else   {
	            f.delete();
	        }
	    }
	    return dir.delete();
	}

	
	/**
	 * Unzips a ZIP file to a target location
	 * @param compressedFile
	 * @param destination
	 */

	private static void decompress(ZipFile zippedResources,String destination) {
		try {
			if (zippedResources.isEncrypted()) {
				//zipFile.setPassword(password);
			}
			zippedResources.extractAll(destination);
		} catch (ZipException e) {
			e.printStackTrace();
		}

		System.out.println("File Decompressed");
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
	public Object loadObjectFromFile(String fileName) {
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
