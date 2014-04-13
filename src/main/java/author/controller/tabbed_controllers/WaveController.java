package main.java.author.controller.tabbed_controllers;

import java.util.ArrayList;
import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.WaveSpawnSchema;

public class WaveController extends TabController {

	public WaveController(MainController superController) {
		super(superController);
		// TODO Auto-generated constructor stub
	}

	public void addWave(WaveSpawnSchema wave) {
		mySuperController.addWaveToModel(wave);
	}

	public int getNumLevels() {
		// hardcoded shit
		return 4;
	}

	public List<String> getEnemyList() {
		// hardcoded shit
		List<String> enemies = new ArrayList<String>();
		enemies.add("Dog");
		enemies.add("Cat");
		return enemies;
	}
}
