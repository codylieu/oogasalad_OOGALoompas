package main.java.engine.util.leapmotion.gamecontroller;

import java.util.ArrayList;
import java.util.List;
import main.java.engine.util.leapmotion.keybinderutils.TabManager;
import com.leapmotion.leap.Gesture;


/**
 * This tool is used to create a properties file of mappings
 * from Leap Motion gestures/actions to input events, i.e.
 * key presses and left and right mouse clicks. The file is saved by default to
 * the file path leapMotion.resources, which is defined as a constant in
 * LeapGameController.java.
 * 
 * There are two tabs, one for mapping of Leap Gestures and another for mapping
 * actions depending on the number of fingers detected by the device.
 * 
 * 
 * The GUI components are reused from keybinder util by Sam and Dennis.
 * 
 * @author Austin Lu
 * 
 */
public class LeapActionbindTool {

    public static void main (String[] args) {
        List<String> gestureNames = getEnumStringNames(Gesture.Type.values());
        List<String> fingerCountNames = getEnumStringNames(FingerCounts.values());
        new TabManager(gestureNames, fingerCountNames);
    }

    /**
     * Return the string names of all of the values in an Enum type.
     * 
     * @param enumValues
     * @return list of string names
     */
    private static List<String> getEnumStringNames (Object[] enumValues) {
        List<String> enumNames = new ArrayList<String>();
        for (Object obj : enumValues) {
            enumNames.add(obj.toString());
        }
        return enumNames;
    }
}
