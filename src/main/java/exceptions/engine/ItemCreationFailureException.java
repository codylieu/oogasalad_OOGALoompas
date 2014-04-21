package main.java.exceptions.engine;

public class ItemCreationFailureException extends Exception {
	public ItemCreationFailureException(Exception e) {
		super(e);
	}
}
