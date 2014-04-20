package main.java.author.controller.tabbed_controllers;

import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.tdobjects.TowerSchema;

public class TowerController extends TabController {

	public TowerController(MainController superController) {
		super(superController);
	}

	public void addTowers(List<TowerSchema> towerSchemas) {
		mySuperController.addTowersToModel(towerSchemas);

	}

}
