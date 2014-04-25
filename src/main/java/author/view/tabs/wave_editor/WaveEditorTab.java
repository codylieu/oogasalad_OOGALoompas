package main.java.author.view.tabs.wave_editor;

import java.awt.BorderLayout;
import java.awt.Component;
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

import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.ClearCommand;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.EnemyController;
import main.java.author.controller.tabbed_controllers.WaveController;
import main.java.author.util.ArrayUtil;
import main.java.author.util.JTableUtil;
import main.java.author.view.components.ColumnRemovableTableModel;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.MonsterSpawnSchema;
import main.java.schema.WaveSpawnSchema;
import main.java.schema.tdobjects.MonsterSchema;

/**
 * Used in the authoring environment to specify attributes of the wave
 */
public class WaveEditorTab extends EditorTab {

	private static final String WAVE_COLUMN_STRING = "Waves";
	private static final String WAVE_STRING = "Wave";
	private static final String ZERO_STRING = "0";
	private static final String ONE_STRING = "1";

	private JButton addNewWaveButton;
	private JButton removeWaveButton;
	private JButton clearAllWavesButton;
	private JButton addEnemyButton;

	private String[] columnNames = {};
	private String[] columnNamesAndWave;
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

	/**
	 * @param columnName
	 * Called when the enemy list is added to in the Enemy Editor Tab
	 */
	private void addNewEnemyColumn(String columnName) {
		List<String> zeroesColumnList = new ArrayList<String>();
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			zeroesColumnList.add(ZERO_STRING);
		}
		tableModel.addColumn(columnName, zeroesColumnList.toArray());
	}

	/**
	 * Updates the wave column with the wave strings
	 */
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
				oldColumns, WAVE_COLUMN_STRING);
		List<String> columnsToRemove = ArrayUtil.getElementsToRemove(
				newColumns, oldColumns, WAVE_COLUMN_STRING);
		for (String columnToAdd : columnsToAdd) {
			addNewEnemyColumn(columnToAdd);
		}
		for (String columnToRemove : columnsToRemove) {
			tableModel.removeColumnAndData(table, JTableUtil
					.getColumnIndexFromName(tableModel, columnToRemove));
		}
	}
	
	/**
	 * @param enemyName
	 * @return
	 * Gets the column index of the enemy represented by the input string
	 */
	private int getColumnOfEnemy(String enemyName) {
		WaveController waveController = (WaveController) myController;
		String[] currentColumnNames = waveController.getEnemyNames();

		for (int index = 0; index < currentColumnNames.length; index++) {
			if(currentColumnNames[index].equals(enemyName)) {
				return index + 1; // because Wave # is not included in currentColumnNames
			}
		}
		return -1;
	}

	/**
	 * @param fieldValue
	 * Adds a new wave and populates the row based off of the input fieldValue String
	 */
	private void addNewWaveRow(String fieldValue){
		ColumnRemovableTableModel model = (ColumnRemovableTableModel) table
				.getModel();
		int newWaveNum = tableModel.getRowCount() + 1;
		List<String> zeroesRowList = new ArrayList<String>();
		zeroesRowList.add(WAVE_STRING + " " + newWaveNum);
		for (int i = 1; i < tableModel.getColumnCount(); i++) {
			zeroesRowList.add(fieldValue);

		}
		model.addRow(zeroesRowList.toArray());

		clearAllWavesButton.setEnabled(true);
	}

	@Override
	public void saveTabData() {
		WaveController waveController = (WaveController) myController;
		int numWaves = tableModel.getRowCount();

		List<WaveSpawnSchema> allWaveSpawnSchemas = new ArrayList<WaveSpawnSchema>();
		for (int waveRow = 0; waveRow < numWaves; waveRow++) {

			WaveSpawnSchema waveSpawnSchema = new WaveSpawnSchema();
			for (MonsterSchema monsterSchema : waveController.getMonsterSchemas()) {
				String monsterName = (String) monsterSchema.getAttributesMap().get(MonsterSchema.NAME);
				int columnOfEnemy = getColumnOfEnemy(monsterName);
				int numEnemies = Integer.parseInt((String) table.getModel().getValueAt(waveRow, columnOfEnemy));
				waveSpawnSchema.addMonsterSchema(new MonsterSpawnSchema(monsterSchema, numEnemies));
			}
			allWaveSpawnSchemas.add(waveSpawnSchema);
		}
		waveController.addWaves(allWaveSpawnSchemas);
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

			addNewWaveRow(ONE_STRING); // Setting default value

			return content;
		}

		/**
		 * @return Creates a table used to specify the attributes of each wave
		 */
		public JComponent createTable() {
			WaveController waveController = (WaveController) myController;

			columnNames = waveController.getEnemyNames();
			columnNamesAndWave = new String[columnNames.length + 1];
			columnNamesAndWave[0] = WAVE_COLUMN_STRING;
			for (int i = 0; i < columnNames.length; i++) {
				columnNamesAndWave[i + 1] = columnNames[i];
			}
			tableModel = new ColumnRemovableTableModel(data, columnNamesAndWave) {

				public boolean isCellEditable(int row, int col){

					if(col == 0){
						return false;
					}
					return true;

				}

			};
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

		/**
		 * Inner inner class to make buttons
		 */
		private class ButtonMaker {

			/**
			 * @return Makes a JPanel that contains all of the buttons used by
			 *         the Wave Editor Tab
			 */
			private JComponent makeButtons() {

				JPanel panel = new JPanel(new GridLayout(0, 1));

				panel.add(makeAddNewWaveButton(), BorderLayout.CENTER);
				panel.add(makeRemoveWaveButton(), BorderLayout.CENTER);
				panel.add(makeClearAllWavesButton(), BorderLayout.CENTER);
				panel.add(makeAddEnemyButton(), BorderLayout.CENTER);

				return panel;

			}

			private Component makeAddEnemyButton() {
				addEnemyButton = new JButton("Add Enemy");

				addEnemyButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						WaveController controller = (WaveController) myController;
						controller.shiftToEnemyTab();
					}

				});

				return addEnemyButton;
			}

			/**
			 * @return Makes a button that adds a new wave to the table
			 */
			private JComponent makeAddNewWaveButton() {

				addNewWaveButton = new JButton("Add New Wave");

				addNewWaveButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						addNewWaveRow(ZERO_STRING);

					}
				});

				return addNewWaveButton;
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

				clearAllWavesButton = new JButton("Clear All Waves");

				clearAllWavesButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						int numberOfWaves = tableModel.getRowCount();

						while (numberOfWaves > 0) {
							numberOfWaves--;
							tableModel.removeRow(numberOfWaves);
						}

						clearAllWavesButton.setEnabled(false);

					}
				});

				return clearAllWavesButton;
			}
		}

	}

}
