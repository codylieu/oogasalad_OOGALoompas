package main.java.author.view.tabs.wave;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.terrain.TileSelectionManager;

public class WaveEditorTab extends EditorTab{

	private static final String SAVE = "Save";
	private static final String CLEAR = "Clear All";
	private static final String REMOVE_WAVE = "Remove last wave";
	private static final String ADD_WAVE = "Add wave";
	private static final String REMOVE_ENEMY = "Remove enemy";
	private static final String ADD_ENEMY = "Add enemy";

	private TileSelectionManager myTileSelectionManager;

	private Canvas myCanvas;

	public WaveEditorTab(TabController controller){
		super(controller);
		myCanvas = new Canvas();
		add(myCanvas, BorderLayout.CENTER);
		add(myTileSelectionManager.getTileDisplayTabs(), BorderLayout.EAST);

	}

	private JTable makeTable() {
		return null;
	}

	private JPanel makeControlPanel() {
		JPanel result = new JPanel();
		result.setLayout(new GridLayout(1,0));
		result.add(makeClearButton());
		result.add(makeSaveButton());
		result.add(makeRemoveWaveButton());
		result.add(makeAddWaveButton());
		return result;
	}
	
	private JPanel makeEnemyPanel() {
		JPanel result = new JPanel();
		result.setLayout(new GridLayout(1,0));
		result.add(makeRemoveEnemyButton());
		result.add(makeAddEnemyButton());
		
		return result;
	}

	private JButton makeSaveButton() {
		JButton result = new JButton(SAVE);
		return result;
	}

	private JButton makeClearButton() {
		JButton result = new JButton(CLEAR);
		return result;
	}

	private JButton makeRemoveWaveButton() {
		JButton result = new JButton(REMOVE_WAVE);
		return result;	
	}
	
	private JButton makeAddWaveButton() {
		JButton result = new JButton(ADD_WAVE);
		return result;	
	}
	
	private JButton makeRemoveEnemyButton() {
		JButton result = new JButton(REMOVE_ENEMY);
		return result;	
	}
	
	private JButton makeAddEnemyButton() {
		JButton result = new JButton(ADD_ENEMY);
		return result;	
	}


	//table
	public void update() {

	}


}
