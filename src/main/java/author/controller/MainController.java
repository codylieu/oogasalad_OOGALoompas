package main.java.author.controller;

import java.util.List;

import javax.swing.SwingUtilities;

import main.java.author.model.AuthorModel;
import main.java.author.view.AuthoringView;
import main.java.schema.GameBlueprint;
import main.java.schema.GameMap;
import main.java.schema.GameSchema;
import main.java.schema.SimpleMonsterSchema;
import main.java.schema.SimpleTowerSchema;
import main.java.schema.WaveSpawnSchema;

public class MainController {

	private AuthorModel myModel;
	private List<TabController> myTabControllers;

	public MainController() {
		myModel = new AuthorModel();
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
	public void addTowerToModel(SimpleTowerSchema towerSchema) {
		myModel.addTower(towerSchema);
	}

	/**
	 * Adds a schema representing an enemy object to the game blueprint
	 * @param enemySchema
	 */
	public void addEnemyToModel(SimpleMonsterSchema enemySchema) {
		myModel.addEnemy(enemySchema);
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
	public void addGameMapToModel(GameMap gameMap) {
		myModel.addGameMap(gameMap);
	}

	/**
	 * Adds a schema representing a TD wave to the game blueprint
	 * @param wave
	 */
	public void addWaveToModel(WaveSpawnSchema wave) {
		myModel.addWave(wave);
	}

	/**
	 * Saves the game blueprint and lets the data team know that they
	 * can begin serializing data
	 */
	public void saveBlueprint() {
		// tell model to save blueprint
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



}
