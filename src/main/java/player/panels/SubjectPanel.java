package main.java.player.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import main.java.player.util.Observing;
import main.java.player.util.Subject;

public abstract class SubjectPanel extends JPanel implements Subject {

	protected List<Observing> observers;
	protected boolean hasChanged;

	public SubjectPanel(){
		super(new BorderLayout());
	}

	protected abstract void update(String objectName);
	
	public void register(Observing o) {
		if(!observers.contains(o)) observers.add(o);

	}

	public void unregister(Observing o) {
		if(observers.contains(o)) observers.remove(o);
	}

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
