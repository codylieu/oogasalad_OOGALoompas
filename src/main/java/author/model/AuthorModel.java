package main.java.author.model;

import java.util.ArrayList;
import java.util.List;

import main.java.schema.GameBlueprint;
import main.java.schema.map.GameMapSchema;
import main.java.schema.GameSchema;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.WaveSpawnSchema;

public class AuthorModel {

	private GameBlueprint myGameBlueprint;

	public AuthorModel() {
		myGameBlueprint = new GameBlueprint();
	}

	public void addTowers(List<TowerSchema> towerSchemas) {
		myGameBlueprint.setMyTowerSchemas(towerSchemas);
	}

	public void addEnemies(List<MonsterSchema> enemySchema) {
		myGameBlueprint.setMyMonsterSchemas(enemySchema);
	}

	public void addGameSettings(GameSchema gameSchema) {
		myGameBlueprint.setMyGameScenario(gameSchema);
	}

	public void addGameMaps(GameMapSchema gameMap) {
		List<GameMapSchema> gameMaps = new ArrayList<GameMapSchema>();
		gameMaps.add(gameMap);
		myGameBlueprint.setMyGameMapSchemas(gameMaps);
	}

	public void addWaves(List<WaveSpawnSchema> waves) {
		myGameBlueprint.setMyWaveSchemas(waves);
	}

	/**
	 * @return true if the bluePrint has everything it needs and is validated
	 */
	public boolean isBlueprintReady() {
		//not implemented
		return true;
	}
	
	public GameBlueprint getBlueprint() {
		return myGameBlueprint;
	}

}
