package main.java.schema;

import main.java.author.view.tabs.terrain.Tile;
import main.java.author.view.tabs.terrain.TileDisplay;

import java.util.List;

/**
 * Wrapper for the tiles corresponding to a terrain map
 *
 * TODO: change to standard schema format; i will do this later (hopefully) -jordan
 * 
 */
public class GameMap {
	private Tile[][] myTiles;
    private List<TileDisplay> myTileDisplays;

	public Tile[][] getMyTiles() {
		return myTiles;
	}

	public void setMyTiles(Tile[][] myTiles) {
		this.myTiles = myTiles;
	}

    public List<TileDisplay> getMyTileDisplays() {
        return myTileDisplays;
    }

    public void setMyTileDisplays(List<TileDisplay> myTileDisplays) {
        this.myTileDisplays = myTileDisplays;
    }
}
