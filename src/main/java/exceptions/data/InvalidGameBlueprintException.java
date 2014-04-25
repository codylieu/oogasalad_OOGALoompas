package main.java.exceptions.data;

import java.io.IOException;

public class InvalidGameBlueprintException extends IOException {
	public InvalidGameBlueprintException(String s)	{
		super("Invalid or missing " + s + " for GameBlueprint");
	}
	public InvalidGameBlueprintException() {
		super("File is not a blueprint");
	}
}
