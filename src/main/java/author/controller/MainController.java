package main.java.author.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.author.controller.tabbed_controllers.EnemyController;
import main.java.author.model.AuthorModel;
import main.java.author.view.AuthoringView;
import main.java.schema.map.GameMapSchema;
import main.java.data.DataHandler;
import main.java.exceptions.data.InvalidGameBlueprintException;
import main.java.schema.GameSchema;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.WaveSpawnSchema;

/**
 * @author garysheng The master controller in the classic MVC framework. Talks
 *         to the Model (AuthorModel), the Master View, and tabControllers which
 *         talk to individual tab views. At a high level serves as the
 *         mastermind of all the activity, as well as the main method that
 *         starts the AuthoringView.
 */
public class MainController {

	/**
	 * Starts the GUI
	 * 
	 * @param args
	 */
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

	private AuthorModel myModel;
	private AuthoringView myAuthoringView;

	private List<TabController> myTabControllers;

	/**
	 * Creates instances the main controller holds references to.
	 */
	public MainController() {
		myModel = new AuthorModel();
		myTabControllers = new ArrayList<TabController>();
	}

	/**
	 * Adds a schema representing an enemy object to the game blueprint
	 * 
	 * @param enemySchema
	 */
	public void addEnemiesToModel(List<MonsterSchema> enemySchema) {
		myModel.addEnemies(enemySchema);
	}

	/**
	 * Adds a schema representing characteristics of a game terrain map to the
	 * game blueprint
	 * 
	 * @param gameMap
	 */
	public void addGameMapsToModel(GameMapSchema gameMap) {
		myModel.addGameMaps(gameMap);
	}

	/**
	 * Adds a schema containing game settings to the game blueprint
	 * 
	 * @param gameSchema
	 */
	public void addGameSettingsToModel(GameSchema gameSchema) {
		myModel.addGameSettings(gameSchema);
	}

	/**
	 * @param itemSchemas
	 *            Adds a schema representing a TD item to the game blueprint
	 */
	public void addItemsToModel(List<ItemSchema> itemSchemas) {
		myModel.addItems(itemSchemas);

	}

	/**
	 * 
	 * @param tabController
	 */
	public void addTabController(TabController tabController) {
		myTabControllers.add(tabController);
	}

	/**
	 * Adds a schema representing a tower object to the game blueprint
	 * 
	 * @param towerSchema
	 */

	public void addTowersToModel(List<TowerSchema> towerSchemas) {
		myModel.addTowers(towerSchemas);
	}

	/**
	 * Adds a schema representing a TD wave to the game blueprint
	 * 
	 * @param waves
	 */
	public void addWaveToModel(List<WaveSpawnSchema> waves) {
		myModel.addWaves(waves);
	}

	/**
	 * Called by the wave editor submodule to:
	 * 
	 * @return the list of enemy names stored in the EnemyEditor tab.
	 */
	public String[] getEnemyNames() {
		for (TabController controller : myTabControllers) {
			if (controller instanceof EnemyController) {
				EnemyController enemyController = (EnemyController) controller;
				return enemyController.getEnemyNames();
			}
		}

		return new String[0];

	}

	/**
	 * Called by the wave editor submodule to:
	 * 
	 * @return the list of monster schemas stored in the EnemyEditor tab. This
	 *         list is needed to serialize useful wave data.
	 */
	public List<MonsterSchema> getMonsterSchemas() {
		for (TabController controller : myTabControllers) {
			if (controller instanceof EnemyController) {
				EnemyController enemyController = (EnemyController) controller;
				return enemyController.getMonsterSchemas();
			}
		}
		return null;
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
	 * Saves the game blueprint and lets the data team know that they can begin
	 * serializing data
	 * 
	 * @throws InvalidGameBlueprintException
	 */
	public void saveBlueprint() throws InvalidGameBlueprintException {
		DataHandler handler = new DataHandler();
		JFileChooser saveFileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"ZIP File", "zip");
		saveFileChooser.setFileFilter(filter);
		String filePath = "";
		if (saveFileChooser.showSaveDialog(myAuthoringView) == JFileChooser.APPROVE_OPTION) {
			filePath = saveFileChooser.getSelectedFile().getAbsolutePath()
					+ ".zip";
		}
		handler.saveBlueprint(myModel.getBlueprint(), filePath);
	}

	/**
	 * Used to allow the Main controller to gain a reference to the Authoring
	 * View.
	 * 
	 * @param view
	 */
	public void setView(AuthoringView view) {
		myAuthoringView = view;
	}

	/**
	 * Called by the wave submodule to switch from the wave tab to the enemy submodule
	 */
	public void shiftToEnemyTab() {
		myAuthoringView.shiftToEnemyTab();
	}


}

