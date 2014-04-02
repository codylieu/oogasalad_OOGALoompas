package main.java.engine.spawnschema;

import java.util.ArrayList;
import java.util.Collection;

import jgame.impl.JGEngineInterface;
import main.java.engine.factories.MonsterFactory;
import main.java.engine.objects.monster.Monster;

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
//        myFactory = new MonsterFactory(engine);
        myFactory = new MonsterFactory();
    }

    /**
     * Create the monster swarm specified, and return a list of all newly created monsters
     */
    public Collection<Monster> spawn () {
    	Collection<Monster> newlyAdded = new ArrayList<Monster>();
        for (int i = 0; i < mySwarmSize; i++) {
        	newlyAdded.add(myFactory.placeMoster());
        }
		return newlyAdded;
    }

}
