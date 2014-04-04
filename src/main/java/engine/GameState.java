package main.java.engine;

import java.awt.geom.Point2D;
import java.util.List;

import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.Tower;
import main.java.engine.spawnschema.WaveSpawnSchema;

/**
 * Store all the current game states so that Data can save
 *
 */
public class GameState {
	// model specific
	private List<Monster> monsters; 
	private Tower[][] towers;
	private Point2D entrance;
	private Point2D exit;
	private int currentWave;
	private List<WaveSpawnSchema> allWaves;
	private double gameClock;
	
	// player specific
	private int money;
	private int life;
	private double score;

	/**
	 * Update the current game states.
	 * This method is called by the model every frame. 
	 * @param currentMonsters
	 * @param currentTowers
	 * @param currentEntrance
	 * @param currentExit
	 * @param currentWaveNumber
	 * @param allCurrentWaves
	 * @param currentGameClock
	 * @param playerMoney
	 * @param playerLife
	 * @param playerScore
	 */
	public void updateGameStates(List<Monster> currentMonsters, Tower[][] currentTowers, Point2D currentEntrance, 
			Point2D currentExit, int currentWaveNumber, List<WaveSpawnSchema> allCurrentWaves, double currentGameClock, 
			int playerMoney, int playerLife, double playerScore) {
		monsters = currentMonsters;
		towers = currentTowers;
		entrance = currentEntrance;
		exit = currentExit;
		currentWave = currentWaveNumber;
		allWaves = allCurrentWaves;
		gameClock = currentGameClock;
		money = playerMoney;
		life = playerLife;
		score = playerScore;
	}

}
