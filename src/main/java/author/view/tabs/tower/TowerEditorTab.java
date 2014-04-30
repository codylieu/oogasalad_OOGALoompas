package main.java.author.view.tabs.tower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.TowerController;
import main.java.author.util.ComboBoxUtil;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.components.TowerBehaviorTogglingRadioButton;
import main.java.author.view.global_constants.ObjectEditorConstants;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.engine.objects.tower.TowerBehaviors;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.TowerSchema;

/**
 * @author garysheng The tab that corresponds to the creation of towers. Talks
 *         to a tab controller to pass information to the model
 * 
 */
public class TowerEditorTab extends ObjectEditorTab {

	private static final String NO_UPGRADE_PATH = "No Upgrade Path";

	private JSpinner healthSpinner, costSpinner, damageSpinner, rangeSpinner,
			buildUpSpinner, firingSpeedSpinner, shrapnelDamageSpinner,
			moneyFarmAmountSpinner, moneyFarmIntervalSpinner,
			freezeRatioSpinner, bulletPiercingSpinner;

	private TowerBehaviorTogglingRadioButton freezeToggleButton,
			shootsToggleButton, moneyFarmingToggleButton, bombingToggleButton;

	private JComboBox<String> upgradeDropDown;

	private ImageCanvas bulletImageCanvas, towerImageCanvas,
			shrapnelImageCanvas;
	private JButton collisionImageButton, shrapnelImageButton;

	private JTextArea descriptionTextArea;

	public TowerEditorTab(TabController towerController, String objectName) {
		super(towerController, objectName);
	}

	@Override
	public void saveTabData() {
		TowerController controller = (TowerController) myController;

		List<TowerSchema> towerSchemas = new ArrayList<TowerSchema>();
		for (TDObjectSchema tower : objectMap.values()) {
			TowerSchema towerSchema = new TowerSchema();
			Map<String, Serializable> towerAttributes = tower
					.getAttributesMap();

			for (String attribute : towerAttributes.keySet()) {
				Serializable castedAttribute = addCastToAttribute(towerAttributes
						.get(attribute));
				towerSchema.addAttribute(attribute, castedAttribute);
			}
			towerSchemas.add(towerSchema);
		}
		controller.addTowers(towerSchemas);
	}

	@Override
	protected void addListeners() {
		super.addListeners();
		shootsToggleButton.setFieldsToToggle(damageSpinner, firingSpeedSpinner,
				shrapnelDamageSpinner, rangeSpinner, freezeToggleButton,
				bombingToggleButton, bulletPiercingSpinner, freezeRatioSpinner);
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

		descriptionTextArea.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e) {
						updateSchemaDataFromView();
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						updateSchemaDataFromView();
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
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

	@Override
	protected TDObjectSchema createSpecificNewObject(String objectName) {
		return new TowerSchema(objectName);

	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new TowerTabViewBuilder(this);
	}

	@Override
	protected void updateSchemaDataFromView() {
		super.updateSchemaDataFromView();

		TDObjectSchema myCurrentObject = getSelectedObject();
		List<TowerBehaviors> behaviorsToggled = new ArrayList<TowerBehaviors>();
		for (TowerBehaviorTogglingRadioButton button : behaviorTogglingButtons) {
			if (button.isSelected()) {
				behaviorsToggled.add(button.getBehavior());
			}

		}
		myCurrentObject.addAttribute(TowerSchema.TOWER_BEHAVIORS,
				(Serializable) behaviorsToggled);
		// upgrade dropdown

		if (upgradeDropDown.getSelectedItem() != null
				&& upgradeDropDown.getSelectedItem().equals(NO_UPGRADE_PATH)) {
			myCurrentObject.addAttribute(upgradeDropDown.getName(), "");
		} else {
			myCurrentObject.addAttribute(upgradeDropDown.getName(),
					(String) upgradeDropDown.getSelectedItem());
		}
		// description
		myCurrentObject.addAttribute(ItemSchema.DESCRIPTION,
				descriptionTextArea.getText());
	}

	/**
	 * 
	 * puts the schema data into the view field
	 * 
	 * @param map
	 *            the object's schema attributes
	 * 
	 */
	@Override
	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
		super.updateViewWithSchemaData(map);
		// upgrade dropdown
		upgradeDropDown.removeAllItems();
		for (String tower : objectMap.keySet()) {
			if (!tower.equals(getSelectedObjectName()))
				upgradeDropDown.addItem(tower);
		}
		if (ComboBoxUtil.containsValue(upgradeDropDown,
				(String) map.get(upgradeDropDown.getName()))) {
			upgradeDropDown.setSelectedItem(map.get(upgradeDropDown.getName()));
		} else {
			upgradeDropDown.addItem(NO_UPGRADE_PATH);
			upgradeDropDown.setSelectedItem(NO_UPGRADE_PATH);
		}

		List<TowerBehaviors> behaviorsToToggle = (List<TowerBehaviors>) map
				.get(TowerSchema.TOWER_BEHAVIORS);
		for (TowerBehaviorTogglingRadioButton radioButton : behaviorTogglingButtons) {
			if (behaviorsToToggle.contains(radioButton.getBehavior())) {
				radioButton.setSelected(true);
			} else {
				radioButton.setSelected(false);
			}
		}
		// description
		descriptionTextArea.setText((String) map.get(TowerSchema.DESCRIPTION));

	}

