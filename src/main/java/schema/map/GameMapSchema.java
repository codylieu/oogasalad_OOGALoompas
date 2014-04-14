package main.java.schema.map;

import main.java.schema.AbstractSchema;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper for the tiles corresponding to a terrain map. MY_TILES takes a list
 * of TileSchemas and MY_TILEMAPS takesa list of TileMapSchemas
 */
public class GameMapSchema extends AbstractSchema implements Serializable {
    public static final String MY_TILES = "myTiles";
    public static final String MY_TILEMAPS = "myTilemaps";

    @Override
    protected Set<String> populateAdditionalAttributes() {
        Set<String> attributes = new HashSet<>();
        attributes.add(MY_TILES);
        attributes.add(MY_TILEMAPS);

        return attributes;
    }
}
