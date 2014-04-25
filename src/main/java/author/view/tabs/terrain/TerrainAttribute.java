package main.java.author.view.tabs.terrain;

import java.awt.Color;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum TerrainAttribute {

	Walkable      { Color getColor() { return Color.GREEN;}
	                     int getIndex()   { return 10; }},
	                     
	Flyable       { Color getColor() { return Color.BLUE; }
	                     int getIndex()   { return 11; }},
	 
	Untraversable { Color getColor() { return Color.RED; }
	                int getIndex() { return 12; }},

	Entry         { Color getColor() { return Color.MAGENTA; }
	                int getIndex() {  return 13; }},
	                
	Exit          { Color getColor() { return Color.ORANGE; }
	                int getIndex() { return 14;}};

	
	private static Map<Integer, TerrainAttribute> enumMap = 
				new HashMap<Integer, TerrainAttribute>();
	
	static {
		for (TerrainAttribute attr : values()) {
			enumMap.put(attr.getIndex(), attr);
		}
	}
			
	
	@Override
	public String toString() {
		return this.name();
	}
	
	public static TerrainAttribute getAttribute(int i){ 
		return enumMap.get(i);
	} 
	
	abstract Color getColor();
	
	abstract int getIndex();
	
}
