package main.java.author.view.tabs.terrain;

import java.awt.Color;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum TerrainAttribute {

	Walkable (10)      { Color getColor() { return Color.GREEN; }},
	Flyable  (11)      { Color getColor() { return Color.BLUE; }},
	Untraversable (12) { Color getColor() { return Color.RED; }},
	Entry (13)         { Color getColor() { return Color.MAGENTA; }},
	Exit (14)          { Color getColor() { return Color.ORANGE; }};

	private final int index;
	TerrainAttribute(int index) { this.index = index; }
	
	@Override
	public String toString() {
		return this.name();
	}
	
	public static TerrainAttribute getAttribute(int i){ 
		return values()[i]; 
	} 
	
	public int getIndex () {
		return index;
	}
	
	abstract Color getColor();
	
}
