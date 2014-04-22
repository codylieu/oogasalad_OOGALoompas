package main.java.author.controller;

import main.java.author.view.tabs.EditorTab;

/**
 * @author garysheng The abstract editor controller that holds a reference to
 *         the MainController and some concrete EditorTab
 */
public abstract class TabController {
	protected MainController mySuperController;
	protected EditorTab myEditorTab;

	public TabController(MainController superController) {
		mySuperController = superController;
	}

	public void setTabToControl(EditorTab tab) {
		myEditorTab = tab;
	}

}
