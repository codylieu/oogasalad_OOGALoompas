package test.java.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import main.java.data.DataHandler;
import main.java.data.JSONHandler;
import main.java.engine.objects.tower.TowerBehaviors;
import main.java.exceptions.data.InvalidGameBlueprintException;
import main.java.schema.GameBlueprint;
import main.java.schema.GameSchema;
import main.java.schema.MonsterSpawnSchema;
import main.java.schema.WaveSpawnSchema;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
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

	private GameBlueprint createTestBlueprint(){
		GameBlueprint testBlueprint = new GameBlueprint();

		// Populate TDObjects
		List<TowerSchema> testTowerSchema = new ArrayList<>();
		List<MonsterSchema> testMonsterSchema = new ArrayList<>();

		// Create test towers
		TowerSchema testTowerOne = new TowerSchema();
		testTowerOne.addAttribute(TowerSchema.NAME, "test-tower-1");
		testTowerOne.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
		testTowerOne.addAttribute(TowerSchema.BULLET_IMAGE_NAME, "red_bullet");
		Collection<TowerBehaviors> towerBehaviors = new ArrayList<TowerBehaviors>();
		towerBehaviors.add(TowerBehaviors.MONEY_FARMING);
		testTowerOne.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors);
		testTowerOne.addAttribute(TowerSchema.COST, (double) 10);
		testTowerSchema.add(testTowerOne);

		TowerSchema testTowerTwo = new TowerSchema();
		testTowerTwo.addAttribute(TowerSchema.NAME, "test-tower-2");
		testTowerTwo.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
		testTowerTwo.addAttribute(TowerSchema.BULLET_IMAGE_NAME, "red_bullet");
		Collection<TowerBehaviors> towerBehaviors2 = new ArrayList<TowerBehaviors>();
		towerBehaviors2.add(TowerBehaviors.SHOOTING);
		testTowerTwo.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors2);
		testTowerTwo.addAttribute(TowerSchema.COST, (double) 10);
		testTowerSchema.add(testTowerTwo);

		TowerSchema testTowerThree = new TowerSchema();
		testTowerThree.addAttribute(TowerSchema.NAME, "test-tower-3");
		testTowerThree.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
		testTowerThree.addAttribute(TowerSchema.BULLET_IMAGE_NAME, "blue_bullet");
		testTowerThree.addAttribute(TowerSchema.SHRAPNEL_IMAGE_NAME, "red_bullet");
		Collection<TowerBehaviors> towerBehaviors3 = new ArrayList<TowerBehaviors>();
		towerBehaviors3.add(TowerBehaviors.BOMBING);
		testTowerThree.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors3);
		testTowerThree.addAttribute(TowerSchema.COST, (double) 10);
		testTowerSchema.add(testTowerThree);

		TowerSchema testTowerFour = new TowerSchema();
		testTowerFour.addAttribute(TowerSchema.NAME, "test-tower-4");
		testTowerFour.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
		testTowerFour.addAttribute(TowerSchema.BULLET_IMAGE_NAME, "red_bullet");
		testTowerFour.addAttribute(TowerSchema.FREEZE_SLOWDOWN_PROPORTION, (double) 0.8);
		Collection<TowerBehaviors> towerBehaviors4 = new ArrayList<TowerBehaviors>();
		towerBehaviors4.add(TowerBehaviors.FREEZING);
		testTowerFour.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors4);
		testTowerFour.addAttribute(TowerSchema.COST, (double) 10);
		testTowerSchema.add(testTowerFour);

		testBlueprint.setMyTowerSchemas(testTowerSchema);
		
		// Create test monsters
		SimpleMonsterSchema testMonsterOne = new SimpleMonsterSchema();
		testMonsterOne.addAttribute(MonsterSchema.NAME, "test-monster-1");
		testMonsterOne.addAttribute(TDObjectSchema.IMAGE_NAME, "monster.png");
		testMonsterOne.addAttribute(MonsterSchema.SPEED, (double) 1);
		testMonsterOne.addAttribute(MonsterSchema.REWARD, (double) 200);
		testMonsterSchema.add(testMonsterOne);

		testBlueprint.setMyMonsterSchemas(testMonsterSchema);

		// Create test game schemas
		GameSchema testGameSchema = new GameSchema();
		testGameSchema.addAttribute(GameSchema.LIVES, 3);
		testGameSchema.addAttribute(GameSchema.MONEY, 503);

		testBlueprint.setMyGameScenario(testGameSchema);

		// Create wave schemas
		List<WaveSpawnSchema> testWaves = new ArrayList<WaveSpawnSchema>();
		MonsterSpawnSchema testMonsterSpawnSchemaOne = new MonsterSpawnSchema(testMonsterOne, 1);
		WaveSpawnSchema testWaveSpawnSchemaOne = new WaveSpawnSchema();
		testWaveSpawnSchemaOne.addMonsterSchema(testMonsterSpawnSchemaOne);
		testWaves.add(testWaveSpawnSchemaOne);

		MonsterSpawnSchema testMonsterSpawnSchemaTwo = new MonsterSpawnSchema(testMonsterOne, 2);
		WaveSpawnSchema testWaveSpawnSchemaTwo = new WaveSpawnSchema();
		testWaveSpawnSchemaTwo.addMonsterSchema(testMonsterSpawnSchemaTwo);
		testWaves.add(testWaveSpawnSchemaTwo);

		MonsterSpawnSchema testMonsterSpawnSchemaThree = new MonsterSpawnSchema(testMonsterOne, 10);
		WaveSpawnSchema testWaveSpawnSchemaThree = new WaveSpawnSchema();
		testWaveSpawnSchemaThree.addMonsterSchema(testMonsterSpawnSchemaThree);
		testWaves.add(testWaveSpawnSchemaThree);

		testBlueprint.setMyLevelSchemas(testWaves);
		return testBlueprint;
	}

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

	@Test
	public void testJsonSerializationAndDeserialization() throws IOException{
		JSONHandler testDataHandler = new JSONHandler();
		GameBlueprint testBlueprint = this.createTestBlueprint();
		// serializes the testBlueprint to src/main/java/resources/testBlueprintJSON.json
		String savedBlueprintLocation = "src/main/java/resources/testBlueprintJSON.json";
		testDataHandler.serializeObjectToJSON("testBlueprintJSON",testBlueprint);
		GameBlueprint loadedBlueprint = ((GameBlueprint) testDataHandler.deserializeObjectFromJSON(savedBlueprintLocation, testBlueprint));
		//assertEquals(testBlueprint, loadedBlueprint);
		//testDataHandler.serializeObjectToJSON("testBlueprintAfterJSONSerialized",loadedBlueprint);
		assertEquals(testBlueprint.getMyGameScenario().getAttributesMap(),
				loadedBlueprint.getMyGameScenario().getAttributesMap());
	}

	/**
	 * test to see if an incomplete blueprint
	 * causes testDataHandler to throw a
	 * InvalidGameBlueprintException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws ZipException
	 */
