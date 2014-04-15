package main.java.author.controller.tabbed_controllers;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.GameSettingsEditorTab;
import main.java.schema.GameSchema;

public class GameSettingsController extends TabController {

	public GameSettingsController(MainController superController) {
		super(superController);
		// TODO Auto-generated constructor stub
	}

	public void addGameSettings(GameSchema gameSchema) {

		mySuperController.addGameSettingsToModel(gameSchema);
	}

	public int getNumLevels() {
		GameSettingsEditorTab myTab = (GameSettingsEditorTab) myEditorTab;
		return myTab.getNumLevels();
	}

}
