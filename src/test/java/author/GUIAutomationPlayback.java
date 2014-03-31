package test.java.author;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import main.java.author.view.AuthoringView;

public class GUIAutomationPlayback {
	
	private File myFile;
	private Robot robot;
	boolean mouseDown;
	private static final int MOUSE_DOWN = -1;
	private static final int MOUSE_UP = -2;
	
	public GUIAutomationPlayback(String filePath) {
		initRobot();
		initFile(filePath);
		play();
	}

	private void initFile(String path) {
		myFile = new File(path);
		if (myFile.exists()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(myFile));
				String line;
				while ((line = br.readLine()) != null) {
					String x = line.substring(line.indexOf(":") + 1, line.indexOf("y"));
					String y = line.substring(line.lastIndexOf(":") + 1);
					x = x.trim();
					y = y.trim();
					int xPos = Integer.parseInt(x);
					int yPos = Integer.parseInt(y);
					if (xPos == MOUSE_DOWN) {
						pressMouseDown();
					} else if (xPos == MOUSE_UP){
						pressMouseUp();
					} else {
						moveToLoc(xPos, yPos, 2);
					}
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initRobot() {
		try {
			robot = new Robot();
		} catch (AWTException e) {e.printStackTrace();}
	}
	
	private void play() {
		
	}
	
	private void pressMouseDown() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		mouseDown = true;
	}
	
	private void pressMouseUp() {
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		mouseDown = false;
	}
	
	private void moveToLoc(int end_x, int end_y, int mouseDelay) {
		int start_x = MouseInfo.getPointerInfo().getLocation().x;
		int start_y = MouseInfo.getPointerInfo().getLocation().y;
		
		int steps = 10;
		
		for (int i=0; i<=steps; i++){  
			int mov_x = ((end_x * i)/steps) + (start_x*(steps-i)/steps);
			int mov_y = ((end_y * i)/steps) + (start_y*(steps-i)/steps);
			robot.mouseMove(mov_x,mov_y);
			robot.delay(mouseDelay);
		}
	}

	public static void main (String [] args) {
		new AuthoringView();
		GUIAutomationPlayback playback = new GUIAutomationPlayback("src/test/java/author/test.txt");
	}
	
}
