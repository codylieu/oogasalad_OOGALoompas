package main.java.author.controller.tabbed_controllers;

import java.util.ArrayList;
import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.wave_editor.WaveEditorTab;
import main.java.schema.WaveSpawnSchema;

public class WaveController extends TabController {

	public WaveController(MainController superController) {
		super(superController);
		// TODO Auto-generated constructor stub
	}

	public void addWaves(List<WaveSpawnSchema> waves) {
		mySuperController.addWaveToModel(waves);
	}

	public String[] getEnemyNames() {
		
		return mySuperController.getEnemyNames();
		
	}
	
	public void updateEnemyList(){
		WaveEditorTab waveEditorTab = (WaveEditorTab) myEditorTab;
		waveEditorTab.updateEnemyList();
	}
	
}
