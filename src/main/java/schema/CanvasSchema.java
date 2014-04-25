package main.java.schema;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * the higher level settings defining the canvas size
 * 
 */
public class CanvasSchema extends AbstractSchema {
	public static final String X_TILES = "xtiles";
	public static final String Y_TILES = "ytiles";
	public static final String ENTRY_ROW = "Entry Row";
	public static final String ENTRY_COL = "Entry Column";
	public static final String EXIT_ROW = "Exit Row";
	public static final String EXIT_COL = "Exit Column";

	public CanvasSchema()	{
		super();
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		Set<String> attributes = new HashSet<String>();
		attributes.add(X_TILES);
		attributes.add(Y_TILES);
		attributes.add(ENTRY_ROW);
		attributes.add(ENTRY_COL);
		attributes.add(EXIT_ROW);
		attributes.add(EXIT_COL);
		return attributes;
	}
}
