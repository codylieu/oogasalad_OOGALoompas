package main.java.player.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import main.java.player.ITDPlayerEngine;
import main.java.reflection.Reflection;
/**
 * A JComboBox that is also a Subject(observable).
 * Notifies observers if user clicks on a different item in drop down box
 *  
 * @author Michael Han
 */
@SuppressWarnings("serial")
public class ObjectChooser extends JPanel implements ActionListener{
	protected JComboBox<String> objectComboBox;
	private List<String> objectNames;
	protected String currentObjectName;
	protected Vector<String> comboBoxItems;
	protected DefaultComboBoxModel<String> comboBoxModel;
	protected Object engine;
	
	
	public ObjectChooser(List<String> objectNamesList, Object myEngine){
		//super();
		//observers = new ArrayList<Observing>();
		//hasChanged = false;
		engine = (ITDPlayerEngine) myEngine;
		currentObjectName = "";
		objectNames = objectNamesList;
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
		populateComboBox();
		add(objectComboBox);
	}

	/**
	 * Populate the JComboBox using list from parameter
	 */
	public void populateComboBox(){
		comboBoxModel.removeAllElements();
		for (String s: objectNames) {
			comboBoxModel.addElement(s);
		}		
		objectComboBox.setSelectedIndex(0);
	}

	/**
	 * pulls the String that was clicked on in the combo box
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> myBox = (JComboBox<String>) e.getSource();
		Object objectName = (String) myBox.getSelectedItem();
		try {
			Method m = engine.getClass().getDeclaredMethod("setCurrentTowerType", Reflection.toClasses((Object[]) objectName));
		} catch (NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//engine.setCurrentTowerType(objectName);
	}
}