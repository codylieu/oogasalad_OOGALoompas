package main.java.exceptions.data;

import java.io.IOException;

public class InvalidGameStateException extends IOException {
	public InvalidGameStateException(String s)	{
		super("Invalid or missing " + s + " for GameState");
	}
}
