package main.java.author.controller;

import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.author.model.AuthorModel;
import main.java.author.view.AuthoringView;
import main.java.data.datahandler.DataHandler;
import main.java.exceptions.data.InvalidGameBlueprintException;
import main.java.schema.map.GameMap;
import main.java.schema.GameSchema;
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
	public void addGameMapsToModel(GameMap gameMap) {
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
		handler.saveBlueprint(myModel.getBlueprint(), filePath);
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
