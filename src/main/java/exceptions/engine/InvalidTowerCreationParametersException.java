package main.java.exceptions.engine;

public class InvalidTowerCreationParametersException extends Exception {
	public InvalidTowerCreationParametersException() {
		super("Invalid number of tower parameters!");
	}
}
