package main.java.engine.util.leapmotion.gamecontroller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Gesture.Type;


/**
 * A game controller object that performs mouse and keyboard events from Leap Motion device actions.
 * Used for game engines such as JGame that have natural game loops.
 * 
 * Steps:
 * 1) Save a leap action->input event mapping with the LeapActionbindTool.java
 * 2) Create a LeapGameController in your game engine, and call its doFrame() at every frame in your
 * game loop.
 * (your saved action map properties file is loaded automatically)
 * 
 * @author Austin Lu
 */
public class LeapGameController implements ILeapController {

    public static final String DEFAULT_ACTIONMAP_PACKAGE = "leapMotion.resources.";
    public static final String DEFAULT_ACTIONMAP_FILENAME = "LeapActionMap";
    public static final String DEFAULT_ACTIONMAP_FILETYPE = ".properties";
    public static final String DEFAULT_ACTIONMAP_FILEPATH = "src/leapMotion/resources/";

    /**
     * Text for gestures tab.
     */
    public static final String GESTURES_TITLE = "Leap Gestures";

    /**
     * Text for finger count actions tab.
     */
    public static final String FINGERCOUNT_TITLE = "Finger count actions";

    /**
     * Text for left click action.
     */
    public static final String LEFT_CLICK = "LeftClick";

    /**
     * Text for right click action.
     */
    public static final String RIGHT_CLICK = "RightClick";

    /**
     * Leap API base controller.
     */
    private Controller myController;

    /**
     * Listens for Leap device's actions, and executes mapped mouse and key events
     */
    private LeapGameActionListener myListener;

    /**
     * Mapping of Leap actions to input events.
     */
    private Map<String, String> myActionMap;

    /**
     * Create a new game controller, and automatically load in any saved action map properties file.
     */
    public LeapGameController () {
        myController = new Controller();

        myActionMap = new HashMap<String, String>();

        loadActionMap();

        myListener = new LeapGameActionListener(myActionMap);

        enableMappedGestures();
    }

    /**
     * Call this method every frame in your game's main loop to check for Leap motion actions and do
     * the mapped input events.
     * 
     * In JGame, this would be called in JGEngine's doFrame()
     */
    public void doFrame () {
        myListener.onFrame(myController);
    }

    /**
     * Checks whether or not a String is a valid name of an Gesture.Type enum value
     * 
     * @param eventName name to check
     * @return if eventName is valid enum value of Gesture.Type
     */
    public static boolean isGesture (String eventName) {
        for (Type gestureType : Gesture.Type.values()) {
            if (gestureType.name().equals(eventName)) { return true; }
        }
        return false;
    }

    /**
     * Loads map of Leap motion events to actions, using a resource bundle and the default actionmap
     * properties file name and path.
     */
    private void loadActionMap () {
        ResourceBundle actionMapProperties =
                ResourceBundle.getBundle(DEFAULT_ACTIONMAP_PACKAGE + DEFAULT_ACTIONMAP_FILENAME);
        Enumeration<String> keys = actionMapProperties.getKeys();

        while (keys.hasMoreElements()) {
            String actionName = keys.nextElement();
            String actionEventName = actionMapProperties.getString(actionName);

            myActionMap.put(actionName, actionEventName);
        }
    }

    /**
     * Enable any Leap gestures that were mapped in the LeapActionMap.properties file.
     */
    private void enableMappedGestures () {
        for (String eventName : myActionMap.keySet()) {
            if (isGesture(eventName)) {
                Gesture.Type mappedGesture = Enum.valueOf(Gesture.Type.class, eventName);
                myController.enableGesture(mappedGesture);
            }
        }
    }

    @Override
    public int getNumberOfFingers () {
        return myListener.getNumberOfFingers();
    }

    @Override
    public int getNumberOfHands () {
        return myListener.getNumberOfHands();
    }

}
