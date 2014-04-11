package main.java.author.controller.tabbed_controllers;

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

}
