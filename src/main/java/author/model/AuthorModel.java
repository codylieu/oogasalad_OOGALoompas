package main.java.author.model;

import java.util.ArrayList;
import java.util.List;

import main.java.schema.GameBlueprint;
import main.java.schema.map.GameMapSchema;
import main.java.schema.GameSchema;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.WaveSpawnSchema;

/**
 * @author garysheng The model that holds the GameBlueprint, and abstracts the
 *         GameBlueprint from the the rest of the Authoring environment. Only
 *         gets referenced by the MainController.
 */
public class AuthorModel {

	private GameBlueprint myGameBlueprint;

	/**
	 * An AuthorModel right now supports the creation of one GameBluePrint at a
	 * time.
	 */
	public AuthorModel() {
		myGameBlueprint = new GameBlueprint();
	}

	/**
	 * Adds towerschemas to the game blueprint
	 * 
	 * @param towerSchemas
	 */
	public void addTowers(List<TowerSchema> towerSchemas) {
		myGameBlueprint.setMyTowerSchemas(towerSchemas);
	}

	/**
	 * Adds enemyschemas to the game blueprint
	 * 
	 * @param enemySchema
	 */
	public void addEnemies(List<MonsterSchema> enemySchema) {
		myGameBlueprint.setMyMonsterSchemas(enemySchema);
	}

	/**
	 * Adds gameschemas to the game blueprint
	 * 
	 * @param gameSchema
	 */
	public void addGameSettings(GameSchema gameSchema) {
		myGameBlueprint.setMyGameScenario(gameSchema);
	}

	/**
	 * Adds gamemaps to the game blueprint
	 * 
	 * @param gameMap
	 */
	public void addGameMaps(GameMapSchema gameMap) {
		List<GameMapSchema> gameMaps = new ArrayList<GameMapSchema>();
		gameMaps.add(gameMap);
		myGameBlueprint.setMyGameMapSchemas(gameMaps);
	}

	/**
	 * adds waves to the game blueprint
	 * 
	 * @param waves
	 */
	public void addWaves(List<WaveSpawnSchema> waves) {
		myGameBlueprint.setMyWaveSchemas(waves);
	}

	/**
	 * @return true if the bluePrint has everything it needs and is validated
	 */
	public boolean isBlueprintReady() {
		// not implemented
		return true;
	}

	/**
	 * @return the referenced blueprint
	 */
	public GameBlueprint getBlueprint() {
		return myGameBlueprint;
	}

	/**
	 * Adds itemschemas to the game blueprint
	 * 
	 * @param itemSchemas
	 */
	public void addItems(List<ItemSchema> itemSchemas) {
		myGameBlueprint.setMyItemSchemas(itemSchemas);

	}

}
