package main.java.author.view.tabs.wave;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractWaveTableModel<T> extends AbstractTableModel {
	protected List<T> modelData;
	protected List<String> columnNames;
	protected Class[] columnClasses;
	protected Boolean[] isColumnEditable;
	private Class columnClass = Object.class;
	private boolean isModelEditable = true;

	protected AbstractWaveTableModel(Class c) {
		setColumnClass(c);
	}

	protected AbstractWaveTableModel(List<String> columnNames) {
		this(new ArrayList<T>(), columnNames);
	}

	protected AbstractWaveTableModel(List<T> modelData, List<String> columnNames) {
		setDataAndColumnNames(modelData, columnNames);
	}


	protected AbstractWaveTableModel(List<T> modelData, List<String> columnNames, Class c) {
		setDataAndColumnNames(modelData, columnNames);
		setColumnClass(c);
	}

	protected void setDataAndColumnNames(List<T> modelData, List<String> columnNames) {
		this.modelData = modelData;
		this.columnNames = columnNames;
		columnClasses = new Class[getColumnCount()];
		isColumnEditable = new Boolean[getColumnCount()];
		fireTableStructureChanged();
	}

	protected void setColumnClass(Class c) {
		this.columnClass = c;
	}

	//
	//  TableModel interface methods
	//

	public Class getColumnClass(int column) {
		Class columnClass = null;

		//  Get the class, if set, for the specified column
		if (column < columnClasses.length)
			columnClass = columnClasses[column];

		//  Get the default class
		if (columnClass == null)
			columnClass = super.getColumnClass(column);

		return columnClass;
	}


	public int getColumnCount() {
		return columnNames.size();
	}

	public int getRowCount() {
		return modelData.size();
	}

	public String getColumnName(int column) {
		Object columnName = null;
		if (column < columnNames.size()) {
			columnName = columnNames.get( column );
		}
		return (columnName == null) ? super.getColumnName( column ) : columnName.toString();
	}

	//returns true
	public boolean isCellEditable(int row, int column) {
		Boolean isEditable = null;
		//  Check is column editability has been set
		if (column < isColumnEditable.length) {
			isEditable = isColumnEditable[column];
		}
		return (isEditable == null) ? isModelEditable : isEditable.booleanValue();
	}

	//
	//  Implement custom methods
	//

	public void addRow(T data) {
		insertRow(getRowCount(), data);
	}

	public void addColumn(T data) {
		insertColumn(getColumnCount(), data);
	}

	public T getColumn(int column) {
		return modelData.get(column);
	}

	@SuppressWarnings("unchecked")
	public T[] getColumnsAsArray(int... cols) {
		List<T> columnData = getColumnsAsList(cols);
		T[] array = (T[])Array.newInstance(columnClass, columnData.size());
		return (T[]) columnData.toArray( array );
	}

	public List<T> getColumnsAsList(int... cols) {
		ArrayList<T> columnData = new ArrayList<T>(cols.length);

		for (int i = 0; i < cols.length; i++) {
			columnData.add( getColumn(cols[i]) );
		}
		return columnData;
	}

	public void insertRow(int row, T data) {
		fireTableRowsInserted(row, row);
	}

	public void insertColumn(int column, T data) {
		modelData.add(column, data);
		fireTableStructureChanged();
	}

	public void setColumnClass(int column, Class columnClass) {
		columnClasses[column] = columnClass;
		fireTableRowsUpdated(0, getRowCount() - 1);
	}

	public void setColumnEditable(int column, boolean isEditable) {
		isColumnEditable[column] = isEditable ? Boolean.TRUE : Boolean.FALSE;
	}

	public void setModelEditable(boolean isModelEditable) {
		this.isModelEditable = isModelEditable;
	}

	public static String formatColumnName(String columnName) {
		if (columnName.length() < 3) return columnName;
		StringBuffer buffer = new StringBuffer( columnName );
		boolean isPreviousLowerCase = false;
		for (int i = 1; i < buffer.length(); i++) {
			boolean isCurrentUpperCase = Character.isUpperCase( buffer.charAt(i) );
			if (isCurrentUpperCase && isPreviousLowerCase) {
				buffer.insert(i, " ");
				i++;
			}
			isPreviousLowerCase = ! isCurrentUpperCase;
		}
		return buffer.toString().replaceAll("_", " ");
	}
}