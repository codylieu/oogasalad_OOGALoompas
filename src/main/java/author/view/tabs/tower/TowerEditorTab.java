package main.java.author.view.tabs.tower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.TowerController;
import main.java.author.util.ComboBoxUtil;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.components.SpinnerTogglingRadioButton;
import main.java.author.view.global_constants.ObjectEditorConstants;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.engine.objects.TDObject;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.TDObjectSchema;

public class TowerEditorTab extends ObjectEditorTab {

	private JSpinner healthSpinner, costSpinner, damageSpinner, rangeSpinner,
			buildUpSpinner, firingSpeedSpinner, shrapnelDamageSpinner,
			moneyFarmAmountSpinner, moneyFarmIntervalSpinner,
			freezeRatioSpinner;

	private SpinnerTogglingRadioButton freezeToggleButton, shootsToggleButton,
			moneyFarmingToggleButton, bombingToggleButton;

	private JComboBox<String> upgradeDropDown;

	protected ImageCanvas bulletImageCanvas, towerImageCanvas, shrapnelImageCanvas;
	protected JButton collisionImageButton;
	protected JButton shrapnelImageButton;

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

	/**
	 * 
	 * puts the schema data into the view field
	 * 
	 * @param map
	 *            the object's schema attributes
	 * 
	 */
	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
		super.updateViewWithSchemaData(map);

		upgradeDropDown.removeAllItems();
		for (String tower : objectMap.keySet()) {
			if (!tower.equals(getSelectedObjectName()))
				upgradeDropDown.addItem(tower);
		}

		if (ComboBoxUtil.containsValue(upgradeDropDown,
				(String) map.get(upgradeDropDown.getName()))) {
			upgradeDropDown.setSelectedItem(map.get(upgradeDropDown.getName()));
		} else {
			upgradeDropDown.addItem(TowerSchema.UPGRADE_PATH_NONE);
			upgradeDropDown.setSelectedItem(TowerSchema.UPGRADE_PATH_NONE);
		}

	}

	protected void updateSchemaDataFromView() {
		super.updateSchemaDataFromView();
		getSelectedObject().addAttribute(upgradeDropDown.getName(),
				(String) upgradeDropDown.getSelectedItem());

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
		upgradeDropDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSchemaDataFromView();
			}
		});

		shrapnelImageButton.addActionListener(new FileChooserListener(
				shrapnelImageCanvas));
		collisionImageButton.addActionListener(new FileChooserListener(
				bulletImageCanvas));
		towerImageButton.addActionListener(new FileChooserListener(
				towerImageCanvas));
	}

	private class TowerTabViewBuilder extends ObjectTabViewBuilder {

		public TowerTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void instantiateAndClumpFields() {
			// spinners
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
			// radio buttons
			shootsToggleButton = new SpinnerTogglingRadioButton(
					TowerSchema.TOWER_BEHAVIOR_SHOOTS, true);
			freezeToggleButton = new SpinnerTogglingRadioButton(
					TowerSchema.TOWER_BEHAVIOR_FREEZES, true);
			bombingToggleButton = new SpinnerTogglingRadioButton(
					TowerSchema.TOWER_BEHAVIOR_BOMBS, true);
			moneyFarmingToggleButton = new SpinnerTogglingRadioButton(
					TowerSchema.TOWER_BEHAVIOR_FARMS_MONEY, true);
			// other
			upgradeDropDown = makeUpgradeDropdown();
			// canvases
			bulletImageCanvas = new ImageCanvas(true,
					TowerSchema.BULLET_IMAGE_NAME);
			shrapnelImageCanvas = new ImageCanvas(true,
					TowerSchema.SHRAPNEL_IMAGE_NAME);
			towerImageCanvas = new ImageCanvas(false,
					TowerSchema.TOWER_IMAGE_NAME);
			// clump data types
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
			ImageCanvas[] canvases = { bulletImageCanvas, shrapnelImageCanvas,
					towerImageCanvas };
			imageCanvases = new ArrayList<ImageCanvas>(Arrays.asList(canvases));

		}

		private JComboBox<String> makeUpgradeDropdown() {

			JComboBox<String> result = new JComboBox<String>();
			result.setName(TowerSchema.UPGRADE_PATH);
			return result;
		}

		protected JComponent makeFieldPane() {
			JPanel result = new JPanel(new GridLayout(0, 3));
			for (JSpinner spinner : spinnerFields) {
				result.add(makeFieldTile(spinner));
			}
			result.add(makeFieldTile(upgradeDropDown));
			return result;
		}

		@Override
		protected JComponent makeSecondaryImagesGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(makeBulletGraphicPane(), BorderLayout.WEST);
			result.add(makeShrapnelGraphicPane(), BorderLayout.CENTER);
			return result;
		}

		private JComponent makeBulletGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			bulletImageCanvas.setSize(new Dimension(
					ObjectEditorConstants.IMAGE_CANVAS_SIZE / 2,
					ObjectEditorConstants.IMAGE_CANVAS_SIZE / 2));
			bulletImageCanvas.setBackground(Color.BLACK);
			result.add(bulletImageCanvas, BorderLayout.CENTER);
			collisionImageButton = makeChooseGraphicsButton("Set Bullet Image");
			result.add(collisionImageButton, BorderLayout.SOUTH);
			return result;
		}

		private JComponent makeShrapnelGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			shrapnelImageCanvas.setSize(new Dimension(
					ObjectEditorConstants.IMAGE_CANVAS_SIZE / 2,
					ObjectEditorConstants.IMAGE_CANVAS_SIZE / 2));
			shrapnelImageCanvas.setBackground(Color.BLACK);
			result.add(shrapnelImageCanvas, BorderLayout.CENTER);
			shrapnelImageButton = makeChooseGraphicsButton("Set Shrapnel Image");
			result.add(shrapnelImageButton, BorderLayout.SOUTH);
			return result;
		}

		@Override
		protected String getObjectGraphicKey() {
			return TowerSchema.TOWER_IMAGE_NAME;
		}

		@Override
		protected JComponent makePrimaryObjectGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());

			towerImageCanvas.setSize(new Dimension(
					ObjectEditorConstants.IMAGE_CANVAS_SIZE,
					ObjectEditorConstants.IMAGE_CANVAS_SIZE));
			towerImageCanvas.setBackground(Color.BLACK);
			result.add(towerImageCanvas, BorderLayout.CENTER);
			towerImageButton = makeChooseGraphicsButton("Set " + objectName
					+ " Image");
			result.add(towerImageButton, BorderLayout.SOUTH);
			return result;
		}

	}

	@Override
	public void saveTabData() {
		TowerController controller = (TowerController) myController;

	}

}