	/**
	 * @author garysheng Concrete subclass of ObjectTabViewBuilder that creates
	 *         specific view parts for the TowerEditorTab
	 * 
	 */
	private class TowerTabViewBuilder extends ObjectTabViewBuilder {

		public TowerTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}

		/**
		 * @return the graphic pane related to the bullet graphic
		 */
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

		/**
		 * @return the graphic pane related to the shrapnel graphic
		 */
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

		/**
		 * @return the dropdown related to which tower upgrades to what
		 */
		private JComboBox<String> makeUpgradeDropdown() {

			JComboBox<String> result = new JComboBox<String>();
			result.setName(TowerSchema.UPGRADE_PATH);
			return result;
		}

		private JTextArea makeDescriptionArea() {
			JTextArea result = new JTextArea();
			result.setLineWrap(true);
			result.setName(TowerSchema.DESCRIPTION);
			return result;
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
			bulletPiercingSpinner = makeAttributeSpinner(TowerSchema.PIERCING_COUNT);
			freezeRatioSpinner = makeAttributeSpinner(
					TowerSchema.FREEZE_SLOWDOWN_PROPORTION, true);
			moneyFarmAmountSpinner = makeAttributeSpinner(TowerSchema.MONEY_GRANTED);
			moneyFarmIntervalSpinner = makeAttributeSpinner(TowerSchema.MONEY_GRANT_INTERVAL);
			// radio buttons
			shootsToggleButton = new TowerBehaviorTogglingRadioButton(
					TowerViewConstants.TOWER_BEHAVIOR_SHOOTS,
					TowerBehaviors.SHOOTING, true);
			freezeToggleButton = new TowerBehaviorTogglingRadioButton(
					TowerViewConstants.TOWER_BEHAVIOR_FREEZES,
					TowerBehaviors.FREEZING, true);
			bombingToggleButton = new TowerBehaviorTogglingRadioButton(
					TowerViewConstants.TOWER_BEHAVIOR_BOMBS,
					TowerBehaviors.BOMBING, true);
			moneyFarmingToggleButton = new TowerBehaviorTogglingRadioButton(
					TowerViewConstants.TOWER_BEHAVIOR_FARMS_MONEY,
					TowerBehaviors.MONEY_FARMING, true);
			// other
			upgradeDropDown = makeUpgradeDropdown();
			// description text
			descriptionTextArea = makeDescriptionArea();
			// canvases
			bulletImageCanvas = new ImageCanvas(true,
					TowerSchema.BULLET_IMAGE_NAME);
			shrapnelImageCanvas = new ImageCanvas(true,
					TowerSchema.SHRAPNEL_IMAGE_NAME);
			towerImageCanvas = new ImageCanvas(false, TDObjectSchema.IMAGE_NAME);
			// clump data types
			clumpFieldsIntoGroups();

		}

		/**
		 * groups fields into subgroups
		 */
		private void clumpFieldsIntoGroups() {
			JSpinner[] spinners = { healthSpinner, costSpinner, buildUpSpinner,
					damageSpinner, rangeSpinner, firingSpeedSpinner,
					shrapnelDamageSpinner, freezeRatioSpinner,
					bulletPiercingSpinner, moneyFarmAmountSpinner,
					moneyFarmIntervalSpinner };
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
			TowerBehaviorTogglingRadioButton[] buttons = { shootsToggleButton,
					freezeToggleButton, bombingToggleButton,
					moneyFarmingToggleButton };
			behaviorTogglingButtons = new ArrayList<TowerBehaviorTogglingRadioButton>(
					Arrays.asList(buttons));
			ImageCanvas[] canvases = { bulletImageCanvas, shrapnelImageCanvas,
					towerImageCanvas };
			imageCanvases = new ArrayList<ImageCanvas>(Arrays.asList(canvases));
		}

		@Override
		protected JComponent makeFieldPane() {
			JPanel result = new JPanel(new GridLayout(0, 3));
			for (JSpinner spinner : spinnerFields) {
				result.add(makeFieldTile(spinner));
			}
			result.add(makeFieldTile(upgradeDropDown));
			result.add(makeFieldTile(descriptionTextArea));
			return result;
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

		@Override
		protected JComponent makeSecondaryImagesGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(makeBulletGraphicPane(), BorderLayout.WEST);
			result.add(makeShrapnelGraphicPane(), BorderLayout.CENTER);
			return result;
		}

	}

}
