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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.author.view.AuthoringView;

public class GUIAutomation extends JPanel{
	
	public static final String MOUSE_PRESS = "MousePress";
	public static final String MOUSE_RELEASE = "MouseRelease";
	public static final String KEY_PRESS = "KeyPress";
	public static final String KEY_RELEASE = "KeyRelease";
	protected static final String MY_DATA_RECORD = "src/test/java/author/MyRecord.ser";
	
	private List<UserInputCommand> myRecordedData = new ArrayList<UserInputCommand>();
	
	private static final int MAX_RECORD_TIME = 25; // (in seconds) can be changed, but kept small so we don't
	                                               // exceed bounds of ArrayList

	private boolean isRecording;     // is the program currently recording 
	

	
	/**
	 * Executes the recording (really obtaining data for the logging) of mouse movements, clicks, drags
	 */
	private void record() {
		isRecording = true;
		long initTime = System.currentTimeMillis() / 1000;
		long currTime;
		
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

			if (shouldMark) {
				myRecordedData.add(new MouseLocationCommand(myLastX, myLastY));
			}
		}
		removeLastClick();
		logToFile();
	}

	/**
	 * Logs information about the set of events that have occurred
	 * during the recording
	 */
	private void logToFile() {
		try {
	        FileOutputStream fos = new FileOutputStream(MY_DATA_RECORD);
	        ObjectOutputStream oos = new ObjectOutputStream (fos);
	        oos.writeObject(myRecordedData);
	        fos.close();
	        oos.close ();
	    } catch ( Exception ex ) {
	        ex.printStackTrace ();
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
	 * Creates a global mouse listener, works regardless of the component the mouse clicks on
	 */
	private void initMouse() {
		long eventMask = AWTEvent.MOUSE_EVENT_MASK;  

		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {  
			public void eventDispatched(AWTEvent e) { 
				if(e.toString().contains("MOUSE_PRESSED")) {
					myRecordedData.add(new MousePressCommand());
				}
				if(e.toString().contains("MOUSE_RELEASED")) {
					myRecordedData.add(new MouseReleaseCommand());
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
		        	myRecordedData.add(new KeyPressCommand(e.getKeyCode()));
		        }
		        if (keyEventInfo.contains("KEY_RELEASED")) {
		        	myRecordedData.add(new KeyReleaseCommand(e.getKeyCode()));
		        }
		        return false;
		      }
		});
	}
	
	private void removeLastClick() {
		if (!isRecording) {
			for (int count = myRecordedData.size() - 1; count >= 0; count--) {
				if (myRecordedData.get(count) instanceof MousePressCommand) {
					myRecordedData.remove(count);
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
		automation.record();
	}	
}
