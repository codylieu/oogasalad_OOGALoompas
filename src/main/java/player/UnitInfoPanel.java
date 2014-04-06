package main.java.player;

import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class UnitInfoPanel extends JPanel implements Observing {
	
	private TDPlayerEngine engine;
	private JTextArea unitInfoArea;

	public UnitInfoPanel(TDPlayerEngine engineInit) {
		engine = engineInit;
		unitInfoArea = new JTextArea();
		unitInfoArea.setEditable(false);
		add(unitInfoArea);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update() {		
		//unitInfoArea.setText(engine.getSelectedObject().toString());
	}

	@Override
	public void setSubjectState(Subject s) {
		
	}

}