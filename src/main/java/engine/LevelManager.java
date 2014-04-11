package main.java.engine;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import main.java.engine.factory.TDObjectFactory;
import main.java.engine.objects.Exit;
import main.java.engine.objects.monster.Monster;
import main.java.exceptions.engine.MonsterCreationFailureException;
import main.java.schema.MonsterSpawnSchema;
import main.java.schema.TDObjectSchema;
import main.java.schema.WaveSpawnSchema;


public class LevelManager {

    private int myCurrentWave;
    private List<WaveSpawnSchema> myAllWaves;
    private TDObjectFactory myFactory;
    private Point2D entrance;
    private Point2D exit;
    private Player myPlayer;

    /**
     * Tasked with managing state for levels/waves/lives and spawning waves of monsters.
     */
    public LevelManager (TDObjectFactory factory) {
        myFactory = factory;
        myCurrentWave = 0;
        myAllWaves = new ArrayList<WaveSpawnSchema>();
    }

    /**
     * Add a new wave to be spawned by the level manager.
     * 
     * @param newWave
     */
    public void addNewWave (WaveSpawnSchema newWave) {
        myAllWaves.add(newWave);
    }

    /**
     * Spawns the next wave in the list of all waves.
     * Currently rotates through all waves indefinitely.
     * 
     * @throws MonsterCreationFailureException
     */
    public Collection<Monster> spawnNextWave () throws MonsterCreationFailureException {
        Collection<Monster> spawnedMonsters = new ArrayList<Monster>();
        for (MonsterSpawnSchema spawnSchema : myAllWaves.get(myCurrentWave)
                .getMonsterSpawnSchemas()) {
            for (int i = 0; i < spawnSchema.getSwarmSize(); i++) {
                Exit monsterExit = new Exit(exit.getX(), exit.getY(), this);
                Monster newlyAdded =
                        myFactory.placeMonster(entrance, monsterExit,
                                               (String) spawnSchema.getMonsterSchema()
                                                       .getAttributesMap().get(TDObjectSchema.NAME));
                spawnedMonsters.add(newlyAdded);
            }
            if (++myCurrentWave >= myAllWaves.size()) {
                myCurrentWave = 0;
            }
        }
        return spawnedMonsters;
    }

    /**
     * Returns whether or not all waves completed.
     * 
     * @return
     */
    public boolean checkAllWavesFinished () {
        return myCurrentWave >= myAllWaves.size();
    }

    /**
     * Set the monsters' entrance
     * 
     * @param x
     * @param y
     */
    public void setEntrance (double x, double y) {
        this.entrance = new Point2D.Double(x, y);
    }

    /**
     * Set the monsters' exit(destination)
     * 
     * @param x
     * @param y
     */
    public void setExit (double x, double y) {
        this.exit = new Point2D.Double(x, y);
    }

    /**
     * Called when monster successfully reaches and triggers exit,
     * and acts on the player, if any
     */
    public void monsterExitAction () {
        if (myPlayer != null) {
            myPlayer.decrementLives();
        }
    }

    /**
     * Returns wave number currently on
     * 
     * @return
     */
    public int getCurrentWave () {
        return myCurrentWave;
    }

    /**
     * Returns unmodifiable list of all current wave spawn schemas in the level manager
     * 
     * @return
     */
    public List<WaveSpawnSchema> getAllWaves () {
        return Collections.unmodifiableList(myAllWaves);
    }

    /**
     * Register a player to be accounted for when monster reaches exit
     * @param player
     */
    public void registerPlayer (Player player) {
        myPlayer = player;

    }

}
