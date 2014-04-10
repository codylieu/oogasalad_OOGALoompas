package main.java.author.model;

import main.java.schema.GameBlueprint;
import main.java.schema.GameMap;
import main.java.schema.GameSchema;
import main.java.schema.SimpleMonsterSchema;
import main.java.schema.SimpleTowerSchema;
import main.java.schema.WaveSpawnSchema;

public class AuthorModel {

	private GameBlueprint myGameBlueprint;

	public AuthorModel() {
		myGameBlueprint = new GameBlueprint();
	}

	public void addTower(SimpleTowerSchema towerSchema) {
		// TODO Auto-generated method stub

	}

	public void addEnemy(SimpleMonsterSchema enemySchema) {
		// TODO Auto-generated method stub

	}

	public void addGameSettings(GameSchema gameSchema) {
		// TODO Auto-generated method stub

	}

	public void addGameMap(GameMap gameMap) {
		// TODO Auto-generated method stub

	}

	public void addWave(WaveSpawnSchema wave) {
		// TODO Auto-generated method stub

	}

}
