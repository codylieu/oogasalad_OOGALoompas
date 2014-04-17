package main.java.author.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.author.controller.tabbed_controllers.EnemyController;
import main.java.author.controller.tabbed_controllers.GameSettingsController;
import main.java.author.model.AuthorModel;
import main.java.author.view.AuthoringView;
import main.java.schema.map.GameMapSchema;
import main.java.data.DataHandler;
import main.java.engine.objects.tower.TowerBehaviors;
import main.java.exceptions.data.InvalidGameBlueprintException;
import main.java.schema.GameBlueprint;
import main.java.schema.GameSchema;
import main.java.schema.MonsterSpawnSchema;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.WaveSpawnSchema;

public class MainController {

	private AuthorModel myModel;
	private AuthoringView myAuthoringView;
	private List<TabController> myTabControllers;

	public MainController() {
		myModel = new AuthorModel();
	}

	public void setView(AuthoringView view) {
		myAuthoringView = view;
	}
	
	public void addTabController(TabController tabController) {
		myTabControllers.add(tabController);
	}

	/**
	 * this method will be called when the Authoring View presses finalizeGame()
	 * to check if the game should actually be finalized
	 * 
	 * @return
	 */
	public boolean isGameValid() {
		return myModel.isBlueprintReady();
	}

	/**
	 * Adds a schema representing a tower object to the game blueprint
	 * @param towerSchema
	 */

	public void addTowerToModel(TowerSchema towerSchema) {
		myModel.addTower(towerSchema);
	}

	/**
	 * Adds a schema representing an enemy object to the game blueprint
	 * @param enemySchema
	 */
	public void addEnemiesToModel(List<SimpleMonsterSchema> enemySchema) {
		myModel.addEnemies(enemySchema);
	}

	/**
	 * Adds a schema containing game settings to the game blueprint
	 * @param gameSchema
	 */
	public void addGameSettingsToModel(GameSchema gameSchema) {
		myModel.addGameSettings(gameSchema);
	}
	/**
	 * Adds a schema representing characteristics of a game terrain map
	 * to the game blueprint
	 * @param gameMap
	 */
	public void addGameMapsToModel(GameMapSchema gameMap) {
		myModel.addGameMaps(gameMap);
	}

	/**
	 * Adds a schema representing a TD wave to the game blueprint
	 * @param waves
	 */
	public void addWaveToModel(List<WaveSpawnSchema> waves) {
		myModel.addWaves(waves);
	}

	/**
	 * Saves the game blueprint and lets the data team know that they
	 * can begin serializing data
	 * @throws InvalidGameBlueprintException 
	 */
	public void saveBlueprint() throws InvalidGameBlueprintException {
		DataHandler handler = new DataHandler();
		JFileChooser saveFileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP File", "zip");
		saveFileChooser.setFileFilter(filter);
		String filePath = "";
		if (saveFileChooser.showSaveDialog(myAuthoringView) == JFileChooser.APPROVE_OPTION) {
			filePath = saveFileChooser.getSelectedFile().getAbsolutePath() + ".zip";
		}
		System.out.println(filePath);
		//fake one
		handler.saveBlueprint(createTestBlueprint(), filePath);
		//handler.saveBlueprint(myModel.getBlueprint(), filePath);
	}

	private GameBlueprint createTestBlueprint() {
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

        testBlueprint.setMyLevelSchemas(testWaves);
        
        // create real map functionality
        List<GameMapSchema> mapSchema = myModel.getBlueprint().getMyGameMapSchemas();
        testBlueprint.setMyGameMapSchemas(mapSchema);

        return testBlueprint;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				AuthoringView authoringView = new AuthoringView(
						new MainController());
				authoringView.createAndShowGUI();
			}
		});

		
	}

	public List<String> getEnemyList() {
		for (TabController controller : myTabControllers) {
			if (controller instanceof EnemyController) {
				EnemyController enemyController = (EnemyController) controller;
				return enemyController.getEnemyList();
			}
		}
		
		return new ArrayList<String>();
		
	}
	
}
