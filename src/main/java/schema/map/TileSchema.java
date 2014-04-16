package main.java.schema.map;

import main.java.exceptions.engine.InvalidParameterForConcreteTypeException;
import main.java.schema.AbstractSchema;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TileSchema extends AbstractSchema {
    public static final String CANVAS_ROW = "canvasRowIndex";
    public static final String CANVAS_COL = "canvasColIndex";
    public static final String TILEMAP_ROW = "tilemapRowIndex";
    public static final String TILEMAP_COL = "tilemapColIndex";
    public static final String TILEMAP_FILE_NAME = "tilemapName";
    public static final String TILE_CID = "tileCID";

    @Override
    protected Set<String> populateAdditionalAttributes() {
        Set<String> attributes = new HashSet<>();
        attributes.add(CANVAS_ROW);
        attributes.add(CANVAS_COL);
        attributes.add(TILEMAP_ROW);
        attributes.add(TILEMAP_COL);
        attributes.add(TILEMAP_FILE_NAME);
        attributes.add(TILE_CID);

        return attributes;
    }
}
