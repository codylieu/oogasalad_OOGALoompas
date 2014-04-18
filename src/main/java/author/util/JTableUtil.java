package main.java.author.util;

import javax.swing.table.TableModel;

public class JTableUtil {
	public static String[] getColumnNames(TableModel tableModel) {
		int size = tableModel.getColumnCount();
		String[] columnNames = new String[size];
		for (int i = 0; i < size; i++) {
			columnNames[i] = tableModel.getColumnName(i);
		}
		return columnNames;
	}

	public static int getColumnIndexFromName(TableModel tableModel, String columnToRemove) {
		for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
			if (tableModel.getColumnName(columnIndex).equals(columnToRemove)) {
				return columnIndex;
			};
		}
		return -1;
		
		
	}
}
