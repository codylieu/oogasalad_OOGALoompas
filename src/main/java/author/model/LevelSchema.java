package main.java.author.model;

import java.util.List;

/**
 * A data wrapper with everything you need to define what a level is
 * 
 */
public class LevelSchema {
	private int myLevelNumber;

	public int getMyLevelNumber() {
		return myLevelNumber;
	}

	public void setMyLevelNumber(int myLevelNumber) {
		this.myLevelNumber = myLevelNumber;
	}

	public List<EnemySwarm> getMyMonsterSwarms() {
		return myMonsterSwarms;
	}

	public void setMyMonsterSwarms(List<EnemySwarm> myMonsterSwarms) {
		this.myMonsterSwarms = myMonsterSwarms;
	}

	private List<EnemySwarm> myMonsterSwarms;

}
