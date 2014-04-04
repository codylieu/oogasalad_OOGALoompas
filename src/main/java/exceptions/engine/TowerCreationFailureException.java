package main.java.exceptions.engine;

public class TowerCreationFailureException extends Exception {
	//TODO: Make exception more specific
	public TowerCreationFailureException() {
		super("Tower could not be created!");
	}
}
