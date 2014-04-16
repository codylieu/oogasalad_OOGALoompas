package main.java.player.util;

public interface Subject {
	public void register(Observing o);
	public void unregister(Observing o);
	
	public void notifyObservers();
	}
