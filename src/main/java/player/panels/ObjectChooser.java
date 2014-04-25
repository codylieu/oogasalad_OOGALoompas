package main.java.player.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import main.java.player.ITDPlayerEngine;
import main.java.player.util.Observing;
/**
 * An abstract class that acts as a JComboBox that is also a Subject(observable).
 * Can easily be altered to be a different type(button, textfield, etc) that notifies other Observing objects.
 *  
 * @author Michael Han
 */
@SuppressWarnings("serial")
public abstract class ObjectChooser extends SubjectPanel implements ActionListener{
	protected JComboBox<String> objectComboBox;
	protected ITDPlayerEngine engine;
	protected String currentObjectName;
	protected Vector<String> comboBoxItems;
	protected DefaultComboBoxModel<String> comboBoxModel;
	
	public ObjectChooser(ITDPlayerEngine myEngine){
		super();
		observers = new ArrayList<Observing>();
		hasChanged = false;
		engine = myEngine;
		register((Observing) engine);
		currentObjectName = "";
		initComboBox();
	}
	
	/**
	 * initializes components relevant to JComboBox
	 */
	private void initComboBox(){
		comboBoxItems = new Vector<String>();
		comboBoxModel = new DefaultComboBoxModel<String>(comboBoxItems);		
		objectComboBox = new JComboBox<String>(comboBoxModel);
		objectComboBox.addActionListener(this);
		add(objectComboBox);
	}
	
	/**
	 * Left abstract because implementation will differ based on where you are getting the names from.
	 * Main purpose is to populate the JComboBox
	 */
	public abstract void getObjectNames();

	/**
	 * pulls the String that was clicked on in the combo box
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> myBox = (JComboBox<String>) e.getSource();
		String objectName = (String) myBox.getSelectedItem();
		update(objectName);
	}

	/**
	 * Changes currentObjectName to new name that user has just clicked on.
	 * Notifies observers
	 */
	@Override
	protected void update(String objectName){
		currentObjectName = objectName;
		hasChanged = true;
		notifyObservers();
	}
	
	/**
	 * @return currentObjectName
	 */
	public String getObjectName(){
		return currentObjectName;
	}
}