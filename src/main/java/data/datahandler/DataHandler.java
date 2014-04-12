package main.java.data.datahandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.unzip.UnzipUtil;
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
	private final static int BUFF_SIZE = 4096;

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
	 * to the file path. The ZIP file which is 
	 * saved to the file-path is a representation of a saved
	 * authoring environment, with blueprint + resources
	 * @param blueprint to save
	 * @param filePath to save blueprint to
	 */

	//	public boolean saveBlueprint(GameBlueprint blueprint, String filePath) {
	//		DataBundle bundleToSave = new DataBundle();
	//		bundleToSave.setBlueprint(blueprint);
	//		String zipFileLocation = filePath + "ZippedResources.zip";
	//		ZipFile myZippedResources = compress(FILE_PATH,zipFileLocation);
	//		//		bundleToSave.setResourcesFolder(myZippedResources);
	//		bundleToSave.setResourceFolderLocation(zipFileLocation);
	//		return saveObjectToFile(bundleToSave, filePath + "Bundle.ser");
	//	}

	public boolean saveBlueprint(GameBlueprint blueprint, String filePath) {
		String zipFileLocation = filePath + "ZippedAuthoringEnvironment.zip"; // take out added string after testing
		saveObjectToFile(blueprint,filePath + "myBlueprint.ser");
		List<File> myFilesToZip = new ArrayList<File>();
		myFilesToZip.add(new File(filePath + "myBlueprint.ser")); // right now hardcoded, can easily change when authoring implements user choosing filePath
		myFilesToZip.add(new File(FILE_PATH)); // resources folder
		return compressAuthoringEnvironment(myFilesToZip,zipFileLocation);
	}

	/**
	 * Zips a file to a target location.
	 * Can set compression level.
	 * @param inputFile
	 * @param compressedFile
	 */

	private boolean compressAuthoringEnvironment(List<File> filesToAdd, String compressedFileLocation) {
		try {
			ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(new File(compressedFileLocation)));
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
			for (File file : filesToAdd){
				outputStream.putNextEntry(file, parameters);

				if (file.isDirectory()) {
					outputStream.closeEntry();
					continue;
				}

				//Initialize inputstream
				FileInputStream inputStream = new FileInputStream(file);
				byte[] readBuff = new byte[4096];
				int readLen = -1;

				//Read the file content and write it to the OutputStream
				while ((readLen = inputStream.read(readBuff)) != -1) {
					outputStream.write(readBuff, 0, readLen);
				}

				outputStream.closeEntry();
				inputStream.close();
			}
			outputStream.finish();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Loads a serialized blueprint (a ZIP file with serialized gameBlueprint + resources))
	 * Deletes current resources folder and replaces it with the zipped resources
	 * inside the databundle
	 * @param filePath
	 * @return unserialized gameblueprint
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws ZipException 
	 */
	//	public GameBlueprint loadBlueprint(String filePath) throws ClassNotFoundException, IOException, ZipException {
	//		Object unserializedObject = loadObjectFromFile(filePath);
	//
	//		if (unserializedObject instanceof DataBundle) {
	//			DataBundle bundle = ((DataBundle) loadObjectFromFile(filePath));
	//			//ZipFile myZippedResources = bundle.getZippedResourcesFolder();
	//			String myZippedResourcesLocation = bundle.getZippedResourcesFolderLocation();
	//			File myDir = new File(TEST_FILE_PATH);
	//			deleteDirectory(myDir);
	//			ZipFile myZippedResourcesFolder = new ZipFile(myZippedResourcesLocation);
	//			//unzip and put resources in src/main/resources
	//			decompress(myZippedResourcesFolder,TEST_FILE_PATH); 
	//			return bundle.getBlueprint();
	//		}
	//		throw new ClassNotFoundException("Not a data bundle");
	//	}

	public GameBlueprint loadBlueprint(String filePath) throws ClassNotFoundException, IOException, ZipException {
		

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
	 * using input stream.
	 * @param compressedFile
	 * @param destination
	 */

	//	private static void decompress(ZipFile zippedResources,String destination) {
	//		try {
	//			if (zippedResources.isEncrypted()) {
	//				//zipFile.setPassword(password);
	//			}
	//			zippedResources.extractAll(destination);
	//		} catch (ZipException e) {
	//			e.printStackTrace();
	//		}
	//
	//		System.out.println("File Decompressed");
	//	}

	private static void decompress(String zipFileLocation,String destinationPath) {
		try {
			// Initiate the ZipFile
			ZipFile zipFile = new ZipFile(zipFileLocation);

			//Get a list of FileHeader. FileHeader is the header information for all the
			//files in the ZipFile
			List fileHeaderList = zipFile.getFileHeaders();

			// Loop through all the fileHeaders
			for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
				if (fileHeader != null) {
					
					String outFilePath = destinationPath + System.getProperty("file.separator") + fileHeader.getFileName();
					File outFile = new File(outFilePath);

					//Checks if the file is a directory
					if (fileHeader.isDirectory()) {
						//This functionality is up to your requirements
						//For now I create the directory
						outFile.mkdirs();
						continue;
					}

					//Check if the directories(including parent directories)
					//in the output file path exists
					File parentDir = outFile.getParentFile();
					if (!parentDir.exists()) {
						parentDir.mkdirs();
					}

					//Get the InputStream from the ZipFile
					ZipInputStream is = zipFile.getInputStream(fileHeader);
					//Initialize the output stream
					OutputStream os = new FileOutputStream(outFile);

					int readLen = -1;
					byte[] buff = new byte[BUFF_SIZE];

					//Loop until End of File and write the contents to the output stream
					while ((readLen = is.read(buff)) != -1) {
						os.write(buff, 0, readLen);
					}

					//Please have a look into this method for some important comments
					closeFileHandlers(is, os);

					//To restore File attributes (ex: last modified file time, 
					//read only flag, etc) of the extracted file, a utility class
					//can be used as shown below
					UnzipUtil.applyFileAttributes(fileHeader, outFile);

					System.out.println("Done extracting: " + fileHeader.getFileName());
				} else {
					System.err.println("fileheader is null. Shouldn't be here");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void closeFileHandlers(ZipInputStream is, OutputStream os) throws IOException{
		//Close output stream
		if (os != null) {
			os.close();
			os = null;
		}
		
		//Closing inputstream also checks for CRC of the the just extracted file.
		//If CRC check has to be skipped (for ex: to cancel the unzip operation, etc)
		//use method is.close(boolean skipCRCCheck) and set the flag,
		//skipCRCCheck to false
		//NOTE: It is recommended to close outputStream first because Zip4j throws 
		//an exception if CRC check fails
		if (is != null) {
			is.close();
			is = null;
		}
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
