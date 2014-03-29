package main.java.author.model;

import java.util.ArrayList;

public class Tower {
	private int myId;
	private int myHealth;
	private int myFireRate;
	private int myRange;
	
	private TowerType myType;
	
	private ArrayList<String> myAttributes;
	
	public Tower(int id) {
		myAttributes = new ArrayList<String>();
		myId = id;
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

