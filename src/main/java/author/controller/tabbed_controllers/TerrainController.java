package main.java.author.controller.tabbed_controllers;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.GameMap;

public class TerrainController extends TabController {
	

	public TerrainController(MainController superController) {
		super(superController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isTabValid() {
		// TODO Auto-generated method stub
		return false;
	}
	

	public void addMap(GameMap gameMap) {
		mySuperController.addGameMapToModel(gameMap);
	}
}
