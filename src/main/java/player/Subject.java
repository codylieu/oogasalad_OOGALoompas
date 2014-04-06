package main.java.player;

public interface Subject {
	public void register(Observing o);
	public void unregister(Observing o);
	
	public void notifyObservers();
	public Object getUpdate(Observing o);
}
