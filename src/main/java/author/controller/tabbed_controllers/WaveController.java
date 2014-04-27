package main.java.author.controller.tabbed_controllers;

import java.util.ArrayList;
import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.wave_editor.WaveEditorTab;
import main.java.schema.WaveSpawnSchema;
import main.java.schema.tdobjects.MonsterSchema;

/**
 * @author garysheng The sub controller for the WaveEditorTab that also talks to
 *         the Main Controller.
 */
public class WaveController extends TabController {

	public WaveController(MainController superController) {
		super(superController);
	}

	/**
	 * Adds waved specified in the wave editor tab into the main model.
	 * 
	 * @param waves
	 */
	public void addWaves(List<WaveSpawnSchema> waves) {
		mySuperController.addWaveToModel(waves);
	}

	/**
	 * Retrieves list of possible enemy names as specified in the enemy
	 * submodule.
	 * 
	 * @return list of possible enemy names
	 */
	public String[] getEnemyNames() {

		return mySuperController.getEnemyNames();

	}

	/**
	 * @return all the created monster schemas, as specified in the
	 *         EnemyEditorTab
	 */
	public List<MonsterSchema> getMonsterSchemas() {
		return mySuperController.getMonsterSchemas();
	}

	/**
	 * Forces the wave editor tab to update its enemy information to reflect
	 * possible changes in possible enemies
	 */
	public void updateEnemyList() {
		WaveEditorTab waveEditorTab = (WaveEditorTab) myEditorTab;
		waveEditorTab.updateEnemyList();
	}

	public void shiftToEnemyTab() {
		mySuperController.shiftToEnemyTab();
	}

}
