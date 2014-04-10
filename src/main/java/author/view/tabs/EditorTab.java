package main.java.author.view.tabs;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.terrain.Canvas;

public abstract class EditorTab extends JPanel {

	protected TabController myController;

	public EditorTab(TabController tabController) {
		myController = tabController;
	}

}
