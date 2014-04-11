package main.java.author.controller.tabbed_controllers;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.schema.SimpleMonsterSchema;

public class EnemyController extends TabController {

	public EnemyController(MainController superController) {
		super(superController);
	}

	public void addEnemy(SimpleMonsterSchema enemySchema) {

		mySuperController.addEnemyToModel(enemySchema);

	}
	
}
