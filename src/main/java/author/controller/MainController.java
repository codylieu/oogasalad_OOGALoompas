package main.java.author.controller;

import java.util.List;
import java.util.Map;

public class MainController {

	private Map<String, TabController> myTabControllers;

	public TabController getTabController(String tabName) {
		return myTabControllers.get(tabName);
	}

	public void addTowerToModel() {

	}

	public void addEnemyToModel() {

	}

	public void addGameSettingsToModel() {

	}

	public void addTerrainSettingsToModel() {

	}

}
