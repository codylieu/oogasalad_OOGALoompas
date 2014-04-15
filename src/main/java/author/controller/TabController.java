package main.java.author.controller;

import main.java.author.view.tabs.EditorTab;

public abstract class TabController {
	protected MainController mySuperController;
	protected EditorTab myEditorTab;

	public TabController(MainController superController) {
		mySuperController = superController;
	}

	public void setControlledTab(EditorTab tab) {
		myEditorTab = tab;
	}


}
