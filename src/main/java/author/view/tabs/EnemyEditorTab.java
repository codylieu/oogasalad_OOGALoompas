package main.java.author.view.tabs;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import main.java.author.controller.MainController;
import main.java.author.view.Canvas;

public class EnemyEditorTab extends EditorTab{
	
	public EnemyEditorTab(MainController controller){
		super(controller);
		add(new Canvas(), BorderLayout.CENTER);
	}
	
}
