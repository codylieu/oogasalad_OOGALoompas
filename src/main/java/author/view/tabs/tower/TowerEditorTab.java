package main.java.author.view.tabs.tower;

import java.awt.Component;
import java.awt.GridLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.TowerController;
import main.java.author.util.GroupButtonUtil;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.TDObjectSchema;

public class TowerEditorTab extends ObjectEditorTab {

	private JSpinner healthSpinner;
	private JSpinner costSpinner;
	private JSpinner damageSpinner;
	private JSpinner buildUpSpinner;
	private JSpinner firingSpeedSpinner;
	private JSpinner shrapnelDamageSpinner;
	
	private JRadioButton iceDamageButton;
	private JRadioButton largeSizeButton;

	private ButtonGroup rangeButtonGroup;
	private ButtonGroup sizeButtonGroup;

	public TowerEditorTab(TabController towerController) {
		super(towerController);
	}

	protected TDObjectSchema createSpecificNewObject(String objectName) {
		return new TowerSchema(objectName);

	}

	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new TowerTabViewBuilder(this);
	}

	protected void initDataFields() {
		spinnerFields = new ArrayList<JSpinner>();
		spinnerFields.add(healthSpinner);
		spinnerFields.add(costSpinner);
		spinnerFields.add(damageSpinner);
		spinnerFields.add(buildUpSpinner);
		radioButtons = new ArrayList<JRadioButton>();
		// no radio buttons right now

	}

	protected void setDefaultObjectName() {
		defaultObjectName = "Tower A";
	}

	/**
	 * puts the view fields' data into the schema data
	 */
	protected void updateSchemaDataFromView() {
		// update schema with fields
		String name = getSelectedObjectName();
		TDObjectSchema myCurrentTower = objectMap.get(name);
		Integer health = (Integer) healthSpinner.getValue();
		myCurrentTower.addAttribute(TowerSchema.HEALTH, health);
		Integer cost = (Integer) costSpinner.getValue();
		myCurrentTower.addAttribute(TowerSchema.COST, cost);
		Integer damage = (Integer) damageSpinner.getValue();
		myCurrentTower.addAttribute(TowerSchema.DAMAGE, damage);
		Integer buildUp = (Integer) buildUpSpinner.getValue();
		myCurrentTower.addAttribute(TowerSchema.BUILDUP, buildUp);
		// update schema with buttons
		myCurrentTower.addAttribute(TowerSchema.TILE_SIZE,
				GroupButtonUtil.getSelectedButtonText(sizeButtonGroup));
		myCurrentTower.addAttribute(TowerSchema.RANGE,
				GroupButtonUtil.getSelectedButtonText(rangeButtonGroup));
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

		healthSpinner.setValue(map.get(TowerSchema.HEALTH));
		costSpinner.setValue(map.get(TowerSchema.COST));
		damageSpinner.setValue(map.get(TowerSchema.DAMAGE));
		buildUpSpinner.setValue(map.get(TowerSchema.BUILDUP));

	/*
		ButtonModel selectedSizeButtonModel = null;
		
		String sizeValue = (String) map.get(TowerSchema.TILE_SIZE);

		for (JRadioButton radioButton : radioButtons) {
			ButtonModel theModel = radioButton.getModel();
			String theButtonText = radioButton.getText();
			if (theButtonText.equals(sizeValue))
				selectedSizeButtonModel = theModel;
		}
	
		sizeButtonGroup.setSelected(selectedSizeButtonModel, true);
*/
	}

	private class TowerTabViewBuilder extends ObjectTabViewBuilder {

		public TowerTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
			// TODO Auto-generated constructor stub
		}

		private JComponent makeSizePane() {
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(0, 1));

			result.setBorder(BorderFactory.createEmptyBorder(0, // top
					20, // left
					0, // bottom
					0)); // right

			largeSizeButton = new JRadioButton(TowerSchema.TILE_SIZE_LARGE);
			iceDamageButton = new JRadioButton(TowerSchema.TILE_SIZE_SMALL);
			sizeButtonGroup = new ButtonGroup();
			sizeButtonGroup.add(largeSizeButton);
			sizeButtonGroup.add(iceDamageButton);
			//result.add(new JLabel(TowerViewConstants.));
			result.add(largeSizeButton);
			result.add(iceDamageButton);
			return result;
		}

		@Override
		protected JComponent makeFieldPane() {

			JPanel result = new JPanel(new GridLayout(0, 2));
			healthSpinner = makeAttributeSpinner();
			costSpinner = makeAttributeSpinner();
			damageSpinner = makeAttributeSpinner();
			buildUpSpinner = makeAttributeSpinner();
			firingSpeedSpinner = makeAttributeSpinner();
			shrapnelDamageSpinner = makeAttributeSpinner();
			result.add(makeSpinnerField(TowerSchema.HEALTH, healthSpinner));
			result.add(makeSpinnerField(TowerSchema.COST, costSpinner));
			result.add(makeSpinnerField(TowerSchema.DAMAGE, damageSpinner));
			result.add(makeSpinnerField(TowerSchema.BUILDUP, buildUpSpinner));
			result.add(makeSpinnerField(TowerSchema.FIRING_SPEED,
					firingSpeedSpinner));
			result.add(makeSpinnerField(TowerSchema.SHRAPNEL_DAMAGE,
					shrapnelDamageSpinner));
			return result;
		}

	}

	@Override
	public void saveTabData() {
		TowerController controller = (TowerController) myController;

	}

}
