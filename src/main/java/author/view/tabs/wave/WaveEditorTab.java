package main.java.author.view.tabs.wave;

import java.awt.BorderLayout;
import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.terrain.Canvas;

public class WaveEditorTab extends EditorTab{
	public WaveEditorTab(TabController controller){
		super(controller);
		add(new Canvas(), BorderLayout.CENTER);
	}
}
