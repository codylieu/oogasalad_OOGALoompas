package main.java.author.controller.tabbed_controllers;

import java.util.ArrayList;
import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.wave_editor.WaveEditorTab;
import main.java.schema.WaveSpawnSchema;
import main.java.schema.tdobjects.MonsterSchema;

public class WaveController extends TabController {

	public WaveController(MainController superController) {
		super(superController);
	}

	public void addWaves(List<WaveSpawnSchema> waves) {
		mySuperController.addWaveToModel(waves);
	}

	public String[] getEnemyNames() {
		
		return mySuperController.getEnemyNames();
		
	}
	
	public List<MonsterSchema> getMonsterSchemas() {
		return mySuperController.getMonsterSchemas();
	}
	
	public void updateEnemyList(){
		WaveEditorTab waveEditorTab = (WaveEditorTab) myEditorTab;
		waveEditorTab.updateEnemyList();
	}

	public void shiftToEnemyTab() {
		mySuperController.shiftToEnemyTab();
	}
	
}
