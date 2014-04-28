package main.java.author.controller.tabbed_controllers;

import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TowerSchema;

/**
 * @author codylieu dennispark The sub controller for the Item tab & sub tabs
 *         that communicates with the Main Controller.
 */
public class ItemController extends TabController{

	public ItemController(MainController superController) {
		super(superController);
	}
	
	public void addItems(List<ItemSchema> itemSchemas) {
		mySuperController.addItemsToModel(itemSchemas);
	}
	
}
