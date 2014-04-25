package main.java.engine.util.leapmotion.gamecontroller;

/**
 * An interface specifying common methods that may be helpful for
 * specifying behavior programmatically in a game engine, i.e. beyond
 * input event mappings.
 * 
 * @author Austin Lu
 *
 */
public interface ILeapController {

    /**
     * Get current number of fingers detected.
     * @return number of fingers.
     */
    public int getNumberOfFingers ();

    /**
     * Get current number of hands detected.
     * @return number of hands.
     */
    public int getNumberOfHands ();

}
