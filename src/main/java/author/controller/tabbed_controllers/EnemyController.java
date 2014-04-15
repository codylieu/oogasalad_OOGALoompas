package main.java.author.controller.tabbed_controllers;

import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.enemy.EnemyEditorTab;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;

public class EnemyController extends TabController {

	public EnemyController(MainController superController) {
		super(superController);
	}

	public void addEnemies(List<SimpleMonsterSchema> enemySchema) {

		mySuperController.addEnemiesToModel(enemySchema);

	}

	public List<String> getEnemyList() {
		EnemyEditorTab editorTab = (EnemyEditorTab) myEditorTab;
		return editorTab.getEnemyList();
	}

	

	
}
