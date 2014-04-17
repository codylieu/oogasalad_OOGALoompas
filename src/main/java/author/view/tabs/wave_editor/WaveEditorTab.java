package main.java.author.view.tabs.wave_editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.WaveController;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.WaveSpawnSchema;

public class WaveEditorTab extends EditorTab {

	private List<WaveSpawnSchema> myWaves;

	String[] columnNames = {"Waves", "Monster 1", "Monster 2", "Monster 3"};

	Object[][] data = {};

	JTable table;

	private static int NUMBER_OF_WAVES = 0;

	public WaveEditorTab(TabController tabController) {
		super(tabController);
		add(createWaveEditorContent(), BorderLayout.CENTER);
	}
	
	// Will make all of the values in the fields initially 0. Have to write a method since list of enemies is dynamic
	public void populateFields(){
		
	}

	public JComponent createWaveEditorContent(){

		JPanel content = new JPanel(new BorderLayout());

		content.add(createTable(), BorderLayout.NORTH);
		content.add(makeButtons(), BorderLayout.SOUTH);

		return content;
	}

	public JComponent createTable(){

		table = new JTable(new DefaultTableModel(data, columnNames));

		JScrollPane sp = new JScrollPane(table);

		return sp;
	}

	private JComponent makeButtons(){

		JPanel panel = new JPanel(new GridLayout(0, 1));

		panel.add(makeNewWaveButton(), BorderLayout.NORTH);
		panel.add(makeRemoveMostRecentWaveButton(), BorderLayout.CENTER);
		panel.add(makeRemoveWaveButton(), BorderLayout.SOUTH);

		return panel;
	}

	private JComponent makeNewWaveButton(){

		JButton addNewWaveButton = new JButton("Add New Wave");

		addNewWaveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				NUMBER_OF_WAVES++;
				model.addRow(new Object[]{"Wave " + NUMBER_OF_WAVES, new Integer(0), new Integer(0), new Integer(0)});

			}
		});

		return addNewWaveButton;
	}

	// Just here to test simpler case of removing rows
	private JComponent makeRemoveMostRecentWaveButton(){

		JButton removeMostRecentWaveButton = new JButton("Remove Last Wave");

		removeMostRecentWaveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if(NUMBER_OF_WAVES > 0){
					NUMBER_OF_WAVES--;
					model.removeRow(NUMBER_OF_WAVES);
				}
			}

		});

		return removeMostRecentWaveButton;
	}

	// Currently does nothing, will figure it out later
	private JComponent makeRemoveWaveButton(){

		JButton removeWaveButton = new JButton("Remove Wave");

		removeWaveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				//				model.removeRow();
			}

		});

		return removeWaveButton;
	}

	private void addWaveData() {

	}

	/**
	 * Gets called when the author clicks on the Wave tab. Updates a table based
	 * on the number of rows and columns specified. Enemy names that no longer
	 * exist get deleted, and rows get increased or decreased based on changes
	 * to that value;
	 */
	public void updateTable() {
		WaveController waveController = (WaveController) myController;
		//		int numLevels = waveController.getNumLevels();
		List<String> possibleEnemies = waveController.getEnemyList();
		//do stuff with table
	}

	@Override
	public void saveTabData() {
		// TODO Auto-generated method stub

	}

}
