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
    
    private List<TileSchema> myTileSchemas;
    private List<TileMapSchema> myTileMapSchemas;
    

	public List<TileMapSchema> getTileMapSchemas() {
		return myTileMapSchemas;
	}

	public void setTileMapSchemas(List<TileMapSchema> tileMapSchemas) {
		this.myTileMapSchemas = tileMapSchemas;
	}

    public List<TileSchema> getTileSchemas() {
        return myTileSchemas;
    }

    public void setTileSchemas(List<TileSchema> tileSchemas) {
        this.myTileSchemas = tileSchemas;
    }
}
