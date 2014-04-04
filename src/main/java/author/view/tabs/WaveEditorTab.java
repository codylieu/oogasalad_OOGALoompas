package main.java.author.view.tabs;

import java.awt.BorderLayout;

import main.java.author.controller.MainController;
import main.java.author.view.Canvas;

public class WaveEditorTab extends EditorTab{
	public WaveEditorTab(MainController controller){
		super(controller);
		add(new Canvas(), BorderLayout.CENTER);
	}
}
