package main.java.author.model;

import java.util.ArrayList;

public class Enemy {
	private int myId;
	private int myHealth;
	
	private EnemyType myType;
	
	private ArrayList<String> myAttributes;
	
	public Enemy(int id) {
		myAttributes = new ArrayList<String>();
	}
	
	public void setAttributes() {
		
	}
	
	public int getHealth() {
		return myHealth;
	}
	
	public int getFireRate() {
		return myFireRate;
	}
	
	public int getRange() {
		return myRange;
	}
}

