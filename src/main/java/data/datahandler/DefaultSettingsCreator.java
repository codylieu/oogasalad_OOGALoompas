package main.java.data.datahandler;

import java.awt.geom.Point2D;
import java.util.List;

import main.java.engine.GameState;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.Tower;
import main.java.engine.spawnschema.WaveSpawnSchema;
import main.java.schema.GameBlueprint;

/**
 * Takes in a GameBlueprint, extracts relevant values,
 * and creates a default GameState. Used the
 * first time the player plays a game.
 * @author jimmy fang
 *
 */
public class DefaultSettingsCreator {
	private GameBlueprint myGameBlueprint;
	
	// Things the gamestate constructor takes in
	//List<Monster> currentMonsters, Tower[][] currentTowers, Point2D currentEntrance, 
	//Point2D currentExit, int currentWaveNumber, List<WaveSpawnSchema> allCurrentWaves, double currentGameClock, 
	//int playerMoney, int playerLife, double playerScore
	
	public DefaultSettingsCreator(GameBlueprint gameBlueprint){
		myGameBlueprint = gameBlueprint;
	}
	
	/**
	 * Creates a default GameState. Currently assumes that
	 * there will be no monsters or towers set on the field
	 * by the gameBlueprint. Can be modified to fit
	 * this condition.
	 * @return a default GameState from a GameBlueprint
	 */
	public GameState createDefaultGameState(){
		return new GameState(null,null,null,null,1,myGameBlueprint.getMyLevelSchemas())
		return null;
	}
	
}
