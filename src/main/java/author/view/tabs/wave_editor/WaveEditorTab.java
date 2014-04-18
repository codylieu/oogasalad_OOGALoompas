package main.java.author.view.tabs.wave_editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
	private JButton removeWaveButton;
	private String[] columnNames = {};
	private Object[][] data = {};

	private JTable table;
	private ColumnRemovableTableModel tableModel;

	private WaveTabContentCreator tabCreator = new WaveTabContentCreator();

	/**
	 * @param tabController
	 *            Constructor for the WaveEditorTab
	 */
	public WaveEditorTab(TabController tabController) {
		super(tabController);
		add(tabCreator.createWaveEditorContent(), BorderLayout.CENTER);
	}

	private void addNewEnemyColumn(String columnName) {
		List<Integer> zeroesColumnList = new ArrayList<Integer>();
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			zeroesColumnList.add(0);
		}
		tableModel.addColumn(columnName, zeroesColumnList.toArray());
	}

	private void updateWaveColumn() {
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			int rowNum = i + 1;
			tableModel.setValueAt("Wave " + rowNum, i, 0);
		}

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
				columnNamesAndWave[i + 1] = columnNames[i];
			}
			tableModel = new ColumnRemovableTableModel(data, columnNamesAndWave);
			table = new JTable(tableModel);

			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent e) {
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							removeWaveButton.setEnabled(false);
						}
					}, 100);

				}

				@Override
				public void focusGained(FocusEvent e) {

					removeWaveButton.setEnabled(true);
				}
			});
			JScrollPane sp = new JScrollPane(table,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sp.setPreferredSize(new Dimension(1000, 550));

			return sp;
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

				JButton addEnemyColumn = new JButton("Add Enemy");

				addEnemyColumn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						WaveController controller = (WaveController) myController;
						controller.shiftToEnemyTab();
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
						int newWaveNum = tableModel.getRowCount() + 1;
						List<Object> zeroesRowList = new ArrayList<Object>();
						for (int i = 0; i < tableModel.getColumnCount(); i++) {
							if (i != 0) {
								zeroesRowList.add(0);
							} else
								zeroesRowList.add(WAVE_STRING + " "
										+ newWaveNum);
						}
						model.addRow(zeroesRowList.toArray());

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

								if (tableModel.getRowCount() > 0) {
									model.removeRow(tableModel.getRowCount() - 1);
								}
							}

						});

				return removeMostRecentWaveButton;
			}

			/**
			 * @return Makes a button to remove a wave chosen by the user
			 */
			private JComponent makeRemoveWaveButton() {

				removeWaveButton = new JButton("Remove Wave");

				removeWaveButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						tableModel.removeRow(table.getSelectedRow());
						updateWaveColumn();
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

						int numberOfWaves = tableModel.getRowCount();

						while (numberOfWaves > 0) {
							numberOfWaves--;
							tableModel.removeRow(numberOfWaves);
						}

					}
				});

				return clearAllWavesButton;
			}
		}

	}

}
