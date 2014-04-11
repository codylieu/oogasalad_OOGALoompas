package main.java.author.view.tabs.wave;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WaveTable extends JPanel implements Serializable{
    private boolean FLAG_DEBUG = false;
    private boolean ALLOW_COLUMN_SELECTION = false;
    private boolean ALLOW_ROW_SELECTION = true;
 
    private WaveTableModel myModel;
    
    public String[] columnNames = {"Wave"};
    
    public Object[][] data = {};
    
    public WaveTable(WaveTableModel model) {
    	myModel = model;
    }
    
    public WaveTable() {
        super(new GridLayout(1,0));
 
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
 
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (ALLOW_ROW_SELECTION) { // true by default
            ListSelectionModel rowSM = table.getSelectionModel();
            rowSM.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    //Ignore extra messages.
                    if (e.getValueIsAdjusting()) return;
 
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        System.out.println("No rows are selected.");
                    } else {
                        int selectedRow = lsm.getMinSelectionIndex();
                        System.out.println("Row " + selectedRow
                                           + " is now selected.");
                    }
                }
            });
        } else {
            table.setRowSelectionAllowed(false);
        }
 
        if (ALLOW_COLUMN_SELECTION) { // false by default
            if (ALLOW_ROW_SELECTION) {
                //We allow both row and column selection, which
                //implies that we *really* want to allow individual
                //cell selection.
                table.setCellSelectionEnabled(true);
            }
            table.setColumnSelectionAllowed(true);
            ListSelectionModel colSM =
                table.getColumnModel().getSelectionModel();
            colSM.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        System.out.println("No columns are selected.");
                    } else {
                        int selectedCol = lsm.getMinSelectionIndex();
                        System.out.println("Column " + selectedCol
                                           + " is now selected.");
                    }
                }
            });
        }
        if (FLAG_DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }
 
    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();
 
        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

	public Object getSelectionModel() {
		// TODO Auto-generated method stub
		return null;
	}
}