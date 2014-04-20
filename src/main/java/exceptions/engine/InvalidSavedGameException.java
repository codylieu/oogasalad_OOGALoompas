package main.java.exceptions.engine;

/**
 * Should be thrown when game state cannot be properly saved or loaded from file.
 * Player could use this to pause and notify the player, then continue the present game.
 * @author Austin
 *
 */
public class InvalidSavedGameException extends Exception {
    public InvalidSavedGameException (Exception e) {
        super(e);
    }
}
