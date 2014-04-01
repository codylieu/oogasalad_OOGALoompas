package main.java.author.view.tabs;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import main.java.author.controller.MainController;
import main.java.author.view.Canvas;

public class TowerEditorTab extends EditorTab{

	public TowerEditorTab(MainController controller){
		super(controller);
		add(new Canvas(), BorderLayout.CENTER);
	}
	
}
