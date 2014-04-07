package main.java.data.datahandler;

import java.awt.geom.Point2D;
import java.util.List;

import main.java.engine.GameState;
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
	//private List<WaveSpawnSchema> myWaveSpawnSchemas;
	private int myStartingMoney;
	private int myStartingLives;
	
	// Things the gamestate constructor takes in
	//List<Monster> currentMonsters, Tower[][] currentTowers, Point2D currentEntrance, 
	//Point2D currentExit, int currentWaveNumber, List<WaveSpawnSchema> allCurrentWaves, double currentGameClock, 
	//int playerMoney, int playerLife, double playerScore
	
	/**
	 * Constructor takes in a gameblueprint, stores all the relevant
	 * value as its fields, and opens the option to create
	 * the Relevant GameState, package it with the GameBlueprint,
	 * and it on to player/engine.
	 * @param gameBlueprint
	 */
	public DefaultSettingsCreator(GameBlueprint gameBlueprint){
		myGameBlueprint = gameBlueprint;
		//myWaveSpawnSchemas = myGameBlueprint.getMyLevelSchemas(); need to figure out this, currently in engine??
		myStartingMoney = myGameBlueprint.getMyGameScenario().getMyStartingGold();
		myStartingLives = myGameBlueprint.getMyGameScenario().getMyStartingLives();
		
	}
	
	/**
	 * Creates a default GameState. Currently assumes that
	 * there will be no monsters or towers set on the field
	 * by the gameBlueprint. Can be modified to fit
	 * this condition.
	 * @return a default GameState from a GameBlueprint
	 */
	public GameState createDefaultGameState(){
		//return new GameState(null,null,null,null,1,myGameBlueprint.getMyLevelSchemas(),0,null,myStartingMoney,myStartingLives,0)
		return null;
	}
	
}
