package main.java.schema;

import main.java.author.view.tabs.terrain.Tile;

/**
 * Wrapper for the tiles corresponding to a terrain map
 * 
 */
public class GameMap {
	private Tile[][] myTiles;

	public GameMap(Tile[][] tiles) {
		myTiles = tiles;
	}

	public Tile[][] getMyTiles() {
		return myTiles;
	}

	public void setMyTiles(Tile[][] myTiles) {
		this.myTiles = myTiles;
	}
}
