package main.java.player;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class TowerChooser extends JPanel implements ActionListener {
	private TDPlayerEngine engine;
	private String[] towerNameList;
	private JComboBox towerComboBox;
	public TowerChooser(TDPlayerEngine myEngine){
		super(new BorderLayout());
		engine = myEngine;
		initTowerList();
		initComboBox();
	}

	private void initComboBox(){
		towerComboBox = new JComboBox(towerNameList);
		towerComboBox.setSelectedIndex(0);
		towerComboBox.addActionListener(this);
		add(towerComboBox);
	}

	private void initTowerList(){
		//engine call to get list of towers, also need size of list
		//might end up just making into a list, then using separate method to convert to array
		//temporary putting in random words to test 
		towerNameList = new String[3];
		towerNameList[0] = "Add Tower";
		towerNameList[1] = "regular tower";
		towerNameList[2] = "powered up tower";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox myBox = (JComboBox) e.getSource();
		String towerName = (String) myBox.getSelectedItem();
		update(towerName);

	}

	private void update(String towerName){
		//method call on engine to switch type of tower
		System.out.println(towerName);
	}

}
