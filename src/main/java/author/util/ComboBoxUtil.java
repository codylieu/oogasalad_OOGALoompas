package main.java.author.util;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class ComboBoxUtil {
	public static boolean containsValue(JComboBox comboBox, String value) {
		ComboBoxModel model = comboBox.getModel();
        int size = model.getSize();
        for(int i=0;i<size;i++) {
            Object element = model.getElementAt(i);
            if (element.equals(value)) {
            	return true;
            }
        }
        return false;
	}
}
