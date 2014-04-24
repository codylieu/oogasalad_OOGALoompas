package main.java.engine.map;

import jgame.impl.JGEngineInterface;
import main.java.engine.Model;
import main.java.schema.map.GameMapSchema;
import main.java.schema.map.TileMapSchema;
import main.java.schema.map.TileSchema;

import java.io.Serializable;
import java.util.*;

public class TDMap {
    private static final int X_OFFSET = 0;
    private static final int Y_OFFSET = 0;
    private static final int X_SKIP = 0;
    private static final int Y_SKIP = 0;
    private static final String IMG_OP = "-";

    private List<TileSchema> tileSchemas;
    private List<TileMapSchema> tileMapSchemas;
    private Map<String, TileMap> tileMaps;
    private Set<String> definedTiles;

    public TDMap(JGEngineInterface engine, GameMapSchema gameMapSchema) {
        Map<String, Object> gameMapAttributes = gameMapSchema.getAttributesMap();
        tileSchemas = (List<TileSchema>) gameMapAttributes.get(GameMapSchema.MY_TILES);
        tileMapSchemas = (List<TileMapSchema>) gameMapAttributes.get(GameMapSchema.MY_TILEMAPS);
        tileMaps = new HashMap<>();
        definedTiles = new HashSet<>();

        // Load tilemaps used for this map
        for (TileMapSchema tms : tileMapSchemas) {
            TileMap tileMap = new TileMap(tms);
            tileMaps.put(tileMap.name, tileMap);

            engine.defineImageMap(tileMap.name, Model.RESOURCE_PATH + tileMap.name,
					X_OFFSET, Y_OFFSET, tileMap.pixelSize, tileMap.pixelSize,
					X_SKIP, Y_SKIP);
        }

        // Load in tiles to engine
        for (TileSchema ts : tileSchemas) {
            Map<String, Object> tsAttributeMap = ts.getAttributesMap();
            String tileMapFileName = (String) tsAttributeMap.get(TileSchema.TILEMAP_FILE_NAME);

            if (tileMaps.get(tileMapFileName) == null) {
                continue; // TODO: fix
            }

            int tileRow = (Integer) tsAttributeMap.get(TileSchema.CANVAS_ROW);
            int tileCol = (Integer) tsAttributeMap.get(TileSchema.CANVAS_COL);
            int tileMapRow = (Integer) tsAttributeMap.get(TileSchema.TILEMAP_ROW);
            int tileMapCol = (Integer) tsAttributeMap.get(TileSchema.TILEMAP_COL);
            int tileCID = (Integer) tsAttributeMap.get(TileSchema.TILE_CID);
            int tileIndex = tileMapRow * tileMaps.get(tileMapFileName).numCols + tileMapCol;

            String tileName = tileMapFileName + tileMapCol + tileMapRow;

            if (!definedTiles.contains(tileName)) {
                engine.defineImage(tileName, tileMapRow + tileMapCol + "", tileCID,
						tileMapFileName, tileIndex, IMG_OP);
                definedTiles.add(tileName);
            }

            engine.setTile(tileCol, tileRow, tileMapRow + tileMapCol + "");
        }
    }

    private class TileMap {
        private String name;
        private int pixelSize;
        private int numRows;
        private int numCols;

        public TileMap(TileMapSchema tileMapSchema) {
            Map<String, Object> tmsAttributesMap = tileMapSchema.getAttributesMap();
            name = (String) tmsAttributesMap.get(TileMapSchema.TILEMAP_FILE_NAME);
            pixelSize = (Integer) tmsAttributesMap.get(TileMapSchema.PIXEL_SIZE);
            numRows = (Integer) tmsAttributesMap.get(TileMapSchema.NUM_ROWS);
            numCols = (Integer) tmsAttributesMap.get(TileMapSchema.NUM_COLS);
        }
    }
}
