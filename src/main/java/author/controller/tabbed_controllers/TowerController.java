package main.java.author.controller.tabbed_controllers;

import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TowerSchema;

/**
 * @author garysheng
 *The sub controller for the TowerEditorTab that also talks to the Main Controller.
 */
public class TowerController extends TabController {

	public TowerController(MainController superController) {
		super(superController);
	}

	/**
	 * Add tower schemas from the TowerEditorTab into the Model
	 * @param towerSchemas
	 */
	public void addTowers(List<TowerSchema> towerSchemas) {
		mySuperController.addTowersToModel(towerSchemas);
	}
	
	public void addItems(List<ItemSchema> itemSchemas) {
		mySuperController.addItemsToModel(itemSchemas);
	}

}
