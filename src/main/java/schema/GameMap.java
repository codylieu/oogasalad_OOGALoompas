package main.java.schema;

import main.java.author.view.tabs.terrain.Tile;
import main.java.author.view.tabs.terrain.TileMap;

import java.io.Serializable;
import java.util.List;

/**
 * Wrapper for the tiles corresponding to a terrain map
 *
 * TODO: change to standard schema format; i will do this later (hopefully) -jordan
 * 
 */
public class GameMap implements Serializable {
	private Tile[][] myTiles;
    private List<TileMap> myTileMaps;

	public Tile[][] getMyTiles() {
		return myTiles;
	}

	public void setMyTiles(Tile[][] myTiles) {
		this.myTiles = myTiles;
	}

    public List<TileMap> getMyTileMaps() {
        return myTileMaps;
    }

    public void setMyTileMaps(List<TileMap> myTileMaps) {
        this.myTileMaps = myTileMaps;
    }
}
