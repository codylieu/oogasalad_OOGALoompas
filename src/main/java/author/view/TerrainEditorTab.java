package main.java.author.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class TerrainEditorTab extends JPanel{

	public TerrainEditorTab(){
		add(new Canvas(), BorderLayout.CENTER);
	}
	
}
