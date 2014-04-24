package main.java.player.panels;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.java.player.TDPlayerEngine;
import main.java.player.util.Observing;
import main.java.player.util.Subject;

@SuppressWarnings("serial")
public class UnitInfoPanel extends JPanel implements Observing {
	
	public static final String TIME = "Time";
	
	private TDPlayerEngine engine;
	private JTextArea unitInfoArea;

	public UnitInfoPanel() {
		unitInfoArea = new JTextArea(5, 10);
		unitInfoArea.setEditable(false);
		add(unitInfoArea);
	}

	@Override
	public void update() {		
		//unitInfoArea.setText(engine.getSelectedObject().toString());
		//unitInfoArea.setText(engine.getCurrentDescription());
		List<String> unitInfoList = engine.getCurrentDescription();
		String unitInfo = "";
		for(String s: unitInfoList){
			unitInfo += s + "\n";
		}
		unitInfoArea.setText(unitInfo);
	}

	@Override
	public void setSubject(Subject s) {
		engine = (TDPlayerEngine) s;
	}

	@Override
	public void setSubject(List<Subject> s) {
		// TODO Auto-generated method stub
		
	}

}