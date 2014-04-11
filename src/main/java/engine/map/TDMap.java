package main.java.engine.map;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;
import main.java.author.view.tabs.terrain.Tile;
import main.java.author.view.tabs.terrain.TileMap;
import main.java.schema.GameMap;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class TDMap {
    private static final int xOffset = 0;
    private static final int yOffset = 0;
    private static final int tileXSize = 32;
    private static final int tileYSize = 32;
    private static final int skipX = 0;
    private static final int skipY = 0;

    private static final String tileImageName = "tile";
    private static final String tileImagePrefix = "t";
    private static final int TILE_CID = 0;
    private static final String IMG_OP = "-";

    private String tileMapName;
    private String tileMapFile;
    private int numXTiles;
    private int numYTiles;
    private String[][] tileMap;

    public TDMap() {
        //TODO: create
    }

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

    public void loadMapIntoGame(JGEngineInterface engine, String fileName) {
        GameMap mapToLoad = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            mapToLoad = (GameMap) is.readObject();
            is.close();

            Tile[][] tileMap = mapToLoad.getMyTiles();
            List<TileMap> tileMapList = mapToLoad.getMyTileMaps();

            // define image maps used in case multiple maps used
            for (TileMap tm : tileMapList) {
                String tileMapNameNew = tm.getMyTileMapFile().replace("\\", "//"); //need to do this for jgengine
                engine.defineImageMap(tm.getMyTileMapFile(), tileMapNameNew,
                        0, 0, 32, 32, 0, 0); // TODO: refactor to constants
            }

            // now set the tiles
            for (int i = 0; i < tileMap.length; i++) {
                for (int j = 0; j < tileMap[0].length; j++) {
                    Tile tile = tileMap[i][j];

                    int tileMapWidth = getTileMapWidth(tileMapList, tile.getMyTileMapFileName());
                    int index = tile.getMyMapXIndex() * tileMapWidth + tile.getMyMapYIndex();

                    //TODO: why does jgame only take tilestr of size 4..., need to think of a better implementation
                    engine.defineImage(tile.getMyTileMapFileName() + tile.getMyMapXIndex() + tile.getMyMapYIndex(),
                            tile.getMyMapXIndex() + "" + tile.getMyMapYIndex(),
                            tile.getPassIndex(), tile.getMyTileMapFileName(), index, "-");
                    engine.setTile(new JGPoint(tile.getCol(), tile.getRow()),
                            tile.getMyMapXIndex() + "" + tile.getMyMapYIndex());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: probably a better way to do this, refactor
    private int getTileMapWidth(List<TileMap> tileMaps, String tileMapName) throws Exception {
        for (TileMap tm : tileMaps) {
            if (tm.getMyTileMapFile().equals(tileMapName)) {
                return tm.getMyNumXTiles();
            }
        }

        throw new Exception("Tilemap " + tileMapName + " not found"); // TODO: throw new exception
    }

    private int getTileMapHeight(List<TileMap> tileMaps, String tileMapName) throws Exception {
        for (TileMap tm : tileMaps) {
            if (tm.getMyTileMapFile().equals(tileMapName)) {
                return tm.getMyNumYTiles();
            }
        }

        throw new Exception("Tilemap " + tileMapName + " not found"); // TODO: throw new exception
    }
}
