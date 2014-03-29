package main.java.author.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AuthoringView extends JFrame {

	private JTabbedPane tabbedPane;
	
	public AuthoringView(){
		createEditorTabs();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setVisible(true);
	}
	
	/**
	 * Creates the Editor Tabs for the tower, enemy, wave, terrain, etc.
	 */
	public void createEditorTabs(){
		tabbedPane = new JTabbedPane();
		
		JPanel towerEditorTab = new JPanel();
		towerEditorTab.add(new Canvas());
		tabbedPane.add("Tower Editor", towerEditorTab);
//		Need to create classes for each editor so that you can just do:
//		tabbedPane.add("Tower Editor", new TowerEditorTab());
		
		JPanel enemyEditorTab = new JPanel();
		enemyEditorTab.add(new Canvas());
		tabbedPane.add("Enemy Editor", enemyEditorTab);
//		tabbedPane.add("Enemy Editor", new EnemyEditorTab());
		
		JPanel waveEditorTab = new JPanel();
		waveEditorTab.add(new Canvas());
		tabbedPane.add("Wave Editor", waveEditorTab);
//		tabbedPane.add("Wave Editor", new WaveEditorTab());
		
		JPanel terrainEditorTab = new JPanel();
		terrainEditorTab.add(new Canvas());
		tabbedPane.add("Terrain Editor", terrainEditorTab);
//		tabbedPane.add("Terrain Editor", new TerrainEditorTab());
	}
	
	public static void main (String [] args) {
		new AuthoringView();
	}
	
}


