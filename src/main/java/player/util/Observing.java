package main.java.player.util;

import java.util.List;
/**
 * Interface to be implemented by classes that are going to be observing a Subject
 * Note: when implementing this, you must call the setSubject method
 * @author Michael Han 
 */
public interface Observing {
	
	/**
	 * Implementation will vary depending on what the class implementing Observing needs to do when notified of a change
	 */	
	public void update();
	
	/**
	 * Sets the subject that the class implementing Observing will be observing
	 * @param s
	 */
	public void setSubject(Subject s);
	/**
	 * Allows for one Observing to observe multiple subjects
	 * @param s
	 */
	public void setSubject(List<Subject> s);
}
