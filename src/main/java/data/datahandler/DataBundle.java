package main.java.data.datahandler;

import java.io.Serializable;

import main.java.schema.GameBlueprint;
import main.java.schema.TowerSchema;


/**
 * DataBundles are designed to store lists of objects mapped to the types of 
 * objects contained in the list.
 * Therefore, it is important to store information within objects rather than
 * just adding random pieces of data (ints, doubles, Strings, etc.) to the 
 * DataBundle since there won't be any way to discern the purposes of primitive
 * data types when they are stored in the map.
 * @author In-Young Jo
 *
 */
public class DataBundle implements Serializable {
	
	private GameBlueprint myGameBlueprint;
	
	public DataBundle() {}
	
	public DataBundle(GameBlueprint gameBlueprintInit) {
		myGameBlueprint = gameBlueprintInit;
	}
	
	public GameBlueprint getBlueprint() {
		return myGameBlueprint;
	}
	
	public void setBlueprint(GameBlueprint gameBlueprint) {
		myGameBlueprint = gameBlueprint;
	}


}
