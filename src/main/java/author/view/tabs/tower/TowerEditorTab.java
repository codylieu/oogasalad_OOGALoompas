package main.java.author.view.tabs.tower;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.sun.corba.se.spi.ior.MakeImmutable;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.TowerController;
import main.java.author.util.GroupButtonUtil;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.TDObjectSchema;

public class TowerEditorTab extends ObjectEditorTab {

	private JSpinner healthSpinner, costSpinner, damageSpinner, rangeSpinner,
			buildUpSpinner, firingSpeedSpinner, shrapnelDamageSpinner, moneyFarmSpinner, freezeRatioSpinner;

	private JRadioButton freezeToggleButton, shootsToggleButton,
			moneyFarmingToggleButton, bombingToggleButton;

	private ButtonGroup rangeButtonGroup, sizeButtonGroup;

	private static final String TOGGLE_FREEZES = "Deals Frost Damage";
	private static final String TOGGLE_SHOOTS = "Can Shoot";
	private static final String TOGGLE_FARMS_MONEY = "Farms Money";
	private static final String TOGGLE_BOMBS = "Creates Shrapnel";

	public TowerEditorTab(TabController towerController, String objectName) {
		super(towerController, objectName);
	}

	protected TDObjectSchema createSpecificNewObject(String objectName) {
		return new TowerSchema(objectName);

	}

	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new TowerTabViewBuilder(this);
	}

	protected void clumpDataFields() {
		JSpinner[] spinners = { healthSpinner, costSpinner, damageSpinner,
				rangeSpinner, buildUpSpinner, firingSpeedSpinner,
				shrapnelDamageSpinner, moneyFarmSpinner, freezeRatioSpinner };
		spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
		JRadioButton[] buttons = { freezeToggleButton, shootsToggleButton,
				moneyFarmingToggleButton, bombingToggleButton };
		radioButtons = new ArrayList<JRadioButton>(Arrays.asList(buttons));
	
		// no radio buttons right now

	}

	protected void setDefaultObjectName() {
		objectName = "Tower A";
	}

	@Override
	protected void addListeners() {
		super.addListeners();
		shootsToggleButton.addActionListener(new FieldToggleActionListener(
				damageSpinner, firingSpeedSpinner, shrapnelDamageSpinner,
				rangeSpinner));
		bombingToggleButton.addActionListener(new FieldToggleActionListener(shrapnelDamageSpinner));
		//moneyFarmingToggleButton.addActionListener(new FieldToggleActionListener());
		//freezeToggleButton.addActionListener(new FieldToggleActionListener());
	}

	/**
	 * puts the view fields' data into the schema data
	 */
	protected void updateSchemaDataFromView() {
		// update schema with fields
		String name = getSelectedObjectName();
		TDObjectSchema myCurrentTower = objectMap.get(name);
		

		for (JSpinner spinner : spinnerFields) {
			
			myCurrentTower.addAttribute(spinner.getName(),
					(Integer) spinner.getValue());
		}
		
		/*
		 * Integer range = (Integer) myCurrentTower.addAttribute(
		 * TowerSchema.RANGE,
		 * GroupButtonUtil.getSelectedButtonText(rangeButtonGroup));
		 */
		// update schema with images
	}

	/**
	 * 
	 * puts the schema data into the view field
	 * 
	 * @param map
	 *            the monster's schema attributes
	 * 
	 */
	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
		// fields (spinners)

		for (JSpinner spinner : spinnerFields) {
			spinner.setValue(map.get(spinner.getName()));
		}

		/*
		 * ButtonModel selectedSizeButtonModel = null;
		 * 
		 * String sizeValue = (String) map.get(TowerSchema.TILE_SIZE);
		 * 
		 * for (JRadioButton radioButton : radioButtons) { ButtonModel theModel
		 * = radioButton.getModel(); String theButtonText =
		 * radioButton.getText(); if (theButtonText.equals(sizeValue))
		 * selectedSizeButtonModel = theModel; }
		 * 
		 * sizeButtonGroup.setSelected(selectedSizeButtonModel, true);
		 */
	}

	private class TowerTabViewBuilder extends ObjectTabViewBuilder {

		public TowerTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
			// TODO Auto-generated constructor stub
		}


		protected void instantiateFields() {
			healthSpinner = makeAttributeSpinner(TowerSchema.HEALTH);
			costSpinner = makeAttributeSpinner(TowerSchema.COST);
			damageSpinner = makeAttributeSpinner(TowerSchema.DAMAGE);
			rangeSpinner = makeAttributeSpinner(TowerSchema.RANGE);
			buildUpSpinner = makeAttributeSpinner(TowerSchema.BUILDUP);
			firingSpeedSpinner = makeAttributeSpinner(TowerSchema.FIRING_SPEED);
			shrapnelDamageSpinner = makeAttributeSpinner(TowerSchema.SHRAPNEL_DAMAGE);
			moneyFarmSpinner = makeAttributeSpinner(TowerSchema.MONEY_GRANTED);
			freezeRatioSpinner = makeAttributeSpinner(TowerSchema.FREEZE_SLOWDOWN_PROPORTION, true);

			
			
			shootsToggleButton = new JRadioButton(TOGGLE_SHOOTS, true);
			freezeToggleButton = new JRadioButton(TOGGLE_FREEZES, true);
			bombingToggleButton = new JRadioButton(TOGGLE_BOMBS, true);
			moneyFarmingToggleButton = new JRadioButton(TOGGLE_FARMS_MONEY,
					true);
		}


	}

	@Override
	public void saveTabData() {
		TowerController controller = (TowerController) myController;

	}

}
