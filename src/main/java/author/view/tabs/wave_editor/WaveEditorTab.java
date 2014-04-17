package main.java.author.view.tabs.wave_editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
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

	private String[] columnNames = {"Waves", "Monster 1", "Monster 2", "Monster 3"};
	private Object[][] data = {};

	private JTable table;

	private static int NUMBER_OF_WAVES = 0;

	private WaveTabContentCreator tabCreator = new WaveTabContentCreator();

	public WaveEditorTab(TabController tabController) {
		super(tabController);
		add(tabCreator.createWaveEditorContent(), BorderLayout.CENTER);
	}

	// Will make all of the values in the fields initially 0. Have to write a method since list of enemies is dynamic
	public void populateFields(){

	}

	private void addWaveData() {

	}

	public void updateEnemyList() {
		WaveController waveController = (WaveController) myController;
		List<String> possibleEnemies = waveController.getEnemyList();
		//do stuff with table
	}

	@Override
	public void saveTabData() {
		// TODO Auto-generated method stub

	}

	private class WaveTabContentCreator{

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
			panel.add(makeRemoveWaveButton(), BorderLayout.CENTER);
			panel.add(makeClearAllWavesButton(), BorderLayout.SOUTH);

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

		private JComponent makeClearAllWavesButton(){

			JButton clearAllWavesButton = new JButton("Clear All Waves");

			clearAllWavesButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					DefaultTableModel model = (DefaultTableModel) table.getModel();

					while(NUMBER_OF_WAVES > 0){
						NUMBER_OF_WAVES--;
						model.removeRow(NUMBER_OF_WAVES);
					}

				}
			});

			return clearAllWavesButton;
		}

	}

}
