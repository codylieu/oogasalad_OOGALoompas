package main.java.author.util;

import java.util.regex.Pattern;

import javax.swing.table.TableModel;

/**
 * @author garysheng
 * 
 *         has a few straightforward utility functions that help with IO in
 *         relation to tables and maps
 */
public class UtilFunctions {
	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	private static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	public static boolean isAlphabetical(String name) {
		char[] chars = name.toCharArray();

		for (char c : chars) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isAlphaNumeric(String name) {

		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		boolean hasSpecialChar = p.matcher(name).find();
		if (!hasSpecialChar && Character.isLetter(name.charAt(0)))
			return true;
		return false;
	}

	public static boolean isNotDuplicateKeyOfTable(String text,
			TableModel model, int column) {

		int rowcount = model.getRowCount();
		for (int row = 0; row < rowcount; row++) {

			if (model.getValueAt(row, column).toString().equals(text))
				return false;
		}
		return true;
	}
}
