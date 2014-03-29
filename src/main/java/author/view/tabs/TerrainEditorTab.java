package main.java.author.view.tabs;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import main.java.author.view.Canvas;

public class TerrainEditorTab extends JPanel{

	public TerrainEditorTab(){
		add(new Canvas(), BorderLayout.CENTER);
	}
	
}
