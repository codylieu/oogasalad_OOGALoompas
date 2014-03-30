package test.java.author;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private static final int CLICK = -1;
	private static final int DRAG = -2;
	private boolean mousePressed;
	private boolean mouseReleased;
	private boolean shouldUpdatePos;
	private boolean hasBeenClicked;
	private int mousePressedX;
	private int mousePressedY;
	private int mouseReleasedX;
	private int mouseReleasedY;
	private boolean isRecording;
	
	private void record() {
		System.out.println("Recording..");
		isRecording = true;
		long initTime = System.currentTimeMillis() / 1000;
		long currTime;
		mouseXPos = new ArrayList<Integer>();
		mouseYPos = new ArrayList<Integer>();
		while (isRecording && (currTime = System.currentTimeMillis() / 1000) < 25 + initTime) {
			int xLoc = MouseInfo.getPointerInfo().getLocation().x;
			int yLoc = MouseInfo.getPointerInfo().getLocation().y;
			boolean shouldMark = (mouseXPos.size() == 0) ? true : !(xLoc == mouseXPos.get(mouseXPos.size() - 1)
					&& yLoc == mouseYPos.get(mouseYPos.size() - 1));

			if (shouldUpdatePos) {
				mousePressedX = MouseInfo.getPointerInfo().getLocation().x;
				mousePressedY = MouseInfo.getPointerInfo().getLocation().y;
				shouldUpdatePos = false;
			}
			if (mouseReleased) {
				mouseReleasedX = MouseInfo.getPointerInfo().getLocation().x;
				mouseReleasedY = MouseInfo.getPointerInfo().getLocation().y;
			}
			
			if (hasBeenClicked) {
				if (mousePressedX == mouseReleasedX && mousePressedY == mouseReleasedY) {
					mouseXPos.add(CLICK);
					mouseYPos.add(CLICK);
				} else {
					mouseXPos.add(DRAG);
					mouseYPos.add(DRAG);
				}
				hasBeenClicked = false;
			}
			
			if (shouldMark && !mousePressed) {
				mouseXPos.add(MouseInfo.getPointerInfo().getLocation().x);
				mouseYPos.add(MouseInfo.getPointerInfo().getLocation().y);
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
		JButton stopButton = new JButton("STOP RECORDING");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isRecording = false;
				mouseXPos.remove(mouseXPos.size() - 1);
				mouseYPos.remove(mouseYPos.size() - 1);
				System.out.println("Recording stopped.");
			}
		});
		
		stopButton.setBackground(Color.RED);
		stopButton.setOpaque(true);
		stopButton.setPreferredSize(new Dimension(200,100));
		stopFrame.getContentPane().add(stopButton, BorderLayout.CENTER);
		stopFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		stopFrame.setLocationRelativeTo(c);
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
				if(e.toString().contains("MOUSE_PRESSED")) {
					mousePressed = true;
					mouseReleased = false;
					shouldUpdatePos = true;
				}
				
				if(e.toString().contains("MOUSE_RELEASED")) {
					mousePressed = false;
					mouseReleased = true;
					hasBeenClicked = true;
				}
				
			}  
		}, eventMask);  

	}
	
/*	private int getXCor(AWTEvent e) {
		int absolute = e.toString().indexOf("absolute");
		int firstParenth = e.toString().indexOf("(", absolute);
		int endComma = e.toString().indexOf(",", firstParenth);
		String xCor = e.toString().substring(firstParenth + 1, endComma);
		System.out.println(xCor);
		return Integer.parseInt(xCor);
	}
	
	private int getYCor(AWTEvent e) {
		int absolute = e.toString().indexOf("absolute");
		int firstParenth = e.toString().indexOf("(", absolute);
		int firstComma = e.toString().indexOf(",",firstParenth);
		int endParenth = e.toString().indexOf(")", firstParenth);
		String yCor = e.toString().substring(firstComma + 1, endParenth);
		System.out.println(yCor);
		return Integer.parseInt(yCor);
	}*/

	public static void main (String [] args) {
		GUIAutomation automation = new GUIAutomation();
		AuthoringView view = new AuthoringView();
		automation.initMouse();
		automation.allowStop(null);
		automation.initRobot();
		automation.record();

	}	
}
