package main.java.author.model;

import java.util.ArrayList;

public class TowerProperties {
	private String myId;
	private int myDamage;
	private int myHealth;
	private int myFireRate;
	
	private TowerType myType;
	
	private ArrayList<String> myAttributes;
	
	public TowerProperties(String id) {
		myId = id;
		myAttributes = new ArrayList<String>();
	}

	public String getId() {
		return myId;
	}
	
	public int getHealth() {
		return myHealth;
	}
	
	public int getFireRate() {
		return myFireRate;
	}
	
	public void setHealth(int health){
		myHealth = health;
	}
	
	public void setFireRate(int speed) {
		myFireRate = speed;
	}
	
	public void setDamage(int damage) {
		myDamage = damage;
	}

}

