package main.java.author.controller;

import java.util.Map;

public class MainController {

	private Map<String, TabController> myTabControllers;

	public TabController getTabController(String tabName) {
		return myTabControllers.get(tabName);
	}
	
	public TabController putTabController(String key, TabController tabController) {
		return myTabControllers.put(key, tabController);
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
