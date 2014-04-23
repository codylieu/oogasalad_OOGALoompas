package main.java.engine;

import java.io.IOException;
import java.util.List;

import main.java.exceptions.engine.InvalidSavedGameException;
import main.java.exceptions.engine.MonsterCreationFailureException;
import main.java.exceptions.engine.TowerCreationFailureException;

public interface IModel {
	/**
	 * Add a new player to the engine
	 */
	public void addNewPlayer ();

	/**
	 * Add a tower at the specified location. If tower already exists in that cell, do nothing.
	 * 
	 * @param x x coordinate of the tower
	 * @param y y coordinate of the tower
	 * @param towerName Type tower to be placed
	 */
	public boolean placeTower (double x, double y, String towerName);


	/**
	 * Check if there's a tower present at the specified coordinates
	 * This is mainly for the view to do a quick check
	 * 
	 * @param x
	 * @param y
	 * @return true if there is a tower
	 */
	public boolean isTowerPresent (double x, double y);
	/**
	 * Get the information of the TDObject, if any, 
	 * at the specified coordinates
	 * 
	 * @param x
	 * @param y
	 * @return The information that we want to display to the player
	 */
	public List<String> getUnitInfo(double x, double y);



	/**
	 * Check if the current location contains any tower. If yes, remove it. If no, do nothing
	 * 
	 * @param x
	 * @param y
	 */
	public void checkAndRemoveTower (double x, double y);

	// TODO: use this instead of other one, will change -jordan
	public void loadMapTest (String fileName);
	/**
	 * Deserialize and load into the engine the GameBlueprint obtained from the file path
	 * 
	 * @param filePath File path of the blueprint to be loaded
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public void loadGameBlueprint (String filePath) throws ClassNotFoundException, IOException;
	/**
	 * Reset the game clock
	 */
	public void resetGameClock ();

	public void addScore (double score);

	/**
	 * Get the score of the player
	 * 
	 * @return player's current score
	 */
	public double getScore ();

	/**
	 * Check whether the game is lost
	 * 
	 * @return true if game is lost
	 */
	public boolean isGameLost ();


	/**
	 * Get the game clock
	 * 
	 * @return current game clock
	 */
	public double getGameClock ();

	/**
	 * Get the number of remaining lives of the player
	 * 
	 * @return number of lives left
	 */
	public int getPlayerLives ();

	/**
	 * Get the amount of money obtained by the player
	 * 
	 * @return current amount of money
	 */
	public int getMoney ();
	/**
	 * Returns whether or not the player has complete all waves and thus has won
	 * the game. This will always return false on survival mode.
	 * 
	 * @return boolean of whether game is won (all waves completed)
	 */
	public boolean isGameWon();

	/**
	 * Set whether or not the game is played on survival mode.
	 * @param survivalMode
	 * @return
	 */
	public void setSurvivalMode(boolean survivalMode);

	/**
	 * Spawns a new wave
	 * 
	 * @throws MonsterCreationFailureException
	 */
	public void doSpawnActivity () throws MonsterCreationFailureException;
	/**
	 * The model's "doFrame()" method that updates all state, spawn monsters,
	 * etc.
	 * 
	 * @throws MonsterCreationFailureException
	 */
	public void updateGame () throws MonsterCreationFailureException;

	/**
	 * Place an item at the specified location.
	 * If it costs more than the player has, do nothing.
	 * 
	 * @param name
	 * @param x
	 * @param y
	 */
	public boolean placeItem (String name, double x, double y);


	/**
	 * Check all collisions specified by the CollisionManager
	 */
	public void checkCollisions ();
	/**
	 * Upgrade the tower at the specified coordinates and return true if upgraded successfully.
	 * If not possible, does nothing, and this method returns false.
	 * 
	 * @param x x-coordinate of tower to be upgraded
	 * @param y y-coordinate of tower to be upgraded
	 * @return boolean whether or not the tower was successfully upgraded
	 * @throws TowerCreationFailureException
	 */
	public boolean upgradeTower (double x, double y) throws TowerCreationFailureException;


	/**
	 * Decrease player's lives by one.
	 */
	public void decrementLives ();



	/**
	 * A list of names of possible towers to create
	 * 
	 * @return
	 */
	public List<String> getPossibleTowers ();

	/**
	 * A list of names of possible items to create
	 * 
	 * @return
	 */
	public List<String> getPossibleItems ();

	/**
	 * Save the present game state to a loadable file.
	 * Note: all saved game files saved to under resources folder.
	 * 
	 * @param gameName the file name to save the current game under.
	 * @throws InvalidSavedGameException Problem saving the game
	 */
	public void saveGame (String gameName) throws InvalidSavedGameException;

	/**
	 * Clears current game and restarts a new game based on loaded saved game.
	 * Only valid saved game files in the resources folder can be loaded.
	 * Pass in the file's name only (e.g. which can be chosen through JFileChooser)
	 * 
	 * @param filename The full filename only.
	 * @throws InvalidSavedGameException issue loading the game,
	 *         (please pause and notify the player, then continue the present game).
	 */
	public void loadSavedGame (String filename) throws InvalidSavedGameException;

}
