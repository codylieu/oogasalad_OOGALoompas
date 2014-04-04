package main.java.engine.spawnschema;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

import jgame.impl.JGEngineInterface;
import main.java.engine.factory.TDObjectFactory;
import main.java.engine.objects.monster.Monster;
import main.java.exceptions.engine.MonsterCreationFailureException;

/**
 * A schema object to store information about which monster to create, and how many of it to be
 * created per swarm.
 * 
 * @author Austin
 * 
 */
public class MonsterSpawnSchema {
	private TDObjectFactory myFactory;
    private String myMonsterName;
    private int mySwarmSize;
    private Point2D myEntrance;
    private Point2D myExit;

    /**
     * 
     * @param monsterToCreate String representation for Factory creation
     * @param swarmSize how many of the specified monster to be created
     */
    public MonsterSpawnSchema (TDObjectFactory factory, String monsterToCreate, int swarmSize, Point2D entrance, Point2D exit) {
    	//TODO: Remove factory from spawn schema. Schema should only bundle data
    	myFactory = factory;
        myMonsterName = monsterToCreate;
        mySwarmSize = swarmSize;
        myEntrance = entrance;
        myExit = exit;
    }

    /**
     * Create the monster swarm specified, and return a list of all newly created monsters
     * @throws MonsterCreationFailureException 
     */
    public Collection<Monster> spawn () throws MonsterCreationFailureException {
    	Collection<Monster> newlyAdded = new ArrayList<Monster>();
        for (int i = 0; i < mySwarmSize; i++) {
        	newlyAdded.add(myFactory.placeMonster(myEntrance, myExit, "test monster 1"));
        }
		return newlyAdded;
    }

}
