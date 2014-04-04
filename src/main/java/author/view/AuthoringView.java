package main.java.author.view;

import java.awt.BorderLayout;
import java.awt.MenuBar;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import main.java.author.controller.MainController;
import main.java.author.view.menubar.BasicMenuBar;
import main.java.author.view.tabs.EnemyEditorTab;
import main.java.author.view.tabs.GameSettingsEditorTab;
import main.java.author.view.tabs.TowerEditorTab;
import main.java.author.view.tabs.terrain.TerrainEditorTab;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Frame that represents the GUI for the Authoring environment.
 * 
 */
public class AuthoringView extends JFrame {
	private MainController myController;
	private JTabbedPane tabbedPane = new JTabbedPane();

	public AuthoringView() {
		myController = new MainController();
		createEditorTabs(myController);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		setJMenuBar(new BasicMenuBar()); // This doesn't work. Usually I use setJMenuBar, but it gives me a compile error now.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setVisible(true);
	}

	/**
	 * Creates the Editor Tabs for the tower, enemy, wave, terrain, etc.
	 */
	public void createEditorTabs(MainController controller) {
		tabbedPane.add("Game Settings Editor", new GameSettingsEditorTab(controller));
		tabbedPane.add("Tower Editor", new TowerEditorTab(controller));
		//tabbedPane.add("Enemy Editor", new EnemyEditorTab(controller));
		tabbedPane.add("Terrain Editor", new TerrainEditorTab(controller));
	}

	public static void main (String [] args) {
		new AuthoringView();
	}

}
