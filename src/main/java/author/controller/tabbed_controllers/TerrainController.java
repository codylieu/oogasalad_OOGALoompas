package main.java.author.controller.tabbed_controllers;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.map.GameMapSchema;

/**
 * @author garysheng The sub controller for the TerrainEditorTab that also talks
 *         to the Main Controller.
 */
public class TerrainController extends TabController {

	public TerrainController(MainController superController) {
		super(superController);
	}

	/**
	 * Adds map specified in the TerrainEditorTab to the model.
	 * 
	 * @param gameMap
	 */
	public void addMaps(GameMapSchema gameMap) {
		mySuperController.addGameMapsToModel(gameMap);
	}

}
