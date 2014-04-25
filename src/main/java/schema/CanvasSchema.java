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

	public CanvasSchema()	{
		super();
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		Set<String> attributes = new HashSet<String>();
		attributes.add(X_TILES);
		attributes.add(Y_TILES);
		return attributes;
	}
}
