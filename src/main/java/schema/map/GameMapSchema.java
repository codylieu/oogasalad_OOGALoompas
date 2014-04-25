package main.java.schema.map;

import main.java.schema.AbstractSchema;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper for the tiles corresponding to a terrain map. MY_TILES takes a list
 * of TileSchemas and MY_TILEMAPS takes a list of TileMapSchemas
 */
public class GameMapSchema extends AbstractSchema implements Serializable {
    public static final String MY_TILES = "myTiles";
    public static final String MY_TILEMAPS = "myTilemaps";
    public static final String MY_CANVAS_ATTRIBUTES = "myCanvasAttributes";

    @Override
    protected Set<String> populateAdditionalAttributes() {
        Set<String> attributes = new HashSet<>();
        attributes.add(MY_TILES);
        attributes.add(MY_TILEMAPS);
        attributes.add(MY_CANVAS_ATTRIBUTES);
        
        return attributes;
    }
}
