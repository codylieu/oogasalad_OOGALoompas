package main.java.author.controller.tabbed_controllers;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.SimpleTowerSchema;

public class TowerController extends TabController {
	

	public TowerController(MainController superController) {
		super(superController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isTabValid() {
		// TODO Auto-generated method stub
		return false;
	}
	

	public void addTower(SimpleTowerSchema towerSchema) {
		mySuperController.addTowerToModel(towerSchema);
	}

	@Override
	public void fireErrorPopUp() {
		// TODO Auto-generated method stub
		
	}
}
