package main.java.engine.spawnschema;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

import jgame.impl.JGEngineInterface;
import main.java.engine.factory.MonsterFactory;
import main.java.engine.objects.monster.Monster;

/**
 * A schema object to store information about which monster to create, and how many of it to be
 * created per swarm.
 * 
 * @author Austin
 * 
 */
public class MonsterSpawnSchema {
	
	private JGEngineInterface engine;
    private String myMonsterName;
    private int mySwarmSize;
    private MonsterFactory myFactory;
    private Point2D myEntrance;
    private Point2D myExit;

    /**
     * 
     * @param monsterToCreate String representation for Factory creation
     * @param swarmSize how many of the specified monster to be created
     */
    public MonsterSpawnSchema (JGEngineInterface engine, String monsterToCreate, int swarmSize, Point2D entrance, Point2D exit) {
    	this.engine = engine;
        myMonsterName = monsterToCreate;
        mySwarmSize = swarmSize;
        myFactory = new MonsterFactory(engine);
        myEntrance = entrance;
        myExit = exit;
    }

    /**
     * Create the monster swarm specified, and return a list of all newly created monsters
     */
    public Collection<Monster> spawn () {
    	Collection<Monster> newlyAdded = new ArrayList<Monster>();
        for (int i = 0; i < mySwarmSize; i++) {
        	newlyAdded.add(myFactory.placeMonster(myEntrance, myExit));
        }
		return newlyAdded;
    }

}
