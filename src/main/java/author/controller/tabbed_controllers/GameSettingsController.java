package main.java.author.controller.tabbed_controllers;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.GameSchema;

public class GameSettingsController extends TabController {
	

	public GameSettingsController(MainController superController) {
		super(superController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isTabValid() {
		// TODO Auto-generated method stub
		return false;
	}
	

	public void addGameSettings(GameSchema gameSchema) {
		mySuperController.addGameSettingsToModel(gameSchema);
	}
}
