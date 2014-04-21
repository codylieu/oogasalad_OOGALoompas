package main.java.player.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import main.java.player.util.Observing;
import main.java.player.util.Subject;
/**
 * A JPanel that can be extended to create panels that will be observed
 * 
 * @author Michael Han
 */
@SuppressWarnings("serial")
public abstract class SubjectPanel extends JPanel implements Subject {

	protected List<Observing> observers;
	protected boolean hasChanged;

	public SubjectPanel(){
		super(new BorderLayout());
	}
	/**
	 * Will vary depending on what you want the update to do
	 * @param objectName
	 */
	protected abstract void update(String objectName);

	/**
	 * registers Observing objects
	 */
	public void register(Observing o) {
		if(!observers.contains(o)) observers.add(o);
	}

	/**
	 * unregisters Observing objects
	 */
	public void unregister(Observing o) {
		if(observers.contains(o)) observers.remove(o);
	}
	
	/**
	 * notify all observers if something has changed
	 */
	public void notifyObservers() {
		List<Observing> localObservers = null;
		if(!hasChanged) return;
		localObservers = new ArrayList<Observing>(observers);
		hasChanged = false;
		for(Observing o: localObservers){
			o.update();
		}
	}


}
