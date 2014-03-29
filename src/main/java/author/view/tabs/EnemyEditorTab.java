package main.java.author.view.tabs;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import main.java.author.view.Canvas;

public class EnemyEditorTab extends JPanel{
	
	public EnemyEditorTab(){
		add(new Canvas(), BorderLayout.CENTER);
	}
	
}