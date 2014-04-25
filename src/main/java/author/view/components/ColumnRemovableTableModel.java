package main.java.author.view.components;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * @author garysheng
 * A concerete subclass of the DefaultTableModel which allows someone to easily remove a column from a table
 */
public class ColumnRemovableTableModel extends DefaultTableModel {

	public ColumnRemovableTableModel(Object[][] data, String[] columnNames) {
		super(data, columnNames);
	}

	public Vector getColumnIdentifiers() {
		return columnIdentifiers;

	}

	/**
	 * removes a column and the corresponding data
	 * @param table
	 * @param vColIndex
	 */
	public void removeColumnAndData(JTable table, int vColIndex) {
		ColumnRemovableTableModel model = (ColumnRemovableTableModel) table
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
