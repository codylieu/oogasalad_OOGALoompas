package main.java.author.util;

import javax.swing.table.TableModel;

/**
 * @author garysheng Some useful JTable, tableModel helper functions
 */
public class JTableUtil {
	/**
	 * @param tableModel
	 * @return the list of column names of a table
	 */
	public static String[] getColumnNames(TableModel tableModel) {
		int size = tableModel.getColumnCount();
		String[] columnNames = new String[size];
		for (int i = 0; i < size; i++) {
			columnNames[i] = tableModel.getColumnName(i);
		}
		return columnNames;
	}

	/**
	 * @param tableModel
	 * @param columnToRemove
	 * @return the index of the column, where the column name is the string
	 */
	public static int getColumnIndexFromName(TableModel tableModel,
			String columnToRemove) {
		for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
			if (tableModel.getColumnName(columnIndex).equals(columnToRemove)) {
				return columnIndex;
			}

		}
		return -1;

	}
}
