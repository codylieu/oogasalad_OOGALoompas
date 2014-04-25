package test.java.engine;

import jgame.JGColor;
import jgame.platform.JGEngine;

public class TestJGEngine extends JGEngine {

	@Override
	public void initCanvas() {
//		 setCanvasSettings(25, 20, 32, 32, null, JGColor.black, null);
		 this.setTile(25, 20, "ha");
		
	}

	@Override
	public void initGame() {
		// TODO Auto-generated method stub
		
	}
	
}
