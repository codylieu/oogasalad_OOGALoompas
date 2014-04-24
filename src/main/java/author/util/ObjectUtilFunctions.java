package main.java.author.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.swing.table.TableModel;

import main.java.schema.tdobjects.TDObjectSchema;

/**
 * @author garysheng
 * 
 *         Some straightforward utility functions that help with IO in
 *         relation to tables and maps. All used in relation to ObjectEditor
 *         submodules
 */
public class ObjectUtilFunctions {
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

	public Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	public static boolean newObjectNameIsValid(String enemyName,
			HashMap<String, TDObjectSchema> objectMap) {

		if (!enemyName.equals("")) {
			if (!objectMap.containsKey(enemyName)) {
				if (enemyName.length() <= 20) {
					if (enemyName.length() >= 2) {
						for (String part : enemyName.split(" ")) {
							if (ObjectUtilFunctions.isAlphaNumeric(part)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
}
