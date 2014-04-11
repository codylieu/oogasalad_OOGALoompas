package main.java.author.view.tabs.wave;

import java.awt.BorderLayout;

import main.java.author.controller.TabController;
import main.java.author.view.tabs.terrain.Canvas;
import main.java.author.view.tabs.EditorTab;

public class WaveEditorTab extends EditorTab{
	public WaveEditorTab(TabController waveController){
		super(waveController);
		add(new Canvas(), BorderLayout.CENTER);
	}
}
