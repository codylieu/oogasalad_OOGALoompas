package main.java.schema;

import java.util.List;

import main.java.author.model.GameMap;

/**
 * 
 * This is a settings object for a designed game, and at a high level is a
 * wrapper for a bunch of key value pairs that the Engine will need to reference
 * to make the game run as it is supposd to. For example, this will hold an
 * important key like isSurvivalMode, which will define a game as never ending.
 * This class does not relate to something that gets explicitly instantiated in the engine.
 */
public class GameBlueprint {

	private GameSchema myGameScenario;
	private List<TowerSchema> myTowerSchemas;
	private List<MonsterSchema> myEnemySchemas;
	private List<LevelSchema> myLevelSchemas;
	private List<GameMap> myGameMaps;

	public GameSchema getMyGameScenario() {
		return myGameScenario;
	}

	public void setMyGameScenario(GameSchema myGameScenario) {
		this.myGameScenario = myGameScenario;
	}

	public List<TowerSchema> getMyTowerSchemas() {
		return myTowerSchemas;
	}

	public void setMyTowerSchemas(List<TowerSchema> myTowerSchemas) {
		this.myTowerSchemas = myTowerSchemas;
	}

	public List<MonsterSchema> getMyEnemySchemas() {
		return myEnemySchemas;
	}

	public void setMyEnemySchemas(List<MonsterSchema> myEnemySchemas) {
		this.myEnemySchemas = myEnemySchemas;
	}

	public List<LevelSchema> getMyLevelSchemas() {
		return myLevelSchemas;
	}

	public void setMyLevelSchemas(List<LevelSchema> myLevelSchemas) {
		this.myLevelSchemas = myLevelSchemas;
	}

	public List<GameMap> getMyGameMaps() {
		return myGameMaps;
	}

	public void setMyGameMaps(List<GameMap> myGameMaps) {
		this.myGameMaps = myGameMaps;
	}
	
}
