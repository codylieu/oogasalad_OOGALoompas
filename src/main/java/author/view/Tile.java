package main.java.author.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Tile extends JPanel {
	
	private int myRow;    // 0 - NUM_ROWS
	private int myColumn; // 0 - NUM_COLS
	private Color myColor;
	private boolean isSelected;

	public Tile(int row, int column, Color color) {
	    myRow = row;
	    myColumn = column;
	    myColor = color;
	}
	
	public Color getColor() {
		return myColor;
	}
	
	public void setColor(Color color) {
		myColor = color;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	protected void toggleSelection() {
		isSelected = !isSelected;
	}
	
	public int getRow() {
		return myRow;
	}
	
	public int getCol() {
		return myColumn;
	}
}
