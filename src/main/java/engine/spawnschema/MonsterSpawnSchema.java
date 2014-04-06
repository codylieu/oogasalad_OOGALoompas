package main.java.engine.spawnschema;

import main.java.schema.MonsterSchema;

/**
 * A schema object/container to store information about which monsterschema to create, and how many of it to be
 * created per swarm.
 * 
 * @author Austin
 * 
 */
public class MonsterSpawnSchema {

    private MonsterSchema myMonsterSchema;
    private int mySwarmSize;


    /**
     * 
     * @param monsterToCreate String representation for Factory creation
     * @param swarmSize how many of the specified monster to be created
     */
    public MonsterSpawnSchema (String name, MonsterSchema monsterSchema, int swarmSize) {
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
