package test.java.author;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import main.java.author.view.AuthoringView;

public class EventPlayback {

	private List<UserInputCommand> myRecordedCommands;
	
	public EventPlayback() {
		play();
	}
	
	private void play() {

		try{
			FileInputStream fileIn = new FileInputStream(EventRecorder.MY_DATA_RECORD);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			myRecordedCommands = (ArrayList<UserInputCommand>) objIn.readObject();
			fileIn.close();
			objIn.close();
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		Robot myRobot = null;
		try {
			myRobot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		for (UserInputCommand command : myRecordedCommands) {
			command.execute(myRobot);
		}
	}

	public static void main (String [] args) {
		//new AuthoringView();
		new EventPlayback();
	}
	
}
