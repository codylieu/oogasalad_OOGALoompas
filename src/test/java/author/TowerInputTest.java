package test.java.author;

import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;

import main.java.author.view.AuthoringView;
import main.java.author.view.Canvas;

public class TowerInputTest {

	private static Robot robot;
	private static final int PIXEL_X_OFFSET = 25;
	private static final int PIXEL_Y_OFFSET = 95;
	private static final int MOUSE_DELAY_FAST = 5;
	
	static {
		try {
			 robot = new Robot();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private void testGUI() {
		moveToTile(5, 5);
		clickMouse();
		moveToTile(0, 0);
		clickMouse();
		moveToTile(5, 14);
		clickMouse();
		moveToTile(14, 5);
		clickMouse();
	}
	
	private void clickMouse() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	private void moveToTile(int row, int col) {
		int start_x = MouseInfo.getPointerInfo().getLocation().x;
		int start_y = MouseInfo.getPointerInfo().getLocation().y;
		
		int end_x = row*Canvas.TILE_SIZE + PIXEL_X_OFFSET;
		int end_y = col*Canvas.TILE_SIZE + PIXEL_Y_OFFSET;
		
		for (int i=0; i<100; i++){  
			int mov_x = ((end_x * i)/100) + (start_x*(100-i)/100);
			int mov_y = ((end_y * i)/100) + (start_y*(100-i)/100);
			robot.mouseMove(mov_x,mov_y);
			robot.delay(MOUSE_DELAY_FAST);
		}
	}
	
	public static void main(String[] args) throws Exception {
		TowerInputTest test = new TowerInputTest();
		new AuthoringView();
		test.testGUI();
	}
}