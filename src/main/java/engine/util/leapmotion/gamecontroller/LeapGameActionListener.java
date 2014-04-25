package main.java.engine.util.leapmotion.gamecontroller;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Map;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Screen;
import com.leapmotion.leap.Vector;


/**
 * ActionListener acts as a mouse depending on the front-most finger's location,
 * using a MovingAverageFilter to smooth the mouse movements.
 * It also checks for and executes any mapped gestures/actions that are detected.
 * 
 * For users with multiple screens, this listener will use the first located screen.
 * 
 * @author Austin Lu
 * 
 */
public class LeapGameActionListener extends Listener {

    /**
     * Width and height value of the first located screen.
     */
    private int myScreenWidth, myScreenHeight;

    /**
     * Average X and Y coordinates of the mouse.
     */
    private int myAverageX, myAverageY;

    /**
     * Keep track of state for number of fingers and hands.
     */
    private int myNumberOfFingers, myNumberOfHands;

    /**
     * A robot to simulate key and mouse presses.
     */
    private Robot myRobot;

    /**
     * Stores a map of leap events to action names.
     */
    private Map<String, String> myActionMap;

    /**
     * Keep track of moving average for smooth mouse movement.
     */
    private MovingAverageFilter myMovingAverageFilter;

    /**
     * Create a new ActionListener with a map of Leap gestures/actions to input events.
     * 
     * @param actionMap map of gesture/action names to input event names
     *        (key codes or mouse click constants)
     */
    public LeapGameActionListener (Map<String, String> actionMap) {
        try {
            myRobot = new Robot();
            myActionMap = actionMap;
            myMovingAverageFilter = new MovingAverageFilter(10);
        }
        catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * Do frame actions. If Leap controller is not connected, it will do nothing.
     * 
     * @see com.leapmotion.leap.Listener#onFrame(com.leapmotion.leap.Controller)
     */
    public void onFrame (Controller controller) {

        if (!controller.isConnected()) { return; }

        Frame frame = controller.frame();
        myNumberOfFingers = frame.fingers().count();
        myNumberOfHands = frame.hands().count();
        Finger frontmostFinger = frame.fingers().frontmost();

        // use first located screen.
        Screen screen = controller.locatedScreens().get(0);
        myScreenWidth = screen.widthPixels();
        myScreenHeight = screen.heightPixels();

        Vector fingerScreenIntersect = screen.intersect(frontmostFinger, true);
        Vector fingertipVelocity = frontmostFinger.tipVelocity();

        // https://developer.leapmotion.com/forums/forums/support/topics/how-to-get-actual-screen-coordinates-x-y-from-fingers-object
        Vector fingerLocation = new Vector(fingertipVelocity);
        fingerLocation.setX(fingerScreenIntersect.getX() * myScreenWidth);
        fingerLocation.setY(screen.heightPixels() - fingerScreenIntersect.getY() * myScreenHeight);

        myMovingAverageFilter.updateFilter(fingerLocation);
        Vector averages = myMovingAverageFilter.getAverages();
        myAverageX = (int) averages.getX();
        myAverageY = (int) averages.getY();
        // System.out.println(averages);
        myRobot.mouseMove(myAverageX, myAverageY);

        GestureList gestures = frame.gestures();
        for (Gesture gesture : gestures) {
            for (String gestureName : myActionMap.keySet()) {
                if (LeapGameController.isGesture(gestureName) &&
                    gesture.type() == Enum.valueOf(Gesture.Type.class, gestureName)) {
                    doEventAction(gestureName);
                }
            }
        }

        String fingerCountActionName = FingerCounts.NAME_PREFIX + frame.fingers().count();

        if (FingerCounts.contains(fingerCountActionName) &&
            myActionMap.containsKey(fingerCountActionName)) {

            doEventAction(fingerCountActionName);
        }

    }

    /**
     * Do the mapped input event corresponding to the named action.
     * 
     * @param actionName name of the action, either gesture or fingercount
     */
    private void doEventAction (String actionName) {
        int keyToPress = Integer.parseInt(myActionMap.get(actionName));
        try {
            myRobot.keyPress(keyToPress);
            myRobot.keyRelease(keyToPress);
        }
        catch (IllegalArgumentException iae) {
            // is not an integer keycode
            myRobot.mousePress(keyToPress);
            myRobot.mousePress(keyToPress);
        }
    }

    /**
     * Get current number of fingers detected.
     * @return number of fingers.
     */
    public int getNumberOfFingers () {
        return myNumberOfFingers;
    }

    /**
     * Get current number of hands detected.
     * @return number of hands.
     */
    public int getNumberOfHands () {
        return myNumberOfHands;
    }

}
