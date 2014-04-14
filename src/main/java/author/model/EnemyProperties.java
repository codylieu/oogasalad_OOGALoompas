package main.java.author.model;

import java.util.ArrayList;

public class EnemyProperties {
	private String myId;
	private int myDamage;
	private int myHealth;
	private int mySpeed;
	
	private EnemyType myType;
	
	private ArrayList<String> myAttributes;
	
	public EnemyProperties(String id) {
		myId = id;
		myAttributes = new ArrayList<String>();
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

