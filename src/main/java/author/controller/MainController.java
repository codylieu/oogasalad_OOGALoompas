package main.java.author.controller;

import java.util.List;

import javax.swing.SwingUtilities;

import main.java.author.model.AuthorModel;
import main.java.author.view.AuthoringView;
import main.java.schema.map.GameMap;
import main.java.schema.GameSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.tdobjects.towers.ShootingTowerSchema;
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

	public void addTowerToModel(ShootingTowerSchema towerSchema) {
		myModel.addTower(towerSchema);
	}

	public void addEnemyToModel(SimpleMonsterSchema enemySchema) {
		myModel.addEnemy(enemySchema);
	}

	public void addGameSettingsToModel(GameSchema gameSchema) {
		myModel.addGameSettings(gameSchema);
	}

	public void addGameMapToModel(GameMap gameMap) {
		myModel.addGameMap(gameMap);
	}

	public void addWaveToModel(WaveSpawnSchema wave) {
		myModel.addWave(wave);
	}

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
