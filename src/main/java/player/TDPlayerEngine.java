package main.java.player;

import jgame.JGColor;
import jgame.platform.JGEngine;

public class TDPlayerEngine extends JGEngine {
	
	public TDPlayerEngine() {
		super();
		initEngineComponent(960, 640);
	}
	
	@Override
	public void initCanvas() {
		setCanvasSettings(15, 10, 32, 32, null, null, null);
	}

	@Override
	public void initGame() {
		setFrameRate(35, 2);
	}

	public void doFrame() {
		
	}
}
