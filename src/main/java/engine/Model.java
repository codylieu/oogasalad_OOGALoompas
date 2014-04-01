package main.java.engine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jgame.JGObject;

import main.java.engine.objects.TDObject;
import main.java.engine.objects.Tower;

public class Model {
	private List<TDObject> TDObjects;
	private int level;
	private Player myPlayer;
	
	public Model() {
		TDObjects = new ArrayList<TDObject>();
		level = 1;
		myPlayer = new Player();
	}
	
	public JGObject createTower(String type, double x, double y, String gfxname) {
		return new JGObject(type, true, x, y, 1, gfxname);
	}
	
	public void loadState() {
		// load in the JSON file created by authoring environment
	}
	
	public void upgradeTowers(Tower id) {
		id.level++;
	}
	
	public TDObject createTowerDefenseObject(String objectName, Map<String, String> attributes) {
		return new TDObjecT();
	}
}
