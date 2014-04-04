package main.java.engine;

import java.util.HashMap;
import java.util.Map;

import jgame.JGObject;

public class Player {
	private static final int DEFAULT_INITIAL_LIVES_NUM = 3;
	private int money;
	private int life;
	private double score;
	private Map<String, Integer> skills;
	
	public Player() {
		this.money = 300;
		this.life = DEFAULT_INITIAL_LIVES_NUM;
		this.score = 0;
		this.skills = new HashMap<String, Integer>();
	}
	
	public void updateMoney(double money) {
		this.money += money;
	}
	
	public void lostLife() {
		this.life -= 1;
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
	
	public void addMoney(double value) {
		this.money+=value;
	}
	
	public int getLife() {
		return this.life;
	}

	public double getScore() {
		return this.score;
	}
} 
