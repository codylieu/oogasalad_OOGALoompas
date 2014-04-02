package main.java.author.model;

/**
 * the higher level settings defining the type of game and some basic
 * overarching stats
 */
public class GameScenario {

	private int myStartingLives;
	private int myStartingGold;
	private int myLevelDifficulty;
	private boolean isSurvivalMode;

	public int getMyStartingLives() {
		return myStartingLives;
	}

	public void setMyStartingLives(int myStartingLives) {
		this.myStartingLives = myStartingLives;
	}

	public int getMyStartingGold() {
		return myStartingGold;
	}

	public void setMyStartingGold(int myStartingGold) {
		this.myStartingGold = myStartingGold;
	}

	public int getMyLevelDifficulty() {
		return myLevelDifficulty;
	}

	public void setMyLevelDifficulty(int myLevelDifficulty) {
		this.myLevelDifficulty = myLevelDifficulty;
	}

	public boolean isSurvivalMode() {
		return isSurvivalMode;
	}

	public void setSurvivalMode(boolean isSurvivalMode) {
		this.isSurvivalMode = isSurvivalMode;
	}

}
