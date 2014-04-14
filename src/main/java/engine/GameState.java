package main.java.engine;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.List;

import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.SimpleTower;
import main.java.engine.objects.tower.ITower;
import main.java.schema.WaveSpawnSchema;

/**
 * Store all the current game states so that Data can save
 *
 */
public class GameState implements Serializable {
	// model specific
	private List<Monster> monsters; 
	private ITower[][] towers;
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
	public void updateGameStates(List<Monster> currentMonsters, ITower[][] currentTowers, int currentWaveNumber, List<WaveSpawnSchema> allCurrentWaves, double currentGameClock, 
			int playerMoney, int playerLife, double playerScore) {
		monsters = currentMonsters;
		towers = currentTowers;
		currentWave = currentWaveNumber;
		allWaves = allCurrentWaves;
		gameClock = currentGameClock;
		money = playerMoney;
		life = playerLife;
		score = playerScore;
	}

	public int getCurrentWaveNumber() {
		return currentWave;
	}
}
