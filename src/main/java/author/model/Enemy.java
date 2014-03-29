package main.java.author.model;

import java.util.ArrayList;

public class Enemy {
	private String myId;
	private int myDamage;
	private int myHealth;
	private int mySpeed;
	
	private EnemyType myType;
	
	private ArrayList<String> myAttributes;
	
	public Enemy(String id) {
		myId = id;
		myAttributes = new ArrayList<String>();
	}
	
	public void setAttributes() {
		
	}

	public String getId() {
		return myId;
	}
	
	public int getHealth() {
		return myHealth;
	}
	
	public int getSpeed() {
		return mySpeed;
	}
	
	public void setHealth(int health){
		myHealth = health;
	}
	
	public void setSpeed(int speed) {
		mySpeed = speed;
	}
	
	public void setDamage(int damage) {
		myDamage = damage;
	}
}

