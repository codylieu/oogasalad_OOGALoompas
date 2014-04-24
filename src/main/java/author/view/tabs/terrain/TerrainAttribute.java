package main.java.author.view.tabs.terrain;

import java.awt.Color;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum TerrainAttribute {

	WalkableTerrain      { Color getColor() { return Color.GREEN; }},
	FlyableTerrain       { Color getColor() { return Color.BLUE; }},
	UntraversableTerrain { Color getColor() { return Color.RED; }};

	@Override
	public String toString() {
		return this.name();
	}
	
	protected static TerrainAttribute getAttribute(int i){ 
		return values()[i]; 
	} 
	
	abstract Color getColor();
	
}
