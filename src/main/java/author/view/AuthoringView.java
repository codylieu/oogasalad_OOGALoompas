package main.java.author.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import main.java.author.view.tabs.*;

public class AuthoringView extends JFrame {

	private JTabbedPane myTabs = new JTabbedPane();;
	
	public AuthoringView(){
		createEditorTabs();
		getContentPane().add(myTabs, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setVisible(true);
	}
	
	/**
	 * Creates the Editor Tabs for the tower, enemy, wave, terrain, etc.
	 */
	public void createEditorTabs(){
		myTabs.add("Tower Editor", new TowerEditorTab());
		myTabs.add("Enemy Editor", new EnemyEditorTab());
		myTabs.add("Terrain Editor", new TerrainEditorTab());
		myTabs.add("Schema Editor", new SchemaEditorTab());
	}
	
	public JPanel createAttributesPanel() {
		return null;
	}
	
	public static void main (String [] args) {
		new AuthoringView();
	}
	
}


