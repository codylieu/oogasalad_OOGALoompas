package main.java.author.view.tabs.enemy;

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
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.tdobjects.TDObjectSchema;

//SplitPaneDemo itself is not a visible component.
@SuppressWarnings("serial")
public class EnemyEditorTab extends ObjectEditorTab {

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

	public EnemyEditorTab(TabController c) {
		super(c);
	}

	protected TDObjectSchema createSpecificNewObject(String objectName) {
		return new SimpleMonsterSchema(objectName);
	}

	protected TabViewBuilder createSpecificTabViewBuilder() {
		return new EnemyTabViewBuilder(this);
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
		defaultObjectName = "Monster A";
	}

	/**
	 * puts the view fields' data into the schema data
	 */
	protected void updateSchemaDataFromView() {
		// update schema with fields
		String name = getSelectedObjectName();
		TDObjectSchema myCurrentEnemy = objectMap.get(name);
		Integer health = (Integer) healthSpinner.getValue();
		myCurrentEnemy.addAttribute(MonsterSchema.HEALTH, health);
		Integer speed = (Integer) speedSpinner.getValue();
		myCurrentEnemy.addAttribute(MonsterSchema.SPEED, speed);
		Integer damage = (Integer) damageSpinner.getValue();
		myCurrentEnemy.addAttribute(MonsterSchema.DAMAGE, damage);
		Integer reward = (Integer) rewardSpinner.getValue();
		myCurrentEnemy.addAttribute(MonsterSchema.REWARD, reward);
		// update schema with buttons
		myCurrentEnemy.addAttribute(MonsterSchema.FLYING_OR_GROUND,
				GroupButtonUtil.getSelectedButtonText(flyingButtonGroup));
		myCurrentEnemy.addAttribute(MonsterSchema.TILE_SIZE,
				GroupButtonUtil.getSelectedButtonText(sizeButtonGroup));
		// update schema with images
	}

	private class EnemyTabViewBuilder extends TabViewBuilder {

		public EnemyTabViewBuilder(EditorTab editorTab) {
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

			groundButton = new JRadioButton(MonsterSchema.GROUND);
			flyingButton = new JRadioButton(MonsterSchema.FLYING);
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
			labels.add(new JLabel(EnemyViewConstants.HEALTH_STRING));
			labels.add(new JLabel(EnemyViewConstants.SPEED_STRING));
			labels.add(new JLabel(EnemyViewConstants.DAMAGE_STRING));
			labels.add(new JLabel(EnemyViewConstants.REWARD_STRING));
			labels.add(new JLabel(EnemyViewConstants.TYPE_STRING));
			labels.add(new JLabel(EnemyViewConstants.TILE_SIZE_STRING));
			return labels;
		}

	}

	@Override
	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
		// fields (spinners)

		healthSpinner.setValue(map.get(MonsterSchema.HEALTH));
		speedSpinner.setValue(map.get(MonsterSchema.SPEED));
		damageSpinner.setValue(map.get(MonsterSchema.DAMAGE));
		rewardSpinner.setValue(map.get(MonsterSchema.REWARD));

		// buttons
		ButtonModel selectedFlyButtonModel = null;
		ButtonModel selectedSizeButtonModel = null;
		String flyOrGroundValue = (String) map.get(MonsterSchema.FLYING_OR_GROUND);
		String tileSizeValue = (String) map.get(MonsterSchema.TILE_SIZE);

		for (JRadioButton radioButton : radioButtons) {
			ButtonModel theModel = radioButton.getModel();
			String theButtonText = radioButton.getText();
			if (theButtonText.equals(flyOrGroundValue))
				selectedFlyButtonModel = theModel;
			if (theButtonText.equals(tileSizeValue))
				selectedSizeButtonModel = theModel;
		}
		flyingButtonGroup.setSelected(selectedFlyButtonModel, true);
		sizeButtonGroup.setSelected(selectedSizeButtonModel, true);
		// images

	}

}