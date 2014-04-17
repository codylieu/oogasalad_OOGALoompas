package main.java.author.view.tabs.tower;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.TowerController;
import main.java.author.view.components.SpinnerTogglingRadioButton;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.TDObjectSchema;

public class TowerEditorTab extends ObjectEditorTab {

	private JSpinner healthSpinner, costSpinner, damageSpinner, rangeSpinner,
			buildUpSpinner, firingSpeedSpinner, shrapnelDamageSpinner,
			moneyFarmAmountSpinner, moneyFarmIntervalSpinner,
			freezeRatioSpinner;

	private SpinnerTogglingRadioButton freezeToggleButton, shootsToggleButton,
			moneyFarmingToggleButton, bombingToggleButton;

	private ButtonGroup rangeButtonGroup, sizeButtonGroup;

	public TowerEditorTab(TabController towerController, String objectName) {
		super(towerController, objectName);
	}

	@Override
	protected TDObjectSchema createSpecificNewObject(String objectName) {
		return new TowerSchema(objectName);

	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new TowerTabViewBuilder(this);
	}

	@Override
	protected void clumpDataFields() {
		JSpinner[] spinners = { healthSpinner, costSpinner, buildUpSpinner,
				damageSpinner, rangeSpinner, firingSpeedSpinner,
				shrapnelDamageSpinner, moneyFarmAmountSpinner,
				moneyFarmIntervalSpinner, freezeRatioSpinner };
		spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
		SpinnerTogglingRadioButton[] buttons = { shootsToggleButton,
				freezeToggleButton, moneyFarmingToggleButton,
				bombingToggleButton };
		radioButtons = new ArrayList<SpinnerTogglingRadioButton>(
				Arrays.asList(buttons));

		// no radio buttons right now

	}

	@Override
	protected void addListeners() {
		super.addListeners();
		shootsToggleButton.setFieldsToToggle(damageSpinner, firingSpeedSpinner,
				shrapnelDamageSpinner, rangeSpinner, freezeToggleButton,
				bombingToggleButton);
		bombingToggleButton.setFieldsToToggle(shrapnelDamageSpinner);
		moneyFarmingToggleButton.setFieldsToToggle(moneyFarmAmountSpinner,
				moneyFarmIntervalSpinner);
		freezeToggleButton.setFieldsToToggle(freezeRatioSpinner);
	}

	/**
	 * puts the view fields' data into the schema data
	 */
	@Override
	protected void updateSchemaDataFromView() {
		// update schema with fields
		String name = getSelectedObjectName();
		TDObjectSchema myCurrentTower = objectMap.get(name);

		for (JSpinner spinner : spinnerFields) {

			myCurrentTower.addAttribute(spinner.getName(),
					(Integer) spinner.getValue());
		}

		for (SpinnerTogglingRadioButton button : radioButtons) {
			myCurrentTower.addAttribute(button.getText(),
					(Boolean) button.isSelected());
		}
	}

	/**
	 * 
	 * puts the schema data into the view field
	 * 
	 * @param map
	 *            the monster's schema attributes
	 * 
	 */
	@Override
	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
		// fields (spinners)

		for (JSpinner spinner : spinnerFields) {
			spinner.setValue(map.get(spinner.getName()));
		}

		for (SpinnerTogglingRadioButton radioButton : radioButtons) {
			radioButton.setSelected((Boolean) map.get(radioButton.getText()));

		}
	}

	private class TowerTabViewBuilder extends ObjectTabViewBuilder {

		public TowerTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void instantiateFields() {
			healthSpinner = makeAttributeSpinner(TowerSchema.HEALTH);
			costSpinner = makeAttributeSpinner(TowerSchema.COST);
			damageSpinner = makeAttributeSpinner(TowerSchema.DAMAGE);
			rangeSpinner = makeAttributeSpinner(TowerSchema.RANGE);
			buildUpSpinner = makeAttributeSpinner(TowerSchema.BUILDUP);
			firingSpeedSpinner = makeAttributeSpinner(TowerSchema.FIRING_SPEED);
			shrapnelDamageSpinner = makeAttributeSpinner(TowerSchema.SHRAPNEL_DAMAGE);
			moneyFarmAmountSpinner = makeAttributeSpinner(TowerSchema.MONEY_GRANTED);
			moneyFarmIntervalSpinner = makeAttributeSpinner(TowerSchema.MONEY_GRANT_INTERVAL);
			freezeRatioSpinner = makeAttributeSpinner(
					TowerSchema.FREEZE_SLOWDOWN_PROPORTION, true);

			shootsToggleButton = new SpinnerTogglingRadioButton(
					TowerSchema.TOWER_BEHAVIOR_SHOOTS, true);
			freezeToggleButton = new SpinnerTogglingRadioButton(
					TowerSchema.TOWER_BEHAVIOR_FREEZES, true);
			bombingToggleButton = new SpinnerTogglingRadioButton(
					TowerSchema.TOWER_BEHAVIOR_BOMBS, true);
			moneyFarmingToggleButton = new SpinnerTogglingRadioButton(
					TowerSchema.TOWER_BEHAVIOR_FARMS_MONEY, true);
		}

	}

	@Override
	public void saveTabData() {
		TowerController controller = (TowerController) myController;

	}

}
