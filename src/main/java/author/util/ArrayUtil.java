package main.java.author.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayUtil {

	public static boolean arraysHaveSameElements(String[] arr1, String[] arr2) {
		List<String> l1 = new ArrayList<String>(Arrays.asList(arr1));
		List<String> l2 = new ArrayList<String>(Arrays.asList(arr1));

		Collections.sort(l1);
		Collections.sort(l2);

		return l1.equals(l2);
	}

	public static List<String> getElementsToAdd(String[] enemyNames,
			String[] currentTableColumns, String ignoreString) {
		List<String> currentTableColumnsList = new ArrayList<String>(
				Arrays.asList(currentTableColumns));
		List<String> namesToAdd = new ArrayList<String>();
		for (String enemyName : enemyNames) {
			if (!currentTableColumnsList.contains(enemyName)) {
				namesToAdd.add(enemyName);
			}
		}
		return namesToAdd;
	}
	public static List<String> getElementsToRemove(String[] enemyNames,
			String[] currentTableColumns, String ignoreString) {
		List<String> enemyNamesList = new ArrayList<String>(
				Arrays.asList(enemyNames));
		List<String> namesToRemove = new ArrayList<String>();
		for (String enemyInTable : currentTableColumns) {
			if (!enemyNamesList.contains(enemyInTable) && !enemyInTable.equals(ignoreString)) {
				namesToRemove.add(enemyInTable);
			}
		}
		return namesToRemove;
	}

}
