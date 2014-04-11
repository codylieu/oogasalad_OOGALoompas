package main.java.schema;

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
public class GameBlueprint implements Serializable {

	private GameSchema myGameScenario;
	private List<TDObjectSchema> myTDObjectSchemas;
	private List<WaveSpawnSchema> myLevelSchemas;
	private List<GameMap> myGameMaps;

	public GameSchema getMyGameScenario() {
		return myGameScenario;
	}

	public void setMyGameScenario(GameSchema myGameScenario) {
		this.myGameScenario = myGameScenario;
	}

    public List<TDObjectSchema> getMyTDObjectSchemas() {
        return myTDObjectSchemas;
    }

    public void setMyTDObjectSchemas(List<TDObjectSchema> myTDObjectSchemas) {
        this.myTDObjectSchemas = myTDObjectSchemas;
    }

	public List<WaveSpawnSchema> getMyLevelSchemas() {
		return myLevelSchemas;
	}

	public void setMyLevelSchemas(List<WaveSpawnSchema> myLevelSchemas) {
		this.myLevelSchemas = myLevelSchemas;
	}

	public List<GameMap> getMyGameMaps() {
		return myGameMaps;
	}

	public void setMyGameMaps(List<GameMap> myGameMaps) {
		this.myGameMaps = myGameMaps;
	}
	
}
