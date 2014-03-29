package main.java.author.view.tabs;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import main.java.author.controller.MainController;
import main.java.author.view.Canvas;

public class TerrainEditorTab extends EditorTab{

	public TerrainEditorTab(MainController controller){
		super(controller);
		add(new Canvas(), BorderLayout.CENTER);
	}
}
