package main.java.author.view.tabs.wave;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

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
	private static final String WAVE_HEADER = "Waves";
	
	//private TileSelectionManager myTileSelectionManager;
	//private Canvas myCanvas;
	private WaveTable table;
	private WaveTableModel model;
	
	public WaveEditorTab(TabController controller){
		super(controller);
		//myCanvas = new Canvas();
		//add(myCanvas, BorderLayout.CENTER);
		//add(myTileSelectionManager.getTileDisplayTabs(), BorderLayout.EAST);
		model = new WaveTableModel();
		createGUI();
	}
	
	private void createGUI() {
		setLayout(new BorderLayout());
		table = new WaveTable(model);
        JPanel titlePane = new JPanel();
        JLabel waveLabel = new JLabel(WAVE_HEADER);
        titlePane.add(waveLabel);
        
        this.add(titlePane, BorderLayout.NORTH);
        this.add(makeEnemyPane(), BorderLayout.WEST);
        this.add(makeOptionPane(), BorderLayout.EAST);
        this.add(table, BorderLayout.CENTER);
        this.add(makeControlPane(), BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		//TODO
	}
	
	private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
        }
    }
 
    private class ColumnListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
        }
    }
	
	private JTextArea consoleText;
//	private JComponent testOutput() {
//		consoleText.setPreferredSize(new Dimension(500,500));
//		table.getSelectionModel().addListSelectionListener(
//				new ListSelectionListener() {
//					@Override
//					public void valueChanged(ListSelectionEvent event) {
//						int viewRow = table.getSelectedRow();
//						if (viewRow < 0) {
//							consoleText.setText("");
//						} else {
//							int modelRow = table.convertRowIndexToModel(viewRow);
//							consoleText.setText(
//									String.format("Selected Row in view: %d. " +
//											"Selected Row in model: %d.", 
//											viewRow, modelRow));
//						}
//					}
//				}
//		);
//		return consoleText;
//	}
	
	private JPanel makeControlPane() {
		JPanel result = new JPanel();
		result.setLayout(new FlowLayout());
		//this.add(testOutput());
		return result;
	}
	
	private JPanel makeOptionPane() {
		JPanel result = new JPanel();
		result.setLayout(new GridLayout(1,0));
		result.add(makeClearButton());
		result.add(makeSaveButton());
		result.add(makeRemoveWaveButton());
		result.add(makeAddWaveButton());
		return result;
	}
	
	private JPanel makeEnemyPane() {
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
		result.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = model.getRowCount()+1;
               // model.addRow(new Object[]{waveTextField.getText(),waveTextField.getText()});
            }
        });
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


	//updating table data
	//updating combobox
	public void update() {
		//controller.update();
	}
}
