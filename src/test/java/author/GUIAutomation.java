package test.java.author;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.author.view.AuthoringView;

public class GUIAutomation extends JPanel{
	private static Robot robot;
	private List<Integer> mouseXPos;
	private List<Integer> mouseYPos;
	private boolean isMousePressed;

	private void record() {
		long initTime = System.currentTimeMillis() / 1000;
		long currTime;
		mouseXPos = new ArrayList<Integer>();
		mouseYPos = new ArrayList<Integer>();
		while ((currTime = System.currentTimeMillis() / 1000) < 15 + initTime) {
			int xLoc = MouseInfo.getPointerInfo().getLocation().x;
			int yLoc = MouseInfo.getPointerInfo().getLocation().y;
			boolean shouldMark = (mouseXPos.size() == 0) ? true : !(xLoc == mouseXPos.get(mouseXPos.size() - 1)
					&& yLoc == mouseYPos.get(mouseYPos.size() - 1));
			if (shouldMark) {
				mouseXPos.add(MouseInfo.getPointerInfo().getLocation().x);
				mouseYPos.add(MouseInfo.getPointerInfo().getLocation().y);
			}
			if (isMousePressed) {
				mouseXPos.add(-7777777);
				mouseYPos.add(-7777777);
				isMousePressed = false;
			}
		}
		logToFile();
	}

	private void logToFile() {
		try {
			File file = new File("src/test/java/author/test.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int count = 0; count < mouseXPos.size(); count++) {
				bw.write("xPos: " + mouseXPos.get(count) + " yPos: " + mouseYPos.get(count) + "\n");
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void allowStop(Component c) {
		JFrame stopFrame = new JFrame();
		JButton stopButton = new JButton("STOP");
		stopButton.setPreferredSize(new Dimension(20,20));
		stopFrame.getContentPane().add(stopButton, BorderLayout.CENTER);
		stopFrame.setPreferredSize(new Dimension(20,20));
		stopFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		stopFrame.setLocationRelativeTo(c);
		stopFrame.setSize(new Dimension(20,20));
		stopFrame.pack();
		stopFrame.setVisible(true);
	}

	private void initRobot() {
		try {
			robot = new Robot();
		} catch (AWTException e) { e.printStackTrace(); }
	}

	private void initMouse() {
		long eventMask = AWTEvent.MOUSE_EVENT_MASK;  

		Toolkit.getDefaultToolkit().addAWTEventListener( new AWTEventListener()  
		{  
			public void eventDispatched(AWTEvent e)  
			{  
				if(e.toString().contains("MOUSE_CLICKED")) {
					isMousePressed = true;
				}
			}  
		}, eventMask);  

	}

	public static void main (String [] args) {
		GUIAutomation automation = new GUIAutomation();
		AuthoringView view = new AuthoringView();
		automation.initMouse();
		automation.allowStop(null);
		automation.initRobot();
		//automation.initMouse(view);
		automation.record();

	}	
}