//	@Test(expected=InvalidGameBlueprintException.class)
//	public void testEngineLoadingBlueprint() throws ClassNotFoundException, IOException, ZipException {
//		DataHandler testDataHandler = new DataHandler();
//		GameSchema testSchema = new GameSchema();
//		testSchema.addAttribute("Lives",10);
//		GameBlueprint testBlueprint = new GameBlueprint();
//		testBlueprint.setMyGameScenario(testSchema);
//		testDataHandler.saveBlueprint(testBlueprint, FILE_PATH + SAVEBLUEPRINT_PATH);
//		testDataHandler.loadBlueprint(FILE_PATH + "SavedBlueprintZippedAuthoringEnvironment.zip",true);
//	}
//
//	/**
//	 * Tests compression and decompression
//	 * only works if saveObjectFromFile
//	 * and loadObjectFromFile are public
//	 * so we can test pre-compressed size
//	 * and post-compressed size
//	 * @throws ClassNotFoundException
//	 * @throws IOException
//	 * @throws ZipException
//	 */
//	@Test
//	public void testCompressionAndDecompression() throws ClassNotFoundException, IOException, ZipException {
//		DataHandler testDataHandler = new DataHandler();
//		//set up gameblueprint, testing by just adding a gameschema
//		GameBlueprint testBlueprint = new GameBlueprint();
//		GameSchema testSchema = new GameSchema();
//		testSchema.addAttribute("Lives",10);
//		testBlueprint.setMyGameScenario(testSchema);
//		testDataHandler.saveObjectToFile(testBlueprint, FILE_PATH + BLUEPRINT_PATH); // 555 bytes
//		testDataHandler.saveBlueprint(testBlueprint, FILE_PATH + SAVEBLUEPRINT_PATH);
//		GameBlueprint loadedBlueprint = testDataHandler.loadBlueprint(FILE_PATH + "SavedBlueprintZippedAuthoringEnvironment.zip",false);
//		String savedBlueprintLocation =  FILE_PATH + "testSerialzedBlueprint.ser";
//		testDataHandler.saveObjectToFile(loadedBlueprint, savedBlueprintLocation);
//		File serializedTestBlueprint = new File(savedBlueprintLocation);
//		File testBlueprintFile = new File(savedBlueprintLocation);
//		assertEquals(testBlueprintFile.length(),serializedTestBlueprint.length());
//		assertEquals(testBlueprint.getMyGameScenario().getAttributesMap().get("Lives"),
//				((GameBlueprint) testDataHandler.loadObjectFromFile(savedBlueprintLocation)).getMyGameScenario().getAttributesMap().get("Lives"));
//
//		//		System.out.println(testDataHandler.saveBlueprint(testBlueprint, FILE_PATH + SAVEBLUEPRINT_PATH));
//	}
	
	


}
