package main.java.author.model;

import main.java.schema.GameBlueprint;
import main.java.schema.map.GameMapSchema;
import main.java.schema.GameSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.tdobjects.towers.SimpleTowerSchema;
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

	public void addGameMap(GameMapSchema gameMapSchema) {
		// TODO Auto-generated method stub

	}

	public void addWave(WaveSpawnSchema wave) {
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
