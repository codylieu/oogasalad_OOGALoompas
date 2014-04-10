package main.java.author.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.EnemyController;
import main.java.author.controller.tabbed_controllers.GameSettingsController;
import main.java.author.controller.tabbed_controllers.TerrainController;
import main.java.author.controller.tabbed_controllers.TowerController;
import main.java.author.controller.tabbed_controllers.WaveController;
import main.java.author.view.menubar.BasicMenuBar;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.GameSettingsEditorTab;
import main.java.author.view.tabs.enemy.EnemyEditorTab;
import main.java.author.view.tabs.terrain.TerrainEditorTab;
import main.java.author.view.tabs.tower.TowerEditorTab;


/**
 * Frame that represents the GUI for the Authoring environment.
 * 
 */
public class AuthoringView extends JFrame {
	private MainController myController;

	private JTabbedPane tabbedPane = new JTabbedPane();

	private static final String GAME_SETTINGS_EDITOR_STRING = "Game Settings Editor";
	private static final String TOWER_EDITOR_STRING = "Tower Editor";
	private static final String ENEMY_EDITOR_STRING = "Enemy Editor";
	private static final String TERRAIN_EDITOR_STRING = "Terrain Editor";

	public AuthoringView(MainController mainController) {
		myController = mainController;
	}

	/**
	 * Creates the Editor Tabs for the tower, enemy, wave, terrain, etc.
	 */
	public void createEditorTabs() {
		
		TabController enemyController = new EnemyController(myController);
		TabController towerController = new TowerController(myController);
		TabController waveController = new WaveController(myController);
		TabController gameSettingsController = new GameSettingsController(
				myController);
		TabController terrainController = new TerrainController(myController);

		tabbedPane.add(GAME_SETTINGS_EDITOR_STRING, new GameSettingsEditorTab(
				gameSettingsController));
		tabbedPane
				.add(TOWER_EDITOR_STRING, new TowerEditorTab(towerController));
		tabbedPane
				.add(ENEMY_EDITOR_STRING, new EnemyEditorTab(enemyController));
		tabbedPane.add(TERRAIN_EDITOR_STRING, new TerrainEditorTab(
				terrainController));

	}

	public void createAndShowGUI() {
		createEditorTabs();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		setJMenuBar(new BasicMenuBar());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setVisible(true);
	}

}
