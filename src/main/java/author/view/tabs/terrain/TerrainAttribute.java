package main.java.author.view.tabs.terrain;

import java.awt.Color;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum TerrainAttribute {

	Walkable      { Color getColor() { return Color.GREEN; }},
	Flyable       { Color getColor() { return Color.BLUE; }},
	Untraversable { Color getColor() { return Color.RED; }},
	Entry         { Color getColor() { return Color.MAGENTA; }},
	Exit          { Color getColor() { return Color.ORANGE; }};

	@Override
	public String toString() {
		return this.name();
	}
	
	protected static TerrainAttribute getAttribute(int i){ 
		return values()[i]; 
	} 
	
	abstract Color getColor();
	
}
