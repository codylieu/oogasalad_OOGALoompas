package main.java.data.datahandler;

import java.io.Serializable;

import main.java.engine.GameState;
import main.java.schema.GameBlueprint;
import main.java.schema.SimpleTowerSchema;


/**
 * DataBundles store two objects. One object is a GameBlueprint,
 * which contains data relevant to the authoring environment.
 * The other object is a GameState, which contains data
 * relevant to the engine. There are public getter and setter methods
 * for both objects so that data can be properly saved and retrieved
 * to/from the data bundle.
 * @author In-Young Jo
 *
 */
public class DataBundle implements Serializable {
	
	private GameBlueprint myGameBlueprint;
	private GameState myGameState;
	
	public DataBundle() {}
	
	public DataBundle(GameBlueprint gameBlueprintInit, GameState gameStateInit) {
		myGameBlueprint = gameBlueprintInit;
		myGameState = gameStateInit;
	}
	
	public DataBundle(GameBlueprint gameBlueprintInit) {
		myGameBlueprint = gameBlueprintInit;
	}
	
	public GameBlueprint getBlueprint() {
		return myGameBlueprint;
	}
	
	public GameState getGameState() {
		return myGameState;
	}
	
	public void setBlueprint(GameBlueprint gameBlueprint) {
		myGameBlueprint = gameBlueprint;
	}
	
	public void setGameState(GameState gameState) {
		myGameState = gameState;
	}
	
}
