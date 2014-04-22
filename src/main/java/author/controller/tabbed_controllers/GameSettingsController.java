package main.java.author.controller.tabbed_controllers;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.GameSettingsEditorTab;
import main.java.schema.GameSchema;

/**
 * @author garysheng The sub controller for the GameSettingsEditorTab that also
 *         talks to the Main Controller.
 */
public class GameSettingsController extends TabController {

	public GameSettingsController(MainController superController) {
		super(superController);
	}

	/**
	 * Adds game settings designed in the GameSettingsEditorTab to the model
	 * 
	 * @param gameSchema
	 */
	public void addGameSettings(GameSchema gameSchema) {

		mySuperController.addGameSettingsToModel(gameSchema);
	}

}
