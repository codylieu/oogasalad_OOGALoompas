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

	private JRadioButton smallRangeButton;
	private JRadioButton mediumRangeButton;
	private JRadioButton largeRangeButton;
	private JRadioButton smallSizeButton;
	private JRadioButton largeSizeButton;

	private ButtonGroup rangeButtonGroup;
	private ButtonGroup sizeButtonGroup;

	public TowerEditorTab(TabController towerController) {
		super(towerController);
	}

	protected TDObjectSchema createSpecificNewObject(String objectName) {
		return new TowerSchema(objectName);

	}

	protected TabViewBuilder createSpecificTabViewBuilder() {
		return new TowerTabViewBuilder(this);
	}

	protected void initDataFields() {
		spinnerFields = new ArrayList<JSpinner>();
		spinnerFields.add(healthSpinner);
		spinnerFields.add(costSpinner);
		spinnerFields.add(damageSpinner);
		spinnerFields.add(buildUpSpinner);
		radioButtons = new ArrayList<JRadioButton>();
		radioButtons.add(smallRangeButton);
		radioButtons.add(mediumRangeButton);
		radioButtons.add(largeRangeButton);
		radioButtons.add(smallSizeButton);
		radioButtons.add(largeSizeButton);

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

		// buttons
		ButtonModel selectedRangeButtonModel = null;
		ButtonModel selectedSizeButtonModel = null;
		String rangeValue = (String) map.get(TowerSchema.RANGE);
		String sizeValue = (String) map.get(TowerSchema.TILE_SIZE);

		for (JRadioButton radioButton : radioButtons) {
			ButtonModel theModel = radioButton.getModel();
			String theButtonText = radioButton.getText();
			if (theButtonText.equals(rangeValue))
				selectedRangeButtonModel = theModel;
			if (theButtonText.equals(sizeValue))
				selectedSizeButtonModel = theModel;
		}
		rangeButtonGroup.setSelected(selectedRangeButtonModel, true);
		sizeButtonGroup.setSelected(selectedSizeButtonModel, true);

	}

	private class TowerTabViewBuilder extends TabViewBuilder {

		public TowerTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
			// TODO Auto-generated constructor stub
		}

		private Component makeRangePane() {
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(1, 0));

			result.setBorder(BorderFactory.createEmptyBorder(0, // top
					20, // left
					0, // bottom
					0)); // right

			smallRangeButton = new JRadioButton(TowerSchema.RANGE_SMALL);
			mediumRangeButton = new JRadioButton(TowerSchema.RANGE_MEDIUM);
			largeRangeButton = new JRadioButton(TowerSchema.RANGE_LARGE);
			rangeButtonGroup = new ButtonGroup();
			rangeButtonGroup.add(smallRangeButton);
			rangeButtonGroup.add(mediumRangeButton);
			rangeButtonGroup.add(largeRangeButton);
			result.add(smallRangeButton);
			result.add(mediumRangeButton);
			result.add(largeRangeButton);
			return result;
		}

		private JComponent makeSizePane() {
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(1, 0));

			result.setBorder(BorderFactory.createEmptyBorder(0, // top
					20, // left
					0, // bottom
					0)); // right

			largeSizeButton = new JRadioButton(TowerSchema.TILE_SIZE_LARGE);
			smallSizeButton = new JRadioButton(TowerSchema.TILE_SIZE_SMALL);
			sizeButtonGroup = new ButtonGroup();
			sizeButtonGroup.add(largeSizeButton);
			sizeButtonGroup.add(smallSizeButton);
			result.add(largeSizeButton);
			result.add(smallSizeButton);
			return result;
		}

		@Override
		protected JComponent makeFieldPane() {

			JPanel result = new JPanel(new GridLayout(0, 1));

			healthSpinner = makeAttributeSpinner();
			costSpinner = makeAttributeSpinner();
			damageSpinner = makeAttributeSpinner();
			buildUpSpinner = makeAttributeSpinner();
			result.add(healthSpinner);
			result.add(costSpinner);
			result.add(damageSpinner);
			result.add(buildUpSpinner);
			result.add(makeSizePane());
			result.add(makeRangePane());
			return result;
		}

		@Override
		protected JComponent makeLabelPane() {

			JPanel labels = new JPanel(new GridLayout(0, 1));
			labels.add(new JLabel(TowerViewConstants.HEALTH_STRING));
			labels.add(new JLabel(TowerViewConstants.COST_STRING));
			labels.add(new JLabel(TowerViewConstants.DAMAGE_STRING));
			labels.add(new JLabel(TowerViewConstants.BUILDUP_STRING));
			labels.add(new JLabel(TowerViewConstants.RANGE_STRING));
			labels.add(new JLabel(TowerViewConstants.TILE_SIZE_STRING));
			return labels;
		}

	}

}
