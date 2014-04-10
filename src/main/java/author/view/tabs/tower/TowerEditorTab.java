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

import main.java.author.controller.MainController;
import main.java.author.util.GroupButtonUtil;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.schema.MonsterSchema;
import main.java.schema.SimpleTowerSchema;
import main.java.schema.TDObjectSchema;

public class TowerEditorTab extends ObjectEditorTab {

	private JSpinner healthSpinner;
	private JSpinner speedSpinner;
	private JSpinner damageSpinner;
	private JSpinner rewardSpinner;

	private JRadioButton smallButton;
	private JRadioButton mediumButton;
	private JRadioButton largeButton;
	private JRadioButton flyingButton;
	private JRadioButton groundButton;

	private ButtonGroup sizeButtonGroup;
	private ButtonGroup flyingButtonGroup;

	public TowerEditorTab(MainController c) {
		super(c);
	}

	protected TDObjectSchema createSpecificNewObject(String objectName) {
		return new SimpleTowerSchema(objectName);
	}

	protected TabViewBuilder createSpecificTabViewBuilder() {
		return new TowerTabViewBuilder(this);
	}

	protected void initDataFields() {
		spinnerFields = new ArrayList<JSpinner>();
		spinnerFields.add(healthSpinner);
		spinnerFields.add(speedSpinner);
		spinnerFields.add(damageSpinner);
		spinnerFields.add(rewardSpinner);
		radioButtons = new ArrayList<JRadioButton>();
		radioButtons.add(smallButton);
		radioButtons.add(mediumButton);
		radioButtons.add(largeButton);
		radioButtons.add(flyingButton);
		radioButtons.add(groundButton);

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
		myCurrentTower.addAttribute(MonsterSchema.HEALTH, health.toString());
		Integer speed = (Integer) speedSpinner.getValue();
		myCurrentTower.addAttribute(MonsterSchema.SPEED, speed.toString());
		Integer damage = (Integer) damageSpinner.getValue();
		myCurrentTower.addAttribute(MonsterSchema.DAMAGE, damage.toString());
		Integer reward = (Integer) rewardSpinner.getValue();
		myCurrentTower.addAttribute(MonsterSchema.REWARD, reward.toString());
		// update schema with buttons
		myCurrentTower.addAttribute(MonsterSchema.FLYING_OR_GROUND,
				GroupButtonUtil.getSelectedButtonText(flyingButtonGroup));
		myCurrentTower.addAttribute(MonsterSchema.TILE_SIZE,
				GroupButtonUtil.getSelectedButtonText(sizeButtonGroup));
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
		healthSpinner.setValue(Integer.parseInt(map.get(MonsterSchema.HEALTH)
				.toString()));
		speedSpinner.setValue(Integer.parseInt(map.get(MonsterSchema.SPEED)
				.toString()));
		damageSpinner.setValue(Integer.parseInt(map.get(MonsterSchema.DAMAGE)
				.toString()));
		rewardSpinner.setValue(Integer.parseInt(map.get(MonsterSchema.REWARD)
				.toString()));
		// buttons
		ButtonModel selectedFlyButton = map.get(MonsterSchema.FLYING_OR_GROUND)
				.equals(MonsterSchema.FLYING_OR_GROUND_GROUND) ? groundButton
				.getModel() : flyingButton.getModel();
		ButtonModel selectedSizeButton;

		if (map.get(MonsterSchema.TILE_SIZE).equals(
				MonsterSchema.TILE_SIZE_SMALL))
			selectedSizeButton = smallButton.getModel();
		else if (map.get(MonsterSchema.TILE_SIZE).equals(
				MonsterSchema.TILE_SIZE_MEDIUM))
			selectedSizeButton = mediumButton.getModel();
		else
			selectedSizeButton = largeButton.getModel();
		flyingButtonGroup.setSelected(selectedFlyButton, true);
		sizeButtonGroup.setSelected(selectedSizeButton, true);
		// images

	}

	private class TowerTabViewBuilder extends TabViewBuilder {

		public TowerTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
			// TODO Auto-generated constructor stub
		}

		private Component makeDimensionPane() {
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(1, 0));

			result.setBorder(BorderFactory.createEmptyBorder(0, // top
					20, // left
					0, // bottom
					0)); // right

			smallButton = new JRadioButton(MonsterSchema.TILE_SIZE_SMALL);
			mediumButton = new JRadioButton(MonsterSchema.TILE_SIZE_MEDIUM);
			largeButton = new JRadioButton(MonsterSchema.TILE_SIZE_LARGE);
			sizeButtonGroup = new ButtonGroup();
			sizeButtonGroup.add(smallButton);
			sizeButtonGroup.add(mediumButton);
			sizeButtonGroup.add(largeButton);
			result.add(smallButton);
			result.add(mediumButton);
			result.add(largeButton);
			return result;
		}

		private JComponent makeTypePane() {
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(1, 0));

			result.setBorder(BorderFactory.createEmptyBorder(0, // top
					20, // left
					0, // bottom
					0)); // right

			groundButton = new JRadioButton(
					MonsterSchema.FLYING_OR_GROUND_GROUND);
			flyingButton = new JRadioButton(
					MonsterSchema.FLYING_OR_GROUND_FLYING);
			flyingButtonGroup = new ButtonGroup();
			flyingButtonGroup.add(groundButton);
			flyingButtonGroup.add(flyingButton);
			result.add(groundButton);
			result.add(flyingButton);
			return result;
		}

		@Override
		protected JComponent makeFieldPane() {

			JPanel result = new JPanel(new GridLayout(0, 1));

			healthSpinner = makeAttributeSpinner();
			speedSpinner = makeAttributeSpinner();
			damageSpinner = makeAttributeSpinner();
			rewardSpinner = makeAttributeSpinner();
			result.add(healthSpinner);
			result.add(speedSpinner);
			result.add(damageSpinner);
			result.add(rewardSpinner);
			result.add(makeTypePane());
			result.add(makeDimensionPane());
			return result;
		}

		@Override
		protected JComponent makeLabelPane() {

			JPanel labels = new JPanel(new GridLayout(0, 1));
			labels.add(new JLabel(TowerViewConstants.HEALTH_STRING));
			labels.add(new JLabel(TowerViewConstants.SPEED_STRING));
			labels.add(new JLabel(TowerViewConstants.DAMAGE_STRING));
			labels.add(new JLabel(TowerViewConstants.REWARD_STRING));
			labels.add(new JLabel(TowerViewConstants.TYPE_STRING));
			labels.add(new JLabel(TowerViewConstants.TILE_SIZE_STRING));
			return labels;
		}

	}

}
