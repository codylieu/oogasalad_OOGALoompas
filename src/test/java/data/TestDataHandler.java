package test.java.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import main.java.data.datahandler.DataHandler;
import main.java.schema.GameBlueprint;
import main.java.schema.GameSchema;
import net.lingala.zip4j.exception.ZipException;

import org.junit.Test;

public class TestDataHandler {
	
	public final static String FILE_PATH = "src/test/resources/";
	public final static String BLUEPRINT_PATH = "TestBlueprint.ser";
	public final static String SAVEBLUEPRINT_PATH = "SavedBlueprint";
	public final static String ZIPPED_RESOURCES = "ZippedResources.zip";
	public final static String STATE_PATH = "TestState.ser";
	public final static String TEST_ATTRIBUTE_1 = "testAttribute1";
	public final static String TEST_VALUE_1 = "testValue1";
	public final static int CURRENT_WAVE_NUMBER = 43;
	
//	@Test
//	public void testBlueprintSavingAndLoading() {
//		//Set up blueprint
//		GameSchema scenario = new GameSchema();
//		scenario.addAttribute(TEST_ATTRIBUTE_1, TEST_VALUE_1);
//		GameBlueprint savedBlueprint = new GameBlueprint();
//		savedBlueprint.setMyGameScenario(scenario);
//		
//		//Try to save blueprint
//		DataHandler dataHandler = new DataHandler();
//		if (!dataHandler.saveBlueprint(savedBlueprint, FILE_PATH + BLUEPRINT_PATH))
//			fail();
//		
//		//Load blueprint
//		GameSchema loadedSchema = null;
//		try {
//			GameBlueprint loadedBlueprint = dataHandler.loadBlueprint(FILE_PATH + BLUEPRINT_PATH);
//			loadedSchema = loadedBlueprint.getMyGameScenario();
//		} catch (ClassNotFoundException | IOException e) {
//			fail();
//		}
//		
//		//Check if the variable values are the same
//		assertTrue(loadedSchema.getAttributesMap().get(TEST_ATTRIBUTE_1).equals(TEST_VALUE_1));
//		assertFalse(loadedSchema.getAttributesMap().get(TEST_ATTRIBUTE_1).equals("THIS SHOULDNT MATCH WITH ANYTHING"));
//	}
	
//	@Test
//	public void testStateSavingAndLoading() {
//		//Set up GameState
//		GameState state = new GameState();
//		state.updateGameStates(null, null, null, null, CURRENT_WAVE_NUMBER, null, 0, 0, 0, 0);
//		
//		//Try to save State
//		DataHandler dataHandler = new DataHandler();
//		try {
//			dataHandler.saveState(state, FILE_PATH + STATE_PATH);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		
//		//Load state
//		GameState loadedState = null;
//		try {
//			loadedState = dataHandler.loadState(FILE_PATH + STATE_PATH);
//		} catch (ClassNotFoundException | IOException e) {}
//		
//		//Check if the variable values are the same
//		assertEquals(CURRENT_WAVE_NUMBER, loadedState.getCurrentWaveNumber());
////		assertNotEquals(CURRENT_WAVE_NUMBER+1, loadedState.getCurrentWaveNumber());
//	}

	/**
	 * Want to test Zippings and unzipping,
	 * do so by saving a testBlueprint to file
	 * then saving/unsaving it
	 * to file again, and finally comparing them
	 * both to make sure it's the same size
	 * the blueprint doesn't actually get compressed,
	 * but still want to make sure it doesn't get changed
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws ZipException 
	 */
	@Test
	public void testCompressionAndDecompression() throws ClassNotFoundException, IOException, ZipException {
		DataHandler testDataHandler = new DataHandler();
		//set up gameblueprint, testing by just adding a gameschema
		GameSchema testSchema = new GameSchema();
		testSchema.addAttribute("Lives",10);
		GameBlueprint testBlueprint = new GameBlueprint();
		testBlueprint.setMyGameScenario(testSchema);
		testDataHandler.saveObjectToFile(testBlueprint, FILE_PATH + BLUEPRINT_PATH); // 555 bytes
		testDataHandler.saveBlueprint(testBlueprint, FILE_PATH + SAVEBLUEPRINT_PATH);
		testDataHandler.loadBlueprint(FILE_PATH + "SavedBlueprintZippedAuthoringEnvironment.zip");
		File testBlueprintAfterTestingFile = new File("src/test/resources.replacement.tester/resources/SavedBluePrintMyBlueprint.ser");
		File testBlueprintFile = new File(FILE_PATH + BLUEPRINT_PATH);
		assertEquals(testBlueprintFile.length(),testBlueprintAfterTestingFile.length());
		// load a blueprint, simulates 
		
//		System.out.println(testDataHandler.saveBlueprint(testBlueprint, FILE_PATH + SAVEBLUEPRINT_PATH));
	}
	
//	@Test
//	public void testDecompression() throws ClassNotFoundException, IOException, ZipException {
//		DataHandler testDataHandler = new DataHandler();
//		testDataHandler.loadBlueprint(FILE_PATH + "SavedBlueprintZippedAuthoringEnvironment.zip"); // attempt to load serialized blueprint in data bundle form
//	}

}
