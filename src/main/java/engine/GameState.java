package main.java.engine;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.List;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.ITower;
import main.java.schema.WaveSpawnSchema;


/**
 * A container for all the current game state data for DataHandler to save/serialize to file.
 * 
 */
public class GameState implements Serializable {
    // model specific
    private ITower[][] towers;
    private Point2D entrance;
    private Point2D exit;
    private int currentWave;
    private List<WaveSpawnSchema> allWaves;
    private double gameClock;

    // player specific
    private int money;
    private int lives;
    private double score;

    /**
     * Update the current game states.
     * This method is called before saving the game state to file.
     * 
     * @param currentTowers
     * @param currentEntrance
     * @param currentExit
     * @param currentWaveNumber
     * @param allCurrentWaves
     * @param currentGameClock
     * @param playerMoney
     * @param playerLives
     * @param playerScore
     */
    public void updateGameStates (ITower[][] currentTowers,
                                  int currentWaveNumber,
                                  List<WaveSpawnSchema> allCurrentWaves,
                                  double currentGameClock,
                                  int playerMoney,
                                  int playerLives,
                                  double playerScore) {
        towers = currentTowers;
        currentWave = currentWaveNumber;
        allWaves = allCurrentWaves;
        gameClock = currentGameClock;
        money = playerMoney;
        lives = playerLives;
        score = playerScore;
    }

    public ITower[][] getTowers () {
        return towers;
    }

    public Point2D getEntrance () {
        return entrance;
    }

    public Point2D getExit () {
        return exit;
    }

    public int getCurrentWaveNumber () {
        return currentWave;
    }

    public List<WaveSpawnSchema> getAllWaveSchemas () {
        return allWaves;
    }

    public double getGameClock () {
        return gameClock;
    }

    public int getMoney () {
        return money;
    }

    public int getLives () {
        return lives;
    }

    public double getScore () {
        return score;
    }

}
