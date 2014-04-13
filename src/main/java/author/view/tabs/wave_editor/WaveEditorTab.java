package main.java.author.view.tabs.wave_editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.WaveController;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.WaveSpawnSchema;

public class WaveEditorTab extends EditorTab {
	
	private List<WaveSpawnSchema> myWaves;
	private WaveTabViewBuilder myBuilder;

	public WaveEditorTab(TabController tabController) {
		super(tabController);
		init();
	}

	private void addWaveData() {
		
	}
	
	private void init() {
		setLayout(new BorderLayout());
		myBuilder = new WaveTabViewBuilder(this);
		add(myBuilder.createTable(), BorderLayout.CENTER);
		updateTable();
	}

	/**
	 * Gets called when the author clicks on the Wave tab. Updates a table based
	 * on the number of rows and columns specified. Enemy names that no longer
	 * exist get deleted, and rows get increased or decreased based on changes
	 * to that value;
	 */
	public void updateTable() {
		WaveController waveController = (WaveController) myController;
		int numLevels = waveController.getNumLevels();
		List<String> possibleEnemies = waveController.getEnemyList();
		//do stuff with table
	}

	private class WaveTabViewBuilder {
		WaveEditorTab myTab;

		public WaveTabViewBuilder(WaveEditorTab tab) {
			myTab = tab;
		}

		public Component createTable() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public void saveTabData() {
		// TODO Auto-generated method stub
		
	}

}
