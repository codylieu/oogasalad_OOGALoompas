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
import main.java.schema.GameBlueprint;
import main.java.schema.MonsterSpawnSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.WaveSpawnSchema;


/**
 * Manages the state of the game for each level. 
 *
 */
public class LevelManager {

    private int myCurrentWave;
    private List<WaveSpawnSchema> myAllWaves;
    private TDObjectFactory myFactory;
    private Point2D entrance;
    private Exit exit;
    private Player myPlayer;
    private boolean survivalMode;

    /**
     * Tasked with managing state for levels/waves/lives and spawning waves of monsters.
     */
    public LevelManager (TDObjectFactory factory) {
        myFactory = factory;
        myCurrentWave = 0;
        myAllWaves = new ArrayList<WaveSpawnSchema>();
        survivalMode = true;
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
            spawnedMonsters.addAll(spawnMonsterSpawnSchema(spawnSchema));
            if (++myCurrentWave >= myAllWaves.size()) {
            	if(survivalMode) {
            		//reset current wave back to beginning
            		myCurrentWave = 0;
            	}
            }
        }
        return spawnedMonsters;
    }
    
    /**
     * Spawn a particular monster spawn schema with the level manager's set entrance.
     * @param spawnSchema
     * @return
     * @throws MonsterCreationFailureException
     */
    public List<Monster> spawnMonsterSpawnSchema(MonsterSpawnSchema spawnSchema)
			throws MonsterCreationFailureException {
		return spawnMonsterSpawnSchema(spawnSchema, entrance);
	}
    
    
	/**
	 * Spawn a particular spawn schema. This can be called to spawn a monsters
	 * schema out of sync with wave spawns.
	 *
	 * Return list of newly spawned monsters.
	 * 
	 * @param spawnSchema
	 * @return
	 * @throws MonsterCreationFailureException
	 */
	public List<Monster> spawnMonsterSpawnSchema(MonsterSpawnSchema spawnSchema, Point2D newEntrance)
			throws MonsterCreationFailureException {
		List<Monster> spawnedMonsters = new ArrayList<Monster>();
		for (int i = 0; i < spawnSchema.getSwarmSize(); i++) {

		    Monster newlyAdded =
		            myFactory.placeMonster(newEntrance, exit,
		                                   (String) spawnSchema.getMonsterSchema()
		                                           .getAttributesMap().get(TDObjectSchema.NAME));
		    spawnedMonsters.add(newlyAdded);
		}
		return spawnedMonsters;
	}


    /**
     * Returns whether or not the game is won,
     * i.e. all waves are completed
     * This will always return false on survival mode.
     * @return whether or not game is over 
     */
    public boolean isGameWon () {
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
//        this.exit = new Point2D.Double(x, y);
    	this.exit = new Exit(x, y, this);
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
    
    /**
     * Get the exit for the current level
     * 
     * @return exit object of the current level
     */
    public Exit getExit() {
    	return exit;
    }

    /**
     * Do a clean load of the waves for the game, and reset the initial wave to start at in the new
     * list (indexed from 0).
     * Note: this will remove all current waves queued in the level manager.
     * 
     * @param waveSchemas list of wave spawn schemas
     * @param initialWave
     */
    public void cleanLoadWaveSchemas (List<WaveSpawnSchema> waveSchemas,
                                      int initialWave) {
        myAllWaves.clear();
        for (WaveSpawnSchema wave : waveSchemas) {
            addNewWave(wave);
        }
        myCurrentWave = initialWave;
    }
    
	/**
	 * Set the survival mode.
	 * @param survivalMode
	 */
	public void setSurvivalMode(boolean survivalMode){
		this.survivalMode = survivalMode;
	}

}
