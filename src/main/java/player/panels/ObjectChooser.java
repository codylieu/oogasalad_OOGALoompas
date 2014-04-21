package main.java.player.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import main.java.player.TDPlayerEngine;
import main.java.player.util.Observing;
import main.java.player.util.Subject;

public abstract class ObjectChooser extends SubjectPanel implements ActionListener{
	protected JComboBox<String> objectComboBox;
	protected TDPlayerEngine engine;
	protected String currentObjectName;
	//protected List<Observing> observers;
	//protected boolean hasChanged;
	protected Vector<String> comboBoxItems;
	protected DefaultComboBoxModel<String> comboBoxModel;
	
	public ObjectChooser(TDPlayerEngine myEngine){
		super();
		observers = new ArrayList<Observing>();
		hasChanged = false;
		engine = myEngine;
		register(engine);
		currentObjectName = "";
		initComboBox();
	}

	private void initComboBox(){
		comboBoxItems = new Vector<String>();
		comboBoxModel = new DefaultComboBoxModel<String>(comboBoxItems);		
		objectComboBox = new JComboBox<String>(comboBoxModel);
		objectComboBox.addActionListener(this);
		add(objectComboBox);
	}

	public abstract void getObjectNames();

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> myBox = (JComboBox<String>) e.getSource();
		String objectName = (String) myBox.getSelectedItem();
		update(objectName);
	}

	@Override
	protected void update(String itemName){
		currentObjectName = itemName;
		hasChanged = true;
		notifyObservers();
	}
		
	public String getObjectName(){
		return currentObjectName;
	}
}
