package main.java.author.view.tabs.enemy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.EnemyController;
import main.java.author.util.ComboBoxUtil;
import main.java.author.util.GroupButtonUtil;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.global_constants.ObjectEditorConstants;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.author.view.tabs.terrain.TerrainAttribute;
import main.java.schema.MonsterSpawnSchema;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.tdobjects.TDObjectSchema;

/**
 * @author garysheng The tab that corresponds to the creation of enemies. Talks
 *         to a tab controller to pass information to the model
 */
public class EnemyEditorTab extends ObjectEditorTab {

	private static final String NO_RES_PATH = "No Resurrection Path";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Set<Integer> flyingSet = new HashSet<Integer>(
			Arrays.asList(TerrainAttribute.Untraversable.getIndex()));

	public static final Set<Integer> groundSet = new HashSet<Integer>(
			Arrays.asList(TerrainAttribute.Untraversable.getIndex(),
					TerrainAttribute.Flyable.getIndex()));
	private JSpinner healthSpinner, speedSpinner, damageSpinner, rewardSpinner;

	private ImageCanvas monsterImageCanvas;
	private JButton monsterImageButton;
	private JRadioButton smallTileButton, mediumTileButton, largeTileButton,
			flyingButton, groundButton;
	private List<JRadioButton> allButtons;
	private ButtonGroup tileSizeGroup, flyingOrGroundGroup;
	private List<MonsterSchema> monsterSchemas;
	private JComboBox<String> resDropDown;

	private JSpinner resNumSpinner;
	public EnemyEditorTab(TabController towerController, String objectName) {
		super(towerController, objectName);
	}

	/**
	 * @return the list of enemy names
	 */
	public String[] getEnemyNamesArray() {
		int size = objectMap.keySet().size();
		return objectMap.keySet().toArray(new String[size]);
	}

	/**
	 * @return the list of monster schemas created so far in this tab
	 */
	public List<MonsterSchema> getMonsterSchemas() {
		return monsterSchemas;
	}

	@Override
	public void saveTabData() {

		EnemyController controller = (EnemyController) myController;

		monsterSchemas = new ArrayList<MonsterSchema>();
		for (TDObjectSchema monster : objectMap.values()) {
			SimpleMonsterSchema monsterSchema = new SimpleMonsterSchema();
			Map<String, Serializable> monsterAttributes = monster
					.getAttributesMap();

			for (String attribute : monsterAttributes.keySet()) {
				Serializable castedAttribute = addCastToAttribute(monsterAttributes
						.get(attribute));
				monsterSchema.addAttribute(attribute, castedAttribute);
			}
			monsterSchemas.add(monsterSchema);
		}
		controller.addEnemies(monsterSchemas);
	}

