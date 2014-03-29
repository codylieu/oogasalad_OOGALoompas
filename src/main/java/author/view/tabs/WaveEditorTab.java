package main.java.author.view.tabs;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import main.java.author.view.Canvas;

public class WaveEditorTab extends JPanel{

	public WaveEditorTab(){
		add(new Canvas(), BorderLayout.CENTER);
	}
	
}
