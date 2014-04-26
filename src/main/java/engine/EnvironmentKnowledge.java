package main.java.engine;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import main.java.engine.objects.Exit;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.ITower;


/**
 * 
 * Use EnvironmentKnowledge to abstract the passing of limited information into Tower and/or Monster
 * behavior method calls.
 * 
 * Goal is to give access to information about the environment of objects,
 * and change their state with controlled methods,
 * but not the ability nor access to directly modify the environmental objects themselves.
 * 
 * Contains helper methods to return info which relies on knowledge unknown to individual objects
 * (typically aggregate info about the entire field of towers and monsters)
 * and methods to change state of environmental objects in a controlled manner.
 * 
 */
public class EnvironmentKnowledge {

    List<Monster> allMonsters;
    Player currentPlayer;
    ITower[][] allTowers;
    Exit exit;

    /**
     * Add necessary state info.
     * 
     * @param allMonsters
     */
    public EnvironmentKnowledge (List<Monster> allMonsters, Player currentPlayer, ITower[][] towers, Exit exit) {

        // add more parameters as necessary

        this.allMonsters = allMonsters;
        this.currentPlayer = currentPlayer;
        this.allTowers = towers;
        this.exit = exit;

    }

    /**
     * Give the current player a specified amount of money.
     * 
     * @param grantedMoney money to be given to player
     */
    public void grantPlayerMoney (int grantedMoney) {
        currentPlayer.changeMoney(grantedMoney);
    }
    
    /**
     * Returns the list of all the monsters on map
     * 
     * @return list of monsters
     */
    public List<Monster> getAllMonsters() {
    	return allMonsters;
    }
    
    /**
     * Returns the array representing all towers on map
     * 
     * @return an array of towers
     */
    public ITower[][] getAllTowers() {
    	return allTowers;
    }
    
    /**
     * Returns the exit object
     * 
     * @return the exit object
     */
    public Exit getExit() {
    	return exit;
    }
    
    /**
     * Add lives to the current player
     */
    public void grantPlayerLife(double livesToGrant) {
    	currentPlayer.increaseLives(livesToGrant);
    }

}