	@Override
	protected void addListeners() {
		super.addListeners();
		monsterImageButton.addActionListener(new FileChooserListener(
				monsterImageCanvas));
		/*
		 * collisionImageButton.addActionListener(new FileChooserListener(
		 * collisionImageCanvas));
		 */
		for (JRadioButton button : allButtons) {
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					updateSchemaDataFromView();
				}
			});
		}

		resDropDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSchemaDataFromView();
			}
		});
	}

	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		return new SimpleMonsterSchema(name);
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new EnemyTabViewBuilder(this);
	}

	@Override
	protected void updateSchemaDataFromView() {
		super.updateSchemaDataFromView();
		TDObjectSchema myCurrentObject = getSelectedObject();
		Set blockedTilesSet;
		// flying or ground
		if (GroupButtonUtil.getSelectedButtonText(flyingOrGroundGroup) == null) {
			blockedTilesSet = new HashSet<Integer>();
		} else if (GroupButtonUtil.getSelectedButtonText(flyingOrGroundGroup)
				.equals(MonsterSchema.FLYING)) {
			blockedTilesSet = flyingSet;
		} else {
			blockedTilesSet = groundSet;
		}
		myCurrentObject.addAttribute(MonsterSchema.BLOCKED_TILES,
				(Serializable) blockedTilesSet);
		// tile size
		myCurrentObject.addAttribute(MonsterSchema.TILE_SIZE,
				GroupButtonUtil.getSelectedButtonText(tileSizeGroup));

		// dropdown
		if (resDropDown.getSelectedItem() != null
				&& resDropDown.getSelectedItem().equals(NO_RES_PATH)) {
			myCurrentObject.addAttribute(resDropDown.getName(), "");
		} else {
			myCurrentObject.addAttribute(resDropDown.getName(),
					(String) resDropDown.getSelectedItem());
		}

	}

	@Override
	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
		super.updateViewWithSchemaData(map);
		for (JRadioButton button : allButtons) {
			Set blockedTilesSet = (Set) map.get(MonsterSchema.BLOCKED_TILES);
			if (button.getText().equals(MonsterSchema.FLYING)
					&& blockedTilesSet.equals(flyingSet)) {
				button.setSelected(true);
			}
			if (button.getText().equals(MonsterSchema.GROUND)
					&& blockedTilesSet.equals(groundSet)) {
				button.setSelected(true);
			}
			if (button.getText().equals(map.get(MonsterSchema.TILE_SIZE))) {

				button.setSelected(true);
			}
		}
		tileSizeGroup.getSelection();

		// res dropdown
		resDropDown.removeAllItems();
		for (String monster : objectMap.keySet()) {
			if (!monster.equals(getSelectedObjectName()))
				resDropDown.addItem(monster);
		}
		if (ComboBoxUtil.containsValue(resDropDown,
				(String) map.get(resDropDown.getName()))) {
			resDropDown.setSelectedItem(map.get(resDropDown.getName()));
		} else {
			resDropDown.addItem(NO_RES_PATH);
			resDropDown.setSelectedItem(NO_RES_PATH);
		}
	}

	/**
	 * @author garysheng Concrete subclass of ObjectTabViewBuilder that creates
	 *         specific view parts for the EnemyEditorTab
	 */
	private class EnemyTabViewBuilder extends ObjectTabViewBuilder {

		public EnemyTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}

		/**
		 * @param buttonGroup
		 * @param name
		 * @return a buttonGroup panel.
		 */
		private JComponent makeButtonGroupPanel(ButtonGroup buttonGroup,
				String name) {
			JPanel result = new JPanel(new GridLayout(0, 1));
			result.setName(name);
			Enumeration<AbstractButton> buttons = buttonGroup.getElements();
			while (buttons.hasMoreElements()) {
				result.add(buttons.nextElement());
			}
			return result;
		}

		private JPanel makeResPane() {
			JPanel result = new JPanel(new GridLayout(0, 1));
			result.add(new JLabel("Monster To Spawn"));
			result.add(resDropDown);
			resDropDown.setName(MonsterSchema.RESURRECT_MONSTER_NAME);
			result.add(new JLabel("How many"));
			result.add(resNumSpinner);
			resNumSpinner.setName(MonsterSchema.RESURRECT_QUANTITY);
			result.setName("Monsters to Spawn Upon Death");
			return result;
		}

		protected void instantiateAndClumpFields() {
			healthSpinner = makeAttributeSpinner(MonsterSchema.HEALTH);
			speedSpinner = makeAttributeSpinner(MonsterSchema.SPEED);
			damageSpinner = makeAttributeSpinner(MonsterSchema.DAMAGE);
			rewardSpinner = makeAttributeSpinner(MonsterSchema.REWARD);

			smallTileButton = new JRadioButton(MonsterSchema.TILE_SIZE_SMALL);
			mediumTileButton = new JRadioButton(MonsterSchema.TILE_SIZE_MEDIUM);
			largeTileButton = new JRadioButton(MonsterSchema.TILE_SIZE_LARGE);
			flyingButton = new JRadioButton(MonsterSchema.FLYING);
			groundButton = new JRadioButton(MonsterSchema.GROUND);

			tileSizeGroup = new ButtonGroup();
			tileSizeGroup.add(smallTileButton);
			tileSizeGroup.add(mediumTileButton);
			tileSizeGroup.add(largeTileButton);

			flyingOrGroundGroup = new ButtonGroup();
			flyingOrGroundGroup.add(flyingButton);
			flyingOrGroundGroup.add(groundButton);
			JRadioButton[] buttons = { smallTileButton, mediumTileButton,
					largeTileButton, flyingButton, groundButton };
			allButtons = new ArrayList<JRadioButton>(Arrays.asList(buttons));
			monsterImageCanvas = new ImageCanvas(true,
					TDObjectSchema.IMAGE_NAME);

			resDropDown = new JComboBox<String>();
			resNumSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));

			JSpinner[] spinners = { healthSpinner, speedSpinner, damageSpinner,
					rewardSpinner, resNumSpinner };
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));

			ImageCanvas[] canvases = { monsterImageCanvas };
			imageCanvases = new ArrayList<ImageCanvas>(Arrays.asList(canvases));

		}

		@Override
		protected JComponent makeFieldPane() {
			JPanel result = new JPanel(new GridLayout(0, 2));
			for (JSpinner spinner : spinnerFields) {
				if (spinner.getName() != null
						&& !spinner.getName().equals(
								MonsterSchema.RESURRECT_QUANTITY))
					result.add(makeFieldTile(spinner));
			}
			result.add(makeFieldTile(makeButtonGroupPanel(tileSizeGroup,
					"Tile Size")));
			result.add(makeFieldTile(makeButtonGroupPanel(flyingOrGroundGroup,
					"Flying Or Ground Type")));
			result.add(makeFieldTile(makeResPane()));
			return result;
		}

		@Override
		protected JComponent makePrimaryObjectGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());

			monsterImageCanvas.setSize(new Dimension(
					ObjectEditorConstants.IMAGE_CANVAS_SIZE,
					ObjectEditorConstants.IMAGE_CANVAS_SIZE));
			monsterImageCanvas.setBackground(Color.BLACK);
			result.add(monsterImageCanvas, BorderLayout.CENTER);
			monsterImageButton = makeChooseGraphicsButton("Set " + objectName
					+ " Image");
			result.add(monsterImageButton, BorderLayout.SOUTH);
			return result;
		}

		@Override
		protected JComponent makeSecondaryImagesGraphicPane() {
			return null;
		}

	}

}