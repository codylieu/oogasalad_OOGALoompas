package main.java.author.view.tabs.wave;

import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import main.java.schema.WaveSpawnSchema;

public class WaveTableModel extends DefaultTableModel{
	
	//K: Wave, V: WaveSpawnSchema
	protected Map<Integer, WaveSpawnSchema> modelMap;
	protected WaveSpawnSchema modelData;

	protected List<String> rowNames;
	protected List<String> columnNames;

	protected Boolean[] isColumnEditable;
	private boolean isModelEditable = true;

	public WaveTableModel() {
		super();
	}
	
	private String getName(int row) {
		return rowNames.get(row);
	}

	private Integer getSize(int row, int column) {
		return (Integer) getValueAt(row, column);
	}


	// LIST METHODS
	@Override
	public int getRowCount() {
		//return modelData.size();
		return 0;
	}
	@Override
	public int getColumnCount() {
		return columnNames.size();
	}


	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}
}
