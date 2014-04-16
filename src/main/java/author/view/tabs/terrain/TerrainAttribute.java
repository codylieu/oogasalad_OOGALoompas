package main.java.author.view.tabs.terrain;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum TerrainAttribute {

	WalkableTerrain, FlyableTerrain, UntraversableTerrain;
	
	private Map<String, Integer> terrainAttributes = new HashMap<String, Integer>();
	private int index;
	
	TerrainAttribute() {
		terrainAttributes.put(this.toString(), (Integer) index++);
	}
	
	@Override
	public String toString() {
		return this.name();
	}
	
	public int getIndex() {
		return terrainAttributes.get(this.toString());
	}
	
}
