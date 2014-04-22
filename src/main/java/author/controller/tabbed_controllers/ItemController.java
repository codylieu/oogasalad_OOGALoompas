package main.java.author.controller.tabbed_controllers;

import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TowerSchema;

public class ItemController extends TabController{

	public ItemController(MainController superController) {
		super(superController);
	}
	
	public void addItems(List<ItemSchema> itemSchemas) {
		mySuperController.addItemsToModel(itemSchemas);
	}
	
}
