package main.java.player.panels;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.player.TDPlayerEngine;
import main.java.player.util.Observing;
import main.java.player.util.Subject;

@SuppressWarnings("serial")
public abstract class ObservingPanel extends JPanel implements Observing{
	protected TDPlayerEngine engine;
	
	public abstract void update();
	
	@Override
	public void setSubject(Subject s) {
		engine = (TDPlayerEngine) s;
	}
	
	@Override
	public void setSubject(List<Subject> s) {
		// TODO Auto-generated method stub
		
	}
	
}