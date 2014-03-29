package main.java.author.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AuthoringView extends JFrame {

	private JTabbedPane tabbedPane = new JTabbedPane();;
	
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
		tabbedPane.add("Tower Editor", new TowerEditorTab());
		tabbedPane.add("Enemy Editor", new EnemyEditorTab());
		tabbedPane.add("Wave Editor", new WaveEditorTab());
		tabbedPane.add("Terrain Editor", new TerrainEditorTab());
	}
	
	public static void main (String [] args) {
		new AuthoringView();
	}
	
}


