package main.java.author.view.tabs;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.enemy.EnemyEditorTab;
import main.java.author.view.tabs.terrain.Canvas;

/**
 * @author garysheng abstract class that corresponds to a tab of the authoring
 *         view
 */
public abstract class EditorTab extends JPanel {

	protected TabController myController;

	public EditorTab(TabController tabController) {
		myController = tabController;
		myController.setTabToControl(this);
	}

	/**
	 * Calls the specific "add_____" schema method. For example,
	 * EnemyEditorTab's saveTabData would call addEnemy
	 */
	public abstract void saveTabData();

}
