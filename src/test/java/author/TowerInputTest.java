package test.java.author;

import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;

import main.java.author.view.AuthoringView;
import main.java.author.view.tabs.terrain.Canvas;

public class TowerInputTest {

	private static Robot robot;
	private static final int PIXEL_X_OFFSET = 25;
	private static final int PIXEL_Y_OFFSET = 115;
	private static final int MOUSE_DELAY_SLOW = 5;
	private static final int MOUSE_DELAY_FAST = 1;
	
	static {
		try {
			 robot = new Robot();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private void clickMouse() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	private void moveToTile(int row, int col, int mouseDelay) {
		int start_x = MouseInfo.getPointerInfo().getLocation().x;
		int start_y = MouseInfo.getPointerInfo().getLocation().y;
		
		int end_x = col*Canvas.TILE_SIZE + PIXEL_X_OFFSET;
		int end_y = row*Canvas.TILE_SIZE + PIXEL_Y_OFFSET;
		
		for (int i=0; i<100; i++){  
			int mov_x = ((end_x * i)/100) + (start_x*(100-i)/100);
			int mov_y = ((end_y * i)/100) + (start_y*(100-i)/100);
			robot.mouseMove(mov_x,mov_y);
			robot.delay(mouseDelay);
		}
	}
	
	private void moveToTile(int end_x, int end_y) {
		moveToTile(end_x, end_y, MOUSE_DELAY_SLOW);
	}
	
	private void moveToLoc(int end_x, int end_y, int mouseDelay) {
		int start_x = MouseInfo.getPointerInfo().getLocation().x;
		int start_y = MouseInfo.getPointerInfo().getLocation().y;
		
		for (int i=0; i<100; i++){  
			int mov_x = ((end_x * i)/100) + (start_x*(100-i)/100);
			int mov_y = ((end_y * i)/100) + (start_y*(100-i)/100);
			robot.mouseMove(mov_x,mov_y);
			robot.delay(mouseDelay);
		}
	}
	
	private void moveToLoc(int end_x, int end_y) {
		moveToLoc(end_x, end_y, MOUSE_DELAY_SLOW);
	}
	
	private void moveToTerrainEditor() {
		moveToLoc(500,70);
		click();
	}
	
	private void clickSand() {
		moveToLoc(640,320);
		click();
	}
	
	private void clickGrass() {
		moveToLoc(640,360);
		click();
	}
	
	private void updateTopTiles() {
		for (int row = 0; row <= 3; row++) {
			for (int col = 0; col <= 14; col++) {
				moveToTile(row,col, MOUSE_DELAY_FAST);
				robot.mousePress(InputEvent.BUTTON1_MASK);
			}
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
	}
	
	private void updateMiddleTiles() {
		for (int row = 4; row <= 9; row++) {
			for (int col = 0; col <= 14; col++) {
				moveToTile(row,col, MOUSE_DELAY_FAST);
				robot.mousePress(InputEvent.BUTTON1_MASK);
			}
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
	}
	
	private void updateLowerTiles() {
		for (int row = 10; row <= 14; row++) {
			for (int col = 0; col <= 14; col++) {
				moveToTile(row,col, MOUSE_DELAY_FAST);
				robot.mousePress(InputEvent.BUTTON1_MASK);
			}
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
	}
	
	private void click() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	
	public static void main(String[] args) throws Exception {
		TowerInputTest test = new TowerInputTest();
		new AuthoringView();
	    test.moveToTerrainEditor();
		test.clickSand();
		test.updateTopTiles();
		test.clickGrass();
		test.updateMiddleTiles();
		test.clickSand();
		test.updateLowerTiles();
	}
}