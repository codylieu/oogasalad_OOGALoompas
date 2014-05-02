package main.java.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import main.java.data.adapters.InterfaceAdapter;
import main.java.data.adapters.MonsterSchemaAdapter;
import main.java.data.adapters.RuntimeTypeAdapterFactory;
import main.java.engine.objects.tower.TowerBehaviors;
import main.java.schema.GameBlueprint;
import main.java.schema.GameSchema;
import main.java.schema.MonsterSpawnSchema;
import main.java.schema.WaveSpawnSchema;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.ws.model.RuntimeModeler;

public class JSONHandler {

	private static String FILE_PATH = "src/main/resources/";
	private Gson myGson;
	
	public JSONHandler(){
		GsonBuilder gsonBuilder = new GsonBuilder();
//		RuntimeTypeAdapterFactory<TDObjectSchema> SchemaAdapter 
//			= RuntimeTypeAdapterFactory.of(TDObjectSchema.class);
//		SchemaAdapter.registerSubtype(MonsterSchema.class,"monsterschema");
//		gsonBuilder.registerTypeAdapter(TDObjectSchema.class, SchemaAdapter);
		
		gsonBuilder.registerTypeAdapter(MonsterSchema.class, new MonsterSchemaAdapter());
		
//		gsonBuilder.registerTypeAdapter(MonsterSchema.class, new InterfaceAdapter<MonsterSchema>());
	    gsonBuilder.setPrettyPrinting();
	    myGson = gsonBuilder.create();
  		
  	}
	/**
	 * Method to write the information of an object into a text file
	 * named after the filename String
	 * @param filename
	 * @param d
	 * @throws FileNotFoundException
	 */
	public String serializeObjectToJSON(Object obj, String filename) throws FileNotFoundException	{

		File outputFile = new File(FILE_PATH + filename + ".json");
		PrintWriter output = new PrintWriter(outputFile);
		String json = myGson.toJson(obj);
		System.out.println(json);
		output.println(json);
		output.close();
		return json;
		
	}
	
	/**
	 * 
	 * Takes a JSON file and creates the object
	 * from it, will mostly be used to load gameblueprints
	 * 
	 * @param filepath
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Object deserializeObjectFromJSON(Object obj, String filename) throws IOException	{
		BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH + filename + ".json"));
		String json = "";
		String line = null;
		while ((line = reader.readLine()) != null) {
		    json += line;
		}
		return myGson.fromJson(json, obj.getClass());
	}
	
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

		// Create test monsters
		SimpleMonsterSchema testMonsterOne = new SimpleMonsterSchema();
		testMonsterOne.addAttribute(MonsterSchema.NAME, "test-monster-1");
		testMonsterOne.addAttribute(TDObjectSchema.IMAGE_NAME, "monster.png");
		testMonsterOne.addAttribute(MonsterSchema.SPEED, (double) 1);
		testMonsterOne.addAttribute(MonsterSchema.REWARD, (double) 200);
		testMonsterSchema.add(testMonsterOne);

		testBlueprint.setMyTowerSchemas(testTowerSchema);
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

		testBlueprint.setMyWaveSchemas(testWaves);
		return testBlueprint;
	}
	
	public static void main(String[] args) throws IOException	{
		
		JSONHandler j = new JSONHandler();
		GameBlueprint testBlueprint = j.createTestBlueprint();
		j.serializeObjectToJSON(testBlueprint, "testblueprint");
		GameBlueprint loadedBlueprint = (GameBlueprint) j.deserializeObjectFromJSON(testBlueprint, "testblueprint");
		j.serializeObjectToJSON(loadedBlueprint, "testLoadedBlueprint");

	}
}
