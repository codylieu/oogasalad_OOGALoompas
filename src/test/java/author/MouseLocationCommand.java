package test.java.author;

import java.awt.MouseInfo;
import java.awt.Robot;

/**
 * Code for mouse speed implementation derived from 
 * 	http://stackoverflow.com/questions/5339325/java-robot-mouse-move-setting-speed
 */
public class MouseLocationCommand extends UserInputCommand {

	// number of steps taken to move between two points
	private static int STEPS = 10;
	private int yPos;
	private int xPos;
	
	public MouseLocationCommand(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	@Override
	public void execute(Robot r) {
		moveToLoc(xPos, yPos, 2, r);
	}
	
	private void moveToLoc(int finalX, int finalY, int mouseDelay, Robot r) {
		int initialX = MouseInfo.getPointerInfo().getLocation().x;
		int initialY = MouseInfo.getPointerInfo().getLocation().y;
		
		for (int index=0; index<=STEPS; index++){  
			int deltaX = ((finalX * index)/STEPS) + (initialX*(STEPS-index)/STEPS);
			int deltaY = ((finalY * index)/STEPS) + (initialY*(STEPS-index)/STEPS);
			r.mouseMove(deltaX, deltaY);
			r.delay(mouseDelay);
		}
	}
}
