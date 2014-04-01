package test.java.author;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
	private static final int MOUSE_DOWN = -1; // 'special' value written to .txt file to recognize a mouse press
	private static final int MOUSE_UP = -2; // 'special' value written to .txt file to recognize a mouse release
	private static final int MAX_RECORD_TIME = 25; // (in seconds) can be changed, but kept small so we don't
	                                               // write too much data to the .txt file
	
	private static final int CARRIAGE_RETURN = 13;
	private static final int LINE_FEED = 10;
	private List<Integer> mouseXPos; // list of mouse movements, presses, and releases
	private List<Integer> mouseYPos; // list of mouse movements, presses, and releases
	private boolean mousePressed;    // is the mouse currently pressed down
	private boolean mouseReleased;   // has the mouse recently been released from a click/drag

	private boolean isRecording;     // is the program currently recording 
	
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

			if (mousePressed) {
				mouseXPos.add(MOUSE_DOWN);
				mouseYPos.add(MOUSE_DOWN);
				mousePressed = false;
			}
			if (mouseReleased) {
				mouseXPos.add(MOUSE_UP);
				mouseYPos.add(MOUSE_UP);
				mouseReleased = false;
			}
			
			if (shouldMark) {
				mouseXPos.add(MouseInfo.getPointerInfo().getLocation().x);
				mouseYPos.add(MouseInfo.getPointerInfo().getLocation().y);
			}
		}
		if (!isRecording) {
			preventLastClick();
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
				}
				if(e.toString().contains("MOUSE_RELEASED")) {
					mousePressed = false;
					mouseReleased = true;
				}
			}  
		}, eventMask);  
	}
	
	private void initKeyboard() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		    	String keyEventInfo = e.toString();
		        if (keyEventInfo.contains("KEY_PRESSED")) {
		        	mouseXPos.add(-1*e.getKeyCode());
		        	mouseYPos.add(-1*e.getKeyCode());
		        }
		        return false;
		      }
		});
	}
	
	
	private void preventLastClick() {
		for (int count = mouseXPos.size() - 1; count >= 0; count--) {
			if (mouseXPos.get(count).equals(MOUSE_DOWN)) {
				while (count < mouseXPos.size()) {
					mouseXPos.remove(count);
					mouseYPos.remove(count);
					return;
				}
			}
		}
	}

	public static void main (String [] args) {
		GUIAutomation automation = new GUIAutomation();
		System.out.println("Recording..");
		AuthoringView view = new AuthoringView(); // MAKE INSTANCE OF YOUR OWN VIEW HERE
		automation.initMouse();
		automation.initKeyboard();
		automation.allowStop();
		automation.initRobot();
		automation.record();

	}	
}
