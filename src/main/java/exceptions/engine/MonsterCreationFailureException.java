package main.java.exceptions.engine;

public class MonsterCreationFailureException extends Exception {
	//TODO: Make exception more specific
	public MonsterCreationFailureException() {
		super("Monster could not be created!");
	}
}
