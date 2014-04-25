package main.java.author.util;

import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 * @author garysheng Some useful ButtonGroup helper functions
 */
public class GroupButtonUtil {

	/**
	 * @param buttonGroup
	 * @return the text corresponding to the button selected
	 */
	public static String getSelectedButtonText(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons
				.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				return button.getText();
			}
		}

		return null;
	}
}