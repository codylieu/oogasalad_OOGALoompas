package main.java.author.view.tabs.wave_editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.EnemyController;
import main.java.author.controller.tabbed_controllers.WaveController;
import main.java.author.util.ArrayUtil;
import main.java.author.util.JTableUtil;
import main.java.author.view.components.ColumnRemovableTableModel;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.WaveSpawnSchema;

/**
 * Used in the authoring environment to specify attributes of the wave
 */
public class WaveEditorTab extends EditorTab {

	private static final String WAVE_STRING = "Waves";

	private List<WaveSpawnSchema> myWaves;

	private String[] columnNames = {};
	private Object[][] data = {};

	private JTable table;
	private ColumnRemovableTableModel tableModel;

	private static int NUMBER_OF_WAVES = 0;

	private WaveTabContentCreator tabCreator = new WaveTabContentCreator();

	/**
	 * @param tabController
	 *            Constructor for the WaveEditorTab
	 */
	public WaveEditorTab(TabController tabController) {
		super(tabController);
		add(tabCreator.createWaveEditorContent(), BorderLayout.CENTER);
	}

	public void addNewEnemyColumn(String columnName) {
		List<Integer> zeroesColumnList = new ArrayList<Integer>();
		for (int i = 0 ; i< tableModel.getRowCount(); i ++) {
			zeroesColumnList.add(0);
		}
		tableModel.addColumn(columnName, zeroesColumnList.toArray());
	}

	/**
	 * Updates the Enemy List in the table every time a new enemy is added in
	 * EnemyEditorTab
	 */
	public void updateEnemyList() {
		WaveController waveController = (WaveController) myController;
		String[] newColumns = waveController.getEnemyNames();
		String[] oldColumns = JTableUtil.getColumnNames(tableModel);
		List<String> columnsToAdd = ArrayUtil.getElementsToAdd(newColumns,
				oldColumns, WAVE_STRING);
		List<String> columnsToRemove = ArrayUtil.getElementsToRemove(
				newColumns, oldColumns, WAVE_STRING);
		for (String columnToAdd : columnsToAdd) {
			addNewEnemyColumn(columnToAdd);
		}
		for (String columnToRemove : columnsToRemove) {
			tableModel.removeColumnAndData(table, JTableUtil
					.getColumnIndexFromName(tableModel, columnToRemove));
		}

	}

	@Override
	public void saveTabData() {
		// TODO CODY

	}

	/**
	 * Creates the content of the Wave Editor Tab
	 */
	private class WaveTabContentCreator {
		// Need to refactor all of the action listeners in the Button Maker
		// class

		/**
		 * @return Creates the panel with all of the Wave Editor Content
		 */
		public JComponent createWaveEditorContent() {

			ButtonMaker buttonMaker = new ButtonMaker();

			JPanel content = new JPanel(new BorderLayout());

			content.add(createTable(), BorderLayout.WEST);
			content.add(buttonMaker.makeButtons(), BorderLayout.EAST);

			return content;
		}

		/**
		 * @return Creates a table used to specify the attributes of each wave
		 */
		public JComponent createTable() {
			WaveController waveController = (WaveController) myController;

			columnNames = waveController.getEnemyNames();
			String[] columnNamesAndWave = new String[columnNames.length + 1];
			columnNamesAndWave[0] = WAVE_STRING;
			for (int i = 0; i < columnNames.length; i++) {
				columnNamesAndWave[i+1] = columnNames[i];
			}
			tableModel = new ColumnRemovableTableModel(data, columnNamesAndWave);
			table = new JTable(tableModel);

			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			JScrollPane sp = new JScrollPane(table,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sp.setPreferredSize(new Dimension(1000, 550));

			return sp;
		}

		// Will make all of the values in the fields initially 0. Have to write
		// a method since list of enemies is dynamic
		public void populateTableCells() {

		}

		// Makes Zeros Column of length waves after a new enemy is added in the
		// Enemy Editor
		public Integer[] makeZerosColumn() {

			Integer[] zerosColumn = new Integer[NUMBER_OF_WAVES];

			return zerosColumn;

		}

		private class ButtonMaker {

			/**
			 * @return Makes a JPanel that contains all of the buttons used by
			 *         the Wave Editor Tab
			 */
			private JComponent makeButtons() {

				JPanel panel = new JPanel(new GridLayout(0, 1));

				panel.add(makeAddNewWaveButton(), BorderLayout.NORTH);
				panel.add(makeRemoveMostRecentWaveButton(), BorderLayout.CENTER);
				panel.add(makeRemoveWaveButton(), BorderLayout.CENTER);
				panel.add(makeClearAllWavesButton(), BorderLayout.SOUTH);
				panel.add(makeAddEnemyColumnTestButton(), BorderLayout.SOUTH);

				return panel;

			}

			private JComponent makeAddEnemyColumnTestButton() {

				JButton addEnemyColumn = new JButton("Add Enemy Column");

				addEnemyColumn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						// addNewEnemyColumn();

					}

				});

				return addEnemyColumn;

			}

			/**
			 * @return Makes a button that adds a new wave to the table
			 */
			private JComponent makeAddNewWaveButton() {

				JButton addNewWaveButton = new JButton("Add New Wave");

				addNewWaveButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						ColumnRemovableTableModel model = (ColumnRemovableTableModel) table
								.getModel();
						NUMBER_OF_WAVES++;
						model.addRow(new Object[] { "Wave " + NUMBER_OF_WAVES,
								new Integer(0), new Integer(0), new Integer(0) });

					}
				});

				return addNewWaveButton;
			}

			// Just here to test simpler case of removing rows
			/**
			 * @return Makes a button that removes the last wave
			 */
			private JComponent makeRemoveMostRecentWaveButton() {
				JButton removeMostRecentWaveButton = new JButton(
						"Remove Last Wave");

				removeMostRecentWaveButton
						.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								ColumnRemovableTableModel model = (ColumnRemovableTableModel) table
										.getModel();
								if (NUMBER_OF_WAVES > 0) {
									NUMBER_OF_WAVES--;
									model.removeRow(NUMBER_OF_WAVES);
								}
							}

						});

				return removeMostRecentWaveButton;
			}

			// Currently does nothing, will figure it out later
			/**
			 * @return Makes a button to remove a wave chosen by the user
			 */
			private JComponent makeRemoveWaveButton() {

				JButton removeWaveButton = new JButton("Remove Wave");

				removeWaveButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						ColumnRemovableTableModel model = (ColumnRemovableTableModel) table
								.getModel();
						// model.removeRow();
					}

				});

				return removeWaveButton;
			}

			/**
			 * @return Makes a button that clears all of the waves
			 */
			private JComponent makeClearAllWavesButton() {

				JButton clearAllWavesButton = new JButton("Clear All Waves");

				clearAllWavesButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						ColumnRemovableTableModel model = (ColumnRemovableTableModel) table
								.getModel();

						while (NUMBER_OF_WAVES > 0) {
							NUMBER_OF_WAVES--;
							model.removeRow(NUMBER_OF_WAVES);
						}

					}
				});

				return clearAllWavesButton;
			}
		}

	}

}
