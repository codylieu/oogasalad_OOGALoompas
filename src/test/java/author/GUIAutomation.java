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
	private static final int CLICK = -1; // 'special' value written to .txt file to recognize a mouse click
	private static final int DRAG = -2; // 'special' value written to .txt file to recognize a mouse drag
	private static final int MAX_RECORD_TIME = 25; // (in seconds) can be changed, but kept small so we don't
	                                               // write too much data to the .txt file
	private List<Integer> mouseXPos; // list of mouse movements, clicks, and drags
	private List<Integer> mouseYPos; // list of mouse movements, clicks, and drags
	private boolean mousePressed;    // is the mouse currently pressed down
	private boolean mouseReleased;   // has the mouse recently been released from a click/drag
	private boolean shouldUpdatePos; // determines whether we should update the position of the initial mouse press 
	                                 // (only should update after a mouse click)
	private boolean hasBeenClicked;  // has the mouse recently been clicked
	private boolean isRecording;     // is the program currently recording 
	private int mousePressedX;       // x position of mouse upon its initial press
	private int mousePressedY;       // y position of mouse upon its initial press
	private int mouseReleasedX;      // x position of mouse upon its release
	private int mouseReleasedY;      // y position of mouse upon its release
	
	/**
	 * Executes the recording (really obtaining data for the logging) of mouse movements, clicks, drags
	 */
	private void record() {
		isRecording = true;
		long initTime = System.currentTimeMillis() / 1000;
		long currTime;
		mouseXPos = new ArrayList<Integer>();
		mouseYPos = new ArrayList<Integer>();
		
		while (isRecording && (currTime = System.currentTimeMillis() / 1000) < MAX_RECORD_TIME + initTime) {
			 // obtaining mouse absolute location
			int xLoc = MouseInfo.getPointerInfo().getLocation().x;
			int yLoc = MouseInfo.getPointerInfo().getLocation().y;
			
			// we typically prevent marking the position of identical, consecutive points as we are concerned
			// with mouse movement, rather than a stationary mouse. In addition, failing to do so results in
			// extremely slow playback time as we would be taking measurements of the mouse position at a very high frequency
			boolean shouldMark = (mouseXPos.size() == 0) ? true : !(xLoc == mouseXPos.get(mouseXPos.size() - 1)
					&& yLoc == mouseYPos.get(mouseYPos.size() - 1)); 
			                                                 

			// the code below distinguishes between a mouse click and a mouse drag (which is tricky). 
			// when the mouse is pressed down, we obtain the position of the mouse when it was pressed
			if (shouldUpdatePos) {
				mousePressedX = MouseInfo.getPointerInfo().getLocation().x;
				mousePressedY = MouseInfo.getPointerInfo().getLocation().y;
				shouldUpdatePos = false;
			}
			
			// when the mouse is released, we obtain the position of the mouse when it was released
			if (mouseReleased) {
				mouseReleasedX = MouseInfo.getPointerInfo().getLocation().x;
				mouseReleasedY = MouseInfo.getPointerInfo().getLocation().y;
			}
			
			// after the mouse has been pressed and released, we must determine whether this was a drag,
			// or just a click
			if (hasBeenClicked) {
				// logic to determine if press and release was a click
				if (mousePressedX == mouseReleasedX && mousePressedY == mouseReleasedY) {
					mouseXPos.add(CLICK);
					mouseYPos.add(CLICK);
				} else {
					mouseXPos.add(DRAG);
					mouseYPos.add(DRAG);
				}
				hasBeenClicked = false;
			}
			
			// we only mark the movement of the the mouse of the mouse is not currently pressed down.
			// if the mouse is pressed down, we may currently be in a drag. If so, the details of the
			// dragging are handled in GUIAutomationPlayback.java
			if (shouldMark && !mousePressed) {
				mouseXPos.add(MouseInfo.getPointerInfo().getLocation().x);
				mouseYPos.add(MouseInfo.getPointerInfo().getLocation().y);
			}
			
		}
		logToFile();
	}

	/**
	 * Logs information about the set of events that have occurred
	 * during the recording
	 */
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

	
	/**
	 * Constructs a way in which the user can stop the 'recording'
	 */
	private void allowStop() {
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
		stopFrame.setLocationRelativeTo(null); // offsets the JButton so it does not appear over user's view
		stopFrame.pack();
		stopFrame.setVisible(true);
	}

	
	/**
	 * Initializes java's Robot as a way of maintaining
	 * track of the state of your program
	 */
	private void initRobot() {
		try {
			robot = new Robot();
		} catch (AWTException e) { e.printStackTrace(); }
	}

	/**
	 * Creates a global mouse listener, works regardless of the component the mouse clicks on
	 */
	private void initMouse() {
		long eventMask = AWTEvent.MOUSE_EVENT_MASK;  

		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {  
			public void eventDispatched(AWTEvent e) { 
				if(e.toString().contains("MOUSE_PRESSED")) {
					mousePressed = true;
					mouseReleased = false;
					shouldUpdatePos = true;
				}
				if(e.toString().contains("MOUSE_RELEASED")) {
					mousePressed = false;
					mouseReleased = true;
					// upon a mouse release, we know that the mouse has been clicked (but, we have yet to
					// determine whether this click was a drag)
					hasBeenClicked = true;
				}
			}  
		}, eventMask);  
	}

	public static void main (String [] args) {
		GUIAutomation automation = new GUIAutomation();
		AuthoringView view = new AuthoringView(); // MAKE INSTANCE OF YOUR OWN VIEW HERE
		automation.initMouse();
		automation.allowStop();
		automation.initRobot();
		automation.record();

	}	
}
