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

public class GUIAutomationPlayback {

	private List<UserInputCommand> myRecordedCommands;
	
	public GUIAutomationPlayback() {
		play();
	}
	
	private void play() {

		try{
			FileInputStream fin = new FileInputStream(GUIAutomation.MY_DATA_RECORD);
			ObjectInputStream ois = new ObjectInputStream(fin);
			myRecordedCommands = (ArrayList<UserInputCommand>) ois.readObject();
			ois.close();

		}catch(Exception ex){
			ex.printStackTrace();
		} 
		
		Robot myRobot;
		try {
			myRobot = new Robot();
		} catch (AWTException e) {
			myRobot = null;
		}

		for (UserInputCommand command : myRecordedCommands) {
			command.execute(myRobot);
		}
	}

	public static void main (String [] args) {
		new AuthoringView();
		new GUIAutomationPlayback();
	}
	
}
