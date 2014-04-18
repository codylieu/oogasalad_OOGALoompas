package main.java.schema;

import main.java.schema.map.GameMapSchema;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.TowerSchema;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * This is a settings object for a designed game, and at a high level is a
 * wrapper for a bunch of key value pairs that the Engine will need to reference
 * to make the game run as it is supposed to. For example, this will hold an
 * important key like isSurvivalMode, which will define a game as never ending.
 * This class does not relate to something that gets explicitly instantiated in the engine.
 */
public class GameBlueprint {
	private GameSchema myGameScenario;
	private List<MonsterSchema> myMonsterSchemas;
	private List<TowerSchema> myTowerSchemas;
	private List<WaveSpawnSchema> myLevelSchemas;
	private List<GameMapSchema> myGameMapSchemas;

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
	
	public List<MonsterSchema> getMyMonsterSchemas() {
		return myMonsterSchemas;
	}

	public void setMyMonsterSchemas(List<MonsterSchema> myMonsterSchemas) {
		this.myMonsterSchemas = myMonsterSchemas;
	}

	public List<WaveSpawnSchema> getMyLevelSchemas() {
		return myLevelSchemas;
	}

	public void setMyLevelSchemas(List<WaveSpawnSchema> myLevelSchemas) {
		this.myLevelSchemas = myLevelSchemas;
	}

	public List<GameMapSchema> getMyGameMapSchemas() {
		return myGameMapSchemas;
	}

	public void setMyGameMapSchemas(List<GameMapSchema> myGameMapSchemas) {
		this.myGameMapSchemas = myGameMapSchemas;
	}
}
