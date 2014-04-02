package test.java.author;

import java.awt.MouseInfo;
import java.awt.Robot;

public class MouseLocationCommand extends UserInputCommand {

	private int yPos;
	private int xPos;
	public MouseLocationCommand(int xPos, int yPos) {
		super(xPos, yPos);
		this.xPos = xPos;
		this.yPos = yPos;
	}

	@Override
	public void execute(Robot r) {
		moveToLoc(xPos, yPos, 2, r);

	}
	
	private void moveToLoc(int end_x, int end_y, int mouseDelay, Robot r) {
		int start_x = MouseInfo.getPointerInfo().getLocation().x;
		int start_y = MouseInfo.getPointerInfo().getLocation().y;
		
		int steps = 10;
		
		for (int i=0; i<=steps; i++){  
			int mov_x = ((end_x * i)/steps) + (start_x*(steps-i)/steps);
			int mov_y = ((end_y * i)/steps) + (start_y*(steps-i)/steps);
			r.mouseMove(mov_x,mov_y);
			r.delay(mouseDelay);
		}
	}
}
