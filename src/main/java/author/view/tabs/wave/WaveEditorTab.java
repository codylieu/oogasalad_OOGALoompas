package main.java.author.view.tabs.wave;

import java.awt.BorderLayout;

import main.java.author.controller.tabbed_controllers.WaveController;
import main.java.author.view.tabs.terrain.Canvas;
import main.java.author.view.tabs.EditorTab;

public class WaveEditorTab extends EditorTab{
	public WaveEditorTab(WaveController controller){
		super(controller);
		add(new Canvas(), BorderLayout.CENTER);
	}
}
