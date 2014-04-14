package main.java.author.model;

import java.util.ArrayList;
import java.util.List;

import main.java.schema.GameBlueprint;
import main.java.schema.map.GameMap;
import main.java.schema.GameSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.WaveSpawnSchema;

public class AuthorModel {

	private GameBlueprint myGameBlueprint;

	public AuthorModel() {
		myGameBlueprint = new GameBlueprint();
	}

	public void addTower(TowerSchema towerSchema) {

	}

	public void addEnemies(List<SimpleMonsterSchema> enemySchema) {

	}

	public void addGameSettings(GameSchema gameSchema) {
		myGameBlueprint.setMyGameScenario(gameSchema);
	}

	public void addGameMaps(GameMap gameMap) {
		List<GameMap> gameMaps = new ArrayList<GameMap>();
		gameMaps.add(gameMap);
		myGameBlueprint.setMyGameMaps(gameMaps);
	}

	public void addWaves(List<WaveSpawnSchema> waves) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return true if the bluePrint has everything it needs and is validated
	 */
	public boolean isBlueprintReady() {
		// TODO Cody think about what kind of validation you would need to run
		// to make sure it's ready to be passed into the data team.
		return false;
	}

}
