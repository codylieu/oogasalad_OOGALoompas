package main.java.author.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class TowerEditorTab extends JPanel{

	public TowerEditorTab(){
		add(new Canvas(), BorderLayout.CENTER);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setResizable(true);
//		pack();
//		setVisible(true);
	}
	
}
