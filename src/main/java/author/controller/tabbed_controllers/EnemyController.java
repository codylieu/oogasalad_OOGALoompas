package main.java.author.controller.tabbed_controllers;

import java.util.List;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.enemy.EnemyEditorTab;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;

/**
 * @author garysheng The sub controller for the EnemyEditorTab that also talks
 *         to the Main Controller.
 */
public class EnemyController extends TabController {

	public EnemyController(MainController superController) {
		super(superController);
	}

	/**
	 * called by the EnemyEditorTab to push designed enemy schemas eventually to
	 * the Model
	 * 
	 * @param enemySchema
	 */
	public void addEnemies(List<MonsterSchema> enemySchema) {

		mySuperController.addEnemiesToModel(enemySchema);

	}

	/**
	 * Used to pass enemy names to other controllers
	 * 
	 * @return the array of possible enemy names
	 */
	public String[] getEnemyNames() {
		EnemyEditorTab editorTab = (EnemyEditorTab) myEditorTab;
		return editorTab.getEnemyNamesArray();
	}

	/**
	 * Called by the EnemyEditorTab to:
	 * 
	 * @return the list of monster schemas
	 */
	public List<MonsterSchema> getMonsterSchemas() {
		EnemyEditorTab editorTab = (EnemyEditorTab) myEditorTab;
		return editorTab.getMonsterSchemas();
	}

}
