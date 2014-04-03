package main.java.author.model;

import java.util.List;

/**
 * 
 * This is a settings object for a designed game, and at a high level is a
 * wrapper for a bunch of key value pairs that the Engine will need to reference
 * to make the game run as it is supposd to. For example, this will hold an
 * important key like isSurvivalMode, which will define a game as never ending.
 * This class does not relate to something that gets explicitly instantiated in the engine.
 */
public class GameBlueprint {

	private GameScenario myGameScenario;
	private List<TowerSchema> myTowerSchemas;
	private List<EnemySchema> myEnemySchemas;
	private List<LevelSchema> myLevelSchemas;
	private List<GameMap> myGameMaps;

	public GameScenario getMyGameScenario() {
		return myGameScenario;
	}

	public void setMyGameScenario(GameScenario myGameScenario) {
		this.myGameScenario = myGameScenario;
	}

	public List<TowerSchema> getMyTowerSchemas() {
		return myTowerSchemas;
	}

	public void setMyTowerSchemas(List<TowerSchema> myTowerSchemas) {
		this.myTowerSchemas = myTowerSchemas;
	}

	public List<EnemySchema> getMyEnemySchemas() {
		return myEnemySchemas;
	}

	public void setMyEnemySchemas(List<EnemySchema> myEnemySchemas) {
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
