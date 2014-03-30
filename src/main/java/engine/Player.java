package main.java.engine;

import java.util.HashMap;
import java.util.Map;

import main.java.engine.objects.Tower;

import jgame.JGObject;

public class Player {
	private double money;
	private int health;
	private double score;
	private Map<String, Integer> skills;
	
	public Player() {
		this.money = 0;
		this.health = 3;
		this.score = 0;
		this.skills = new HashMap<String, Integer>();
	}
	
	public void updateMoney(double money) {
		this.money += money;
	}
	
	public void updateHealth() {
		this.health -= 1;
	}
	
	public void updateScore(double score) {
		this.score += score;
	}
	
	public void updateSkills(String skill) {
		if (this.skills.containsKey(skill)) this.skills.put(skill, this.skills.get(skill)+1);
		else this.skills.put(skill, 1);
	}
	
	public double getMoney() {
		return this.money;
	}
	
	public double getHealth() {
		return this.health;
	}
} 
