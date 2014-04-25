package main.java.exceptions.engine;

public class TowerCreationFailureException extends Exception {
	public TowerCreationFailureException(Exception e) {
		super(e);
	}
}
