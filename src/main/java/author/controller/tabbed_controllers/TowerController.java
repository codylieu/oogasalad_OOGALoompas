package main.java.author.controller.tabbed_controllers;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.tdobjects.towers.ShootingTowerSchema;

public class TowerController extends TabController {

	public TowerController(MainController superController) {
		super(superController);
		// TODO Auto-generated constructor stub
	}

	public void addTower(ShootingTowerSchema towerSchema) {

		mySuperController.addTowerToModel(towerSchema);

	}

}
