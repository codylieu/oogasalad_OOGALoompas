package main.java.engine.objects.monster.jgpathfinder;

import jgame.JGPoint;

import java.util.List;

public interface JGTileMapInterface {
	/**
	 * Get the number of x tiles in a tilemap.
	 * @return The number of x tiles in the tilemap
	 */
	public int getNumXTiles();

	/**
	 * Get the number of y tiles in a tilemap.
	 * @return The number of y tiles in the tilemap
	 */
	public int getNumYTiles();

	/**
	 * Get the indexes of the neighbors of the given tile. Neighbors can be defined
	 * and implemented in any way such as direct neighbors, diagonals, only tiles
	 * two blocks away, and etc.
	 *
	 * @param tile The tile index you want the neighbors of
	 * @return A list of indexes of the neighboring tiles
	 */
	public List<JGPoint> getNeighbors(JGPoint tile);

	/**
	 * Get the cost of moving on a given tile (for example a grass tile can have a cost of
	 * 1 while a mud tile can have a cost of 4).
	 *
	 * @param source The tile that you are moving on
	 * @return The cost to move on that tile
	 */
	public int getCostToMove(JGPoint source);
}
