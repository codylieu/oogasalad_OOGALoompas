package main.java.engine;

import main.java.engine.objects.CollisionManager;

/** 
 * A TD player must support the methods of this interface. 
 * Each player maintains a list of game stats and dynamically
 * keep track of them. 
 * Possible players: human player and AI player
 * @author lawrencelin
 *
 */
public interface IPlayer {
	
	/** 
	 * Get the score of this player. 
	 * @return current score of the player
	 */
	public double getScore();
	
	/** 
	 * Get the health of this player
	 * @return current health level of player
	 */
	public double getHealth();
	
	/**
	 * Call to update current stats associated
	 * with the player
	 */
	public void updateGameStats();
	
	/**
	 * Return the name of the player
	 * @return the name of this player
	 */
	public String getName();
	
	/**
	 * Check whether the game is lost
	 * @return true if the player is dead
	 */
	public boolean isGameLost();
}
