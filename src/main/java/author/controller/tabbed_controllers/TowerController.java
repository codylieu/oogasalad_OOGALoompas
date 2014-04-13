package main.java.author.controller.tabbed_controllers;

import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.SimpleTowerSchema;

public class TowerController extends TabController {

	public TowerController(MainController superController) {
		super(superController);
		// TODO Auto-generated constructor stub
	}

	public void addTowers(List<SimpleTowerSchema> towerSchemas) {

		mySuperController.addTowersToModel(towerSchemas);

	}

}
