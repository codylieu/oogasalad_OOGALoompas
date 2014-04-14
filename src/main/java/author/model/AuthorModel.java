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
		//not implemented
		return true;
	}
	
	public GameBlueprint getBlueprint() {
		return myGameBlueprint;
	}

}
