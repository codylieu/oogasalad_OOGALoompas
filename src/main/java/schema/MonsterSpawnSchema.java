package main.java.schema;

import java.io.Serializable;

import main.java.schema.tdobjects.MonsterSchema;

/**
 * A schema object/container to store information about which monsterschema to create, and how many of it to be
 * created per swarm.
 * 
 * @author Austin
 */
public class MonsterSpawnSchema implements Serializable {
    private MonsterSchema myMonsterSchema;
    private int mySwarmSize;

    /**
     * Create a new MonsterSpawnSchema with the specified monster and
     * number to be spawned
     *
     * @param monsterSchema MonsterSchema of monster to be created
     * @param swarmSize How many of the specified monster to be created
     */
    public MonsterSpawnSchema (MonsterSchema monsterSchema, int swarmSize) {
    	myMonsterSchema = monsterSchema;
        mySwarmSize = swarmSize;
    }

    /**
     * Get how many of this monster to create
     */
    public int getSwarmSize(){
        return mySwarmSize;
    }
    
    /**
     * Get the schema of the monster to be created by the factory
     */
    public MonsterSchema getMonsterSchema(){
        return myMonsterSchema;
    }
}
