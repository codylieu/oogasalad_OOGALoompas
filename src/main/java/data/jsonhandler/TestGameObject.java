package main.java.data.jsonhandler;

import java.util.HashMap;
import java.util.Map;

public class TestGameObject {
	
	private int myID;
	private int myHealth;
	
	public TestGameObject(int id, int health){
		myID = id;
		myHealth = health;
	}
	
	public Map<String, Object> getInfo()	{
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("myID", myID);
		output.put("myHealth", myHealth);
		return output;
	}
}
