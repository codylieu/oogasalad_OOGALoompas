package main.java.author.view;

import java.awt.BorderLayout;
import java.awt.MenuBar;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import main.java.author.view.tabs.EnemyEditorTab;
import main.java.author.view.tabs.GameSettingsEditorTab;
import main.java.author.view.tabs.TerrainEditorTab;
import main.java.author.view.tabs.TowerEditorTab;


/**
 * Frame that represents the GUI for the Authoring environment.
 *
 */
public class AuthoringView extends JFrame {

	private JTabbedPane tabbedPane = new JTabbedPane();;

	public AuthoringView(){
		createEditorTabs();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		setMenuBar(new MenuBar());
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
		tabbedPane.add("Game Settings Editor", new GameSettingsEditorTab());
		tabbedPane.add("Terrain Editor", new TerrainEditorTab());
	}

	public static void main (String [] args) {
		new AuthoringView();
	}

}


