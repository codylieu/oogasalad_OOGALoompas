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
	
	String[] columnNames = {"Wave:", "1", "2"};
	Object[][] data = {{"MonsterA", new Integer(0), new Integer(0)},
						{"MonsterB", new Integer(0), new Integer(0)},
						{"MonsterC", new Integer(0), new Integer(0)}
						};

	public WaveEditorTab(TabController tabController) {
		super(tabController);
		add(createTable(), BorderLayout.CENTER);
	}
	
	public JComponent createWaveEditorContent(){
		
		JPanel content = new JPanel(new BorderLayout());
		
		content.add(createTable(), BorderLayout.NORTH);
		content.add(makeNewWaveButton(), BorderLayout.SOUTH);
		
		return content;
	}
	
	public JComponent createTable(){
		
		JTable table = new JTable(data, columnNames);
		
		JScrollPane sp = new JScrollPane(table);
		
		return sp;
	}

	private JComponent makeNewWaveButton() {
		
		JButton addNewWaveButton = new JButton("Add New Row");
		
		addNewWaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return addNewWaveButton;
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
