package test.java.author;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class UserInputCommand implements Serializable{

	List<Integer> myCommandValues;
	
	public UserInputCommand(Integer ... commandValue) {
		myCommandValues = Arrays.asList(commandValue);
	}
	
	public String toString() {
		return this.getClass().getName();
	}
	
	public abstract void execute(Robot r);
	
}
