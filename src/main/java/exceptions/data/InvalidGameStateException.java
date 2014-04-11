package main.java.exceptions.data;

public class InvalidGameStateException extends Exception {
	public InvalidGameStateException(String s)	{
		super("Invalid or missing " + s + " for GameState");
	}
}
