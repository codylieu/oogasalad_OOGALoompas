package main.java.engine.util.leapmotion.gamecontroller;

/**
 * Enum of possible finger counts, from one to ten.
 *
 * @author Austin Lu
 *
 */
public enum FingerCounts {
    FINGER_COUNT1,
    FINGER_COUNT2,
    FINGER_COUNT3,
    FINGER_COUNT4,
    FINGER_COUNT5,
    FINGER_COUNT6,
    FINGER_COUNT7,
    FINGER_COUNT8,
    FINGER_COUNT9,
    FINGER_COUNT10;

    /**
     * Adding the NAME_PREFIX before an integer returns a String possibly representing a valid FingerCount value.
     */
    public static final String NAME_PREFIX = "FINGER_COUNT";

    /**
     * Checks whether or not a string is a valid FingerCount value name.
     * 
     * @param enumName, should be a NAME_PREFIX concatenated before some integer.
     * @return
     */
    public static boolean contains (String enumName) {
        for (FingerCounts f : FingerCounts.values()) {
            if (f.toString().equals(enumName)) { return true; }
        }
        return false;
    }
}
