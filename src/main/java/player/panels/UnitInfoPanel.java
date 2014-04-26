package main.java.player.panels;

import java.util.List;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class UnitInfoPanel extends ObservingPanel{

	public static final String TIME = "Time";
	private JTextArea unitInfoArea;

	public UnitInfoPanel() {

		unitInfoArea = new JTextArea(5, 5);
		unitInfoArea.setEditable(false);
		unitInfoArea.setLineWrap(true);
		unitInfoArea.setWrapStyleWord(true);
		add(unitInfoArea);
	}

	@Override
	public void update() {		
		List<String> unitInfoList = engine.getCurrentDescription();
		if (!unitInfoList.isEmpty()) {
			String unitInfo = "";
			for(String s: unitInfoList){
				unitInfo += s + "\n";
			}
			unitInfoArea.setText(unitInfo);
		}
	}
}