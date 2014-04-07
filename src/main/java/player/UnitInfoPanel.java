package main.java.player;

import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class UnitInfoPanel extends JPanel implements Observing {
	
	private TDPlayerEngine engine;
	private JTextArea unitInfoArea;

	public UnitInfoPanel() {
		unitInfoArea = new JTextArea();
		unitInfoArea.setEditable(false);
		add(unitInfoArea);
	}

	@Override
	public void update() {		
		//unitInfoArea.setText(engine.getSelectedObject().toString());
		unitInfoArea.setText(engine.getGameAttributes().get("Time"));
	}

	@Override
	public void setSubjectState(Subject s) {
		engine = (TDPlayerEngine) s;
	}

}