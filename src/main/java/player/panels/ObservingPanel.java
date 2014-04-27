package main.java.player.panels;

import javax.swing.JPanel;

import main.java.player.TDPlayerEngine;
import main.java.player.util.Observing;
import main.java.player.util.Subject;

@SuppressWarnings("serial")
public abstract class ObservingPanel extends JPanel implements Observing{
	protected TDPlayerEngine engine;
	
	public abstract void update();
	
	@Override
	public void addSubject(Subject s) {
		engine = (TDPlayerEngine) s;
	}
	
}