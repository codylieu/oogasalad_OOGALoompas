package main.java.engine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jgame.JGObject;

import main.java.engine.objects.CollisionManager;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.Tower;

public class Model implements IModel{
	private List<TDObject> TDObjects;
	private int level;
	private IPlayer myPlayer;
	private CollisionManager myCollisionManager;
	
	public Model() {
		TDObjects = new ArrayList<TDObject>();
		level = 1;
		myPlayer = new Player();
		addPlayer(myPlayer);
//		addCollisionManager(new CollisionManager()); 
		
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
	
	public TDObject createTDObject(String objectName, Map<String, String> attributes) {
//		return new TDObject();
		return null;
	}

	@Override
	public void setCollisionManager(CollisionManager collisionManager) {
		this.myCollisionManager = collisionManager;
	}

	@Override
	public void addPlayer(IPlayer player) {
		this.myPlayer = player;
	}

	@Override
	public List<TDObject> getListOfObjects() {
		return TDObjects;
	}
}
