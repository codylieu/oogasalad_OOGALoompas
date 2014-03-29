package main.java.author.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class WaveEditorTab extends JPanel{

	public WaveEditorTab(){
		add(new Canvas(), BorderLayout.CENTER);
	}
	
}
