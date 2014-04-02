package main.java.engine.spawnschema;

import main.java.engine.factories.MonsterFactory;

/**
 * A schema object to store information about which monster to create, and how many of it to be
 * created per swarm.
 * 
 * @author Austin
 * 
 */
public class MonsterSpawnSchema {

    private String myMonsterName;
    private int mySwarmSize;

    private MonsterFactory myFactory;

    /**
     * 
     * @param monsterToCreate String representation for Factory creation
     * @param swarmSize how many of the specified monster to be created
     */
    public MonsterSpawnSchema (String monsterToCreate, int swarmSize) {
        myMonsterName = monsterToCreate;
        mySwarmSize = swarmSize;
    }

    /**
     * Create the monster swarm specified
     */
    public void spawn () {
        for (int i = 0; i < mySwarmSize; i++) {
            // myFactory.create(myMonsterName);
        	myFactory.placeMoster();
        }
    }

}
