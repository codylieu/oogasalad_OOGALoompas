package main.java.author.view.tabs.terrain;

import java.io.Serializable;

//TODO : probably replace with schema, better called tilemapinfo - jordan
public class TileMap implements Serializable {
    private int myNumXTiles;
    private int myNumYTiles;
    private int myPixelSize;
    private String myTileMapFile;

    public TileMap(int myNumXTiles, int myNumYTiles, int myPixelSize, String myTileMapFile) {
        this.myNumXTiles = myNumXTiles;
        this.myNumYTiles = myNumYTiles;
        this.myPixelSize = myPixelSize;
        this.myTileMapFile = myTileMapFile;
    }

    public int getMyNumXTiles() {
        return myNumXTiles;
    }

    public int getMyNumYTiles() {
        return myNumYTiles;
    }

    public int getMyPixelSize() {
        return myPixelSize;
    }

    public String getMyTileMapFile() {
        return myTileMapFile;
    }
}
