package main.java.engine.map;

import jgame.impl.JGEngineInterface;

public class TDMap {
    private static final int xOffset = 0;
    private static final int yOffset = 0;
    private static final int tileXSize = 32;
    private static final int tileYSize = 32;
    private static final int skipX = 1;
    private static final int skipY = 1;

    private static final String tileImageName = "tile";
    private static final String tileImagePrefix = "t";
    private static final int TILE_CID = 0;
    private static final String IMG_OP = "-";

    private String tileMapName;
    private String tileMapFile;
    private int numXTiles;
    private int numYTiles;
    private String[][] tileMap;

    /**
     * Create a TDMap object which holds the information needed to initialize the tiles of the engine.
     *
     * @param tileMapName   Name of the tile map
     * @param tileMapFile   Name of the file that contains the tile map
     * @param numXTiles     Number of X tiles across the tile map
     * @param numYTiles     Number of y tiles down the tile map
     * @param tileMap       2D string array the size of the map with each element containing the id of the tile
     *                      that is mapped there
     */
    public TDMap(String tileMapName, String tileMapFile, int numXTiles, int numYTiles, String[][] tileMap) {
        this.tileMapName = tileMapName;
        this.tileMapFile = tileMapFile;
        this.numXTiles = numXTiles;
        this.numYTiles = numYTiles;
        this.tileMap = tileMap;
    }

    /**
     * Loads the map into the specified engine.
     *
     * @param engine The engine for the map to be loaded into
     */
    public void loadIntoGame(JGEngineInterface engine) {
        engine.defineImageMap(tileMapName, tileMapFile, xOffset, yOffset, tileXSize, tileYSize, skipX, skipY);

        for (int i = 0; i < numXTiles; i++) {
            for (int j = 0; j < numYTiles; j++) {
                int num = i * numXTiles + j;
                engine.defineImage(tileImageName + num, tileImagePrefix + num, TILE_CID, tileMapName, num, IMG_OP);
            }
        }

        for (int i = 0; i < engine.pfTilesX(); i++) {
            for (int j = 0; j < engine.pfTilesY(); j++) {
                engine.setTile(i, j, tileMap[i][j]);
            }
        }
    }
}
