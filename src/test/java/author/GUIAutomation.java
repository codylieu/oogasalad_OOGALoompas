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
	
	private static final String X_POS = "xPos";
	private static final String Y_POS = "yPos";
	public static final String MOUSE_PRESS = "MousePress";
	public static final String MOUSE_RELEASE = "MouseRelease";
	public static final String KEY_PRESS = "KeyPress";
	public static final String KEY_RELEASE = "KeyRelease";
	
	private int xPosIndex = 0;
	private int yPosIndex = 0;
	private int mousePressIndex = 0;
	private int mouseReleaseIndex = 0;
	private int keyPressIndex = 0;
	private int keyReleaseIndex = 0;
	
	private Map<String, Integer> myRecordedData = new LinkedHashMap<String, Integer>();
	
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
		
		int myLastX = MouseInfo.getPointerInfo().getLocation().x;
		int myLastY = MouseInfo.getPointerInfo().getLocation().y;
		
		while (isRecording && (currTime = System.currentTimeMillis() / 1000) < MAX_RECORD_TIME + initTime) {
			 // obtaining mouse absolute location
			int xLoc = MouseInfo.getPointerInfo().getLocation().x;
			int yLoc = MouseInfo.getPointerInfo().getLocation().y;
			
			// we typically prevent marking the position of identical, consecutive points as we are concerned
			// with mouse movement, rather than a stationary mouse. In addition, failing to do so results in
			// extremely slow playback time as we would be taking measurements of the mouse position at a very high frequency
			boolean shouldMark = (myRecordedData.size() == 0) ? true : !(xLoc == myLastX
					&& yLoc == myLastY);                 
			
			if (shouldMark) {
				myLastX = xLoc;
				myLastY = yLoc;
			}

			if (mousePressed) {	
				myRecordedData.put(MOUSE_PRESS + (mousePressIndex++), MOUSE_DOWN);
				mousePressed = false;
			}
			if (mouseReleased) {
				myRecordedData.put(MOUSE_RELEASE + (mouseReleaseIndex++), MOUSE_UP);
				mouseReleased = false;
			}
			if (shouldMark) {
				myRecordedData.put(X_POS + (xPosIndex++), myLastX);
				myRecordedData.put(Y_POS + (yPosIndex++), myLastY);
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
			for (String command : myRecordedData.keySet()) {
				bw.write(command + ": " + myRecordedData.get(command) + "\n");
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
		        	myRecordedData.put(KEY_PRESS + (keyPressIndex++), e.getKeyCode());
		        }
		        if (keyEventInfo.contains("KEY_RELEASED")) {
		        	myRecordedData.put(KEY_RELEASE + (keyReleaseIndex++), e.getKeyCode());
		        }
		        return false;
		      }
		});
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
