package main.java.author.model;

import java.util.ArrayList;

/**
 * 
 * This is a settings object for a specific type of Tower like a Fire Tower, and at
 * a high level is a wrapper for a bunch of key value pairs that the Engine will
 * need to reference to create TDObjects. This class is not a specific instance
 * of a Tower. Please refer to the Game Engine's TDObjects for the objects
 * related to ones you will see onscreen.
 */
public class TowerSchema {
	private int myId;
	private int myHealth;
	private int myFireRate;
	private int myRange;
	
	private TowerType myType;
	
	private ArrayList<String> myAttributes;
	
	public TowerSchema(int id) {
		myAttributes = new ArrayList<String>();
		myId = id;
	}
	
	public void setAttributes() {
		
	}
	
	public int getId() {
		return myId;
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

