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

    private ITower[][] towers;
    private Point2D entrance;
    private Point2D exit;
    private int currentWave;
    private List<WaveSpawnSchema> allWaves;
    private double gameClock;
    private Player player;

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
     * @param currentPlayer the Player object with state to be saved
     */
    public void updateGameStates (ITower[][] currentTowers,
                                  int currentWaveNumber,
                                  List<WaveSpawnSchema> allCurrentWaves,
                                  double currentGameClock,
                                  Player currentPlayer) {
        towers = currentTowers;
        currentWave = currentWaveNumber;
        allWaves = allCurrentWaves;
        gameClock = currentGameClock;
        player = currentPlayer;
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

    public Player getPlayer () {
        return player;
    }
}
