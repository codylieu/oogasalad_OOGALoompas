package main.java.author.model;

import java.util.ArrayList;

/**
 * This is a settings object for a specific type of Enemy like a Spider, and at
 * a high level is a wrapper for a bunch of key value pairs that the Engine will
 * need to reference to create TDObjects. This class is not a specific instance
 * of an Enemy. Please refer to the Game Engine's TDObjects for the objects
 * related to ones you will see onscreen.
 */
public class EnemySchema {
	private String myId;
	private int myDamage;
	private int myHealth;
	private int mySpeed;

	private EnemyType myType;

	private ArrayList<String> myAttributes;

	public EnemySchema(String id) {
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

	public void setHealth(int health) {
		myHealth = health;
	}

	public void setSpeed(int speed) {
		mySpeed = speed;
	}

	public void setDamage(int damage) {
		myDamage = damage;
	}
}
