package main.java.author.view.tabs.wave;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


abstract class WaveTableModel<T> extends AbstractTableModel {
	protected List<T> modelData;
	protected List<String> columnNames;
	protected Class[] columnClasses;
	protected Boolean[] isColumnEditable;
	private Class columnClass = Object.class;
	private boolean isModelEditable = true;

	
	protected WaveTableModel(Class columnClass) {
		setColumnClass(columnClass );
	}

	
	protected WaveTableModel(List<String> columnNames) {
		this(new ArrayList<T>(), columnNames);
	}

	
	protected WaveTableModel(List<T> modelData, List<String> columnNames) {
		setDataAndColumnNames(modelData, columnNames);
	}

	
	protected WaveTableModel(List<T> modelData, List<String> columnNames, Class rowClass) {
		setDataAndColumnNames(modelData, columnNames);
		setColumnClass( rowClass );
	}

	protected void setDataAndColumnNames(List<T> modelData, List<String> columnNames)
	{
		this.modelData = modelData;
		this.columnNames = columnNames;
		columnClasses = new Class[getColumnCount()];
		isColumnEditable = new Boolean[getColumnCount()];
		fireTableStructureChanged();
	}

	protected void setColumnClass(Class rowClass)
	{
		this.columnClass = rowClass;
	}
//
//  Implement the TableModel interface
//
	
	public Class getColumnClass(int column)
	{
		Class columnClass = null;

		//  Get the class, if set, for the specified column

		if (column < columnClasses.length)
			columnClass = columnClasses[column];

		//  Get the default class

		if (columnClass == null)
			columnClass = super.getColumnClass(column);

		return columnClass;
	}


	public int getColumnCount()
	{
		return columnNames.size();
	}


	public String getColumnName(int column)
	{
		Object columnName = null;

		if (column < columnNames.size())
		{
			columnName = columnNames.get( column );
		}

		return (columnName == null) ? super.getColumnName( column ) : columnName.toString();
	}

	public int getRowCount()
	{
		return modelData.size();
	}

	public boolean isCellEditable(int row, int column)
	{
		Boolean isEditable = null;

		//  Check is column editability has been set

		if (column < isColumnEditable.length)
			isEditable = isColumnEditable[column];

		return (isEditable == null) ? isModelEditable : isEditable.booleanValue();
	}
//
//  Implement custom methods
//

	public void addRow(T rowData) {
		insertRow(getRowCount(), rowData);
	}
	
	public void addColumn(T columnData) {
		insertColumn(getColumnCount(), columnData);
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

	public List<T> getColumnsAsList(int... cols)
	{
		ArrayList<T> columnData = new ArrayList<T>(cols.length);

		for (int i = 0; i < cols.length; i++)
		{
			columnData.add( getColumn(cols[i]) );
		}

		return columnData;
	}

	/**
	 *  Insert a row of data at the <code>row</code> location in the model.
	 *  Notification of the row being added will be generated.
	 *
	 *  @param   row	  row in the model where the data will be inserted
	 *  @param   rowData  data of the row being added
	 */
	public void insertRow(int row, T rowData)
	{
		modelData.add(row, rowData);
		fireTableRowsInserted(row, row);
	}
	
	public void insertColumn(int column, T columnData)
	{
		modelData.add(row, rowData);
		fireTableRowsInserted(row, row);
	}

	/**
	 *  Insert multiple rows of data at the <code>row</code> location in the model.
	 *  Notification of the row being added will be generated.
	 *
	 * @param   row	  row in the model where the data will be inserted
	 * @param   rowList  each item in the list is a separate row of data
	 */
	public void insertRows(int row, List<T> rowList)
	{
		modelData.addAll(row, rowList);
		fireTableRowsInserted(row, row + rowList.size() - 1);
	}

	/**
	 *  Insert multiple rows of data at the <code>row</code> location in the model.
	 *  Notification of the row being added will be generated.
	 *
	 * @param   row	  row in the model where the data will be inserted
	 * @param   rowArray  each item in the Array is a separate row of data
	 */
	public void insertRows(int row, T[] rowArray)
	{
		List<T> rowList = new ArrayList<T>(rowArray.length);

		for (int i = 0; i < rowArray.length; i++)
		{
			rowList.add( rowArray[i] );
		}

		insertRows(row, rowList);
	}

	/**
	 *  Remove the specified rows from the model. Rows between the starting
	 *  and ending indexes, inclusively, will be removed.
	 *  Notification of the rows being removed will be generated.
	 *
	 * @param   start		 starting row index
	 * @param   end		   ending row index
	 * @exception  ArrayIndexOutOfBoundsException
	 *								if any row index is invalid
	 */
	public void removeRowRange(int start, int end)
	{
		modelData.subList(start, end + 1).clear();
		fireTableRowsDeleted(start, end);
	}

	public void removeRows(int... rows) {
		for (int i = rows.length - 1; i >= 0; i--)
		{
			int row = rows[i];
			modelData.remove(row);
			fireTableRowsDeleted(row, row);
		}
	}

	public void replaceRow(int row, T rowData) {
		modelData.set(row, rowData);
		fireTableRowsUpdated(row, row);
	}


	public void setColumnClass(int column, Class columnClass) {
		columnClasses[column] = columnClass;
		fireTableRowsUpdated(0, getRowCount() - 1);
	}


	public void setColumnEditable(int column, boolean isEditable)
	{
		isColumnEditable[column] = isEditable ? Boolean.TRUE : Boolean.FALSE;
	}

	
	public void setModelEditable(boolean isModelEditable)
	{
		this.isModelEditable = isModelEditable;
	}


	public static String formatColumnName(String columnName)
	{
		if (columnName.length() < 3) return columnName;

		StringBuffer buffer = new StringBuffer( columnName );
		boolean isPreviousLowerCase = false;

		for (int i = 1; i < buffer.length(); i++)
		{
			boolean isCurrentUpperCase = Character.isUpperCase( buffer.charAt(i) );

			if (isCurrentUpperCase && isPreviousLowerCase)
			{
				buffer.insert(i, " ");
				i++;
			}

			isPreviousLowerCase = ! isCurrentUpperCase;
		}

		return buffer.toString().replaceAll("_", " ");
	}
}