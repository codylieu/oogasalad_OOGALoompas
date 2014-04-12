package main.java.schema;

import main.java.exceptions.engine.InvalidParameterForConcreteTypeException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TileMapSchema extends AbstractSchema {
    public static final String NUM_ROWS = "numRows";
    public static final String NUM_COLS = "numCols";
    public static final String PIXEL_SIZE = "pixelSize";
    public static final String TILEMAP_FILE_NAME = "tilemapFileName";

    @Override
    protected Set<String> populateAdditionalAttributes() {
        Set<String> attributes = new HashSet<>();
        attributes.add(NUM_ROWS);
        attributes.add(NUM_COLS);
        attributes.add(PIXEL_SIZE);
        attributes.add(TILEMAP_FILE_NAME);

        return attributes;
    }

    @Override
    public void addAttribute(String attributeName, Serializable attributeValue)
            throws InvalidParameterForConcreteTypeException {
    }
}
