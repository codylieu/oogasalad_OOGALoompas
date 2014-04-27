package main.java.engine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jgame.JGObject;

/**
 * Represents a player in the game. 
 * Keeps track of the player's stats. 
 *
 */
public class Player implements Serializable {
	private static final int DEFAULT_INITIAL_LIVES_NUM = 3;
	private int money;
	private int lives;
	private double score;
	private Map<String, Integer> skills;
	
	public Player() {
		this.money = 300;
		this.lives = DEFAULT_INITIAL_LIVES_NUM;
		this.score = 0;
		this.skills = new HashMap<String, Integer>();
	}
	
	/**
	 * Constructor to be used in the loadGameBlueprint method in Model
	 * @param money
	 * @param lives
	 */
	public Player(Integer m, Integer l)	{
		this.money = m;
		this.lives = l;
		this.score = 0;
		this.skills = new HashMap<String, Integer>();
	}
	
	/**
	 * Increase score by one.
	 */
	public void incrementScore() {
		this.score ++;
	}
	
	/**
	 * Update the skills and associated level of the player. 
	 * @param skill
	 */
	public void updateSkills(String skill) {
		if (this.skills.containsKey(skill)) this.skills.put(skill, this.skills.get(skill)+1);
		else this.skills.put(skill, 1);
	}
	
	/**
	 * Get the amount of money left. 
	 * @return
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Change player's money by this value.
	 * Positive for increasing, negative for decreasing.
	 * @param value
	 */
	public void changeMoney(double value) {
		this.money+=value;
	}
	
	/**
	 * Get the number of remaining lives of the player.
	 * @return
	 */
	public int getLivesRemaining() {
		return this.lives;
	}
	
	
	/**
	 * Decrease the player's lives by 1.
	 */
	public void decrementLives(){
	    lives--;
	}
	
	/**
	 * Increase the player's lives by 1.
	 */
	public void incrementLives() {
		lives++;
	}

	/**
	 * Get the score of the player.
	 * @return
	 */
	public double getScore() {
		return this.score;
	}
} 
