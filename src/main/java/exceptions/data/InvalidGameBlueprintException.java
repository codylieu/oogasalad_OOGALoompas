package main.java.exceptions.data;

public class InvalidGameBlueprintException extends Exception {
	public InvalidGameBlueprintException(String s)	{
		super("Invalid or missing " + s + " for GameBlueprint");
	}
}
