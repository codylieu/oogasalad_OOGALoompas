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


/**
 * Allows a user to log a series of different events which can then be replayed through
 * EventPlayback.java, allowing for a variety of applications.
 * 
 * 	1) GUI TESTING : Simple to construct visual tests of different GUI components. 
 * 
 * @author Stephen Hughes, with assistance from StackOverflow forums for maintaining
 *         both mouse and key listeners
 *         
 *         http://stackoverflow.com/questions/6708436/java-mouselistener
 *         http://stackoverflow.com/questions/5344823/how-can-i-listen-for-key-presses-within-java-swing-accross-all-components
 *
 */
public class EventRecorder extends JPanel{
	
	private List<UserInputCommand> myRecordedData = new ArrayList<UserInputCommand>();
	private boolean isRecording;    
	private static final int MAX_RECORD_TIME = 25; // (in seconds) 
	protected static final String MY_DATA_RECORD = "src/test/java/author/MyRecord.ser";
 
	
	/**
	 * Executes the recording user commands, which currently includes
	 * mouse movements, clicks, drags, and text entry
	 */
	private void record() {
		isRecording = true;
		long initTime = System.currentTimeMillis() / 1000;
		long currTime;
		
		int myLastX = MouseInfo.getPointerInfo().getLocation().x;
		int myLastY = MouseInfo.getPointerInfo().getLocation().y;
		
		while (isRecording && ((currTime = System.currentTimeMillis() / 1000) < MAX_RECORD_TIME + initTime)) {
			 // obtaining mouse absolute location
			int xLoc = MouseInfo.getPointerInfo().getLocation().x;
			int yLoc = MouseInfo.getPointerInfo().getLocation().y;
			
			// we prevent marking the position of identical, consecutive points as we are concerned with mouse
			// movement, rather than a stationary mouse. In addition, failing to do so results in extremely 
			// slow playback time as we would be taking measurements of the mouse position at a very high frequency
			boolean shouldUpdateMouse = !(xLoc == myLastX && yLoc == myLastY);                 
			if (shouldUpdateMouse) {
				myLastX = xLoc;
				myLastY = yLoc;
				myRecordedData.add(new MouseLocationCommand(xLoc, yLoc));
			}
		}
		removeLastMousePress();
		logToFile();
	}

	/**
	 * Logs information about the set of events that have occurred
	 * during the recording through serialization. A file specified
	 * by MY_DATA_RECORD is created
	 */
	private void logToFile() {
		try {
	        FileOutputStream fileOut = new FileOutputStream(MY_DATA_RECORD);
	        ObjectOutputStream objOut = new ObjectOutputStream (fileOut);
	        objOut.writeObject(myRecordedData);
	        fileOut.close();
	        objOut.close ();
	    } catch (Exception e) {
	        e.printStackTrace ();
	    }
	}

	/**
	 * Constructs a way in which the user can stop the 'recording,'
	 * implemented by provided a JButton for them to click on when
	 * the recording is finished
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
		stopFrame.setLocation(2500, 2500); // offsets the JButton so it does not appear over user's view
		stopFrame.pack();
		stopFrame.setVisible(true);
	}

	/**
	 * Creates a global mouse listener that works regardless of the GUI component
	 * that the mouse clicks on
	 */
	private void initMouseListener() {
		long mouseEventMask = AWTEvent.MOUSE_EVENT_MASK;  
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() { 
			@Override
			public void eventDispatched(AWTEvent e) { 
				if(e.getID() == MouseEvent.MOUSE_PRESSED) {
					myRecordedData.add(new MousePressCommand());
				}
				if(e.getID() == MouseEvent.MOUSE_RELEASED) {
					myRecordedData.add(new MouseReleaseCommand());
				}
			}  
		}, mouseEventMask);  
	}
	
	/**
	 * Creates a global keyboard listener that works regardless of the GUI component
	 * that is in focus when text is entered
	 */
	private void initKeyListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		        if (e.getID() == KeyEvent.KEY_PRESSED) {
		        	myRecordedData.add(new KeyPressCommand(e.getKeyCode()));
		        }
		        if (e.getID() == KeyEvent.KEY_RELEASED) {
		        	myRecordedData.add(new KeyReleaseCommand(e.getKeyCode()));
		        }
		        return false;
		      }
		});
	}
	
	/**
	 * Initializes all listeners
	 */
	private void initListeners() {
		initMouseListener();
		initKeyListener();
	}
	
	/**
	 * Removes the last recorded 'MouseEvent.MOUSE_PRESSED' if it were
	 * caused by clicking the provided 'Stop' JButton
	 */
	private void removeLastMousePress() {
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
		EventRecorder automation = new EventRecorder();
		System.out.println("Recording..");
		new AuthoringView(); // MAKE INSTANCE OF YOUR OWN VIEW HERE
		automation.initListeners();
		automation.allowStop();
		automation.record();
	}	
}
