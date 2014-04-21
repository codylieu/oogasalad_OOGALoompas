package main.java.player.util;
/**
 * Interface to be implemented by classes that are going to be observed
 * Note:There is an Observable class in java, but this interface was created for 
 * classes that were already extending another class. When implementing this, 
 * you must register objects.
 * 
 * @author Michael Han
 */
public interface Subject {
	/**
	 * Registers an item so that Subject knows which objects to notify
	 * @param o
	 */
	public void register(Observing o);
	/**
	 * Unregisters an item
	 * @param o
	 */
	public void unregister(Observing o);

	/**
	 * Alerts all registered Observing objects of change
	 */
	public void notifyObservers();
}
