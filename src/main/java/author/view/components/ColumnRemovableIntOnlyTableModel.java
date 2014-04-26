package main.java.author.view.components;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * @author garysheng A concerete subclass of the DefaultTableModel which allows
 *         someone to easily remove a column from a table, and only accepts ints
 *         for the values that arent in column 0
 */
public class ColumnRemovableIntOnlyTableModel extends DefaultTableModel {

	public ColumnRemovableIntOnlyTableModel(Object[][] data,
			String[] columnNames) {
		super(data, columnNames);
	}

	public Vector getColumnIdentifiers() {
		return columnIdentifiers;

	}

	public boolean isCellEditable(int row, int col) {

		if (col == 0) {
			return false;
		}
		return true;

	}
	@Override
	public java.lang.Class<?> getColumnClass(int columnIndex) {
		if (columnIndex != 0) // second column accepts only Integer values
			return Integer.class;
		else
			return String.class; // other columns accept String values
	};

	/**
	 * removes a column and the corresponding data
	 * 
	 * @param table
	 * @param vColIndex
	 */
	public void removeColumnAndData(JTable table, int vColIndex) {
		ColumnRemovableIntOnlyTableModel model = (ColumnRemovableIntOnlyTableModel) table
				.getModel();
		TableColumn col = table.getColumnModel().getColumn(vColIndex);
		int columnModelIndex = col.getModelIndex();
		Vector data = model.getDataVector();
		Vector colIds = model.getColumnIdentifiers();

		// Remove the column from the table
		table.removeColumn(col);

		// Remove the column header from the table model
		colIds.removeElementAt(columnModelIndex);

		// Remove the column data
		for (int r = 0; r < data.size(); r++) {
			Vector row = (Vector) data.get(r);
			row.removeElementAt(columnModelIndex);
		}
		model.setDataVector(data, colIds);

		// Correct the model indices in the TableColumn objects
		// by decrementing those indices that follow the deleted column
		Enumeration enumm = table.getColumnModel().getColumns();
		for (; enumm.hasMoreElements();) {
			TableColumn c = (TableColumn) enumm.nextElement();
			if (c.getModelIndex() >= columnModelIndex) {
				c.setModelIndex(c.getModelIndex() - 1);
			}
		}
		model.fireTableStructureChanged();
	}
	
	
}
