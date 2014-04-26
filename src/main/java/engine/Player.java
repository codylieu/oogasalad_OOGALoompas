package main.java.engine;

import java.util.HashMap;
import java.util.Map;

import jgame.JGObject;

public class Player {
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
	
	public void updateMoney(double money) {
		this.money += money;
	}
	
	public void lostLife() {
		this.lives -= 1;
	}
	
	public void addScore(double score) {
		this.score += score;
	}
	
	public void updateSkills(String skill) {
		if (this.skills.containsKey(skill)) this.skills.put(skill, this.skills.get(skill)+1);
		else this.skills.put(skill, 1);
	}
	
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
	
	public int getLivesRemaining() {
		return this.lives;
	}
	
	
	public void decrementLives(){
	    lives--;
	}
	
	public void incrementLives() {
		lives++;
	}
	
	public void increaseLives(double changeAmount) {
		lives+=changeAmount;
	}

	public double getScore() {
		return this.score;
	}
} 
