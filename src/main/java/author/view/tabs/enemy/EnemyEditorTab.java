package main.java.author.view.tabs.enemy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.EnemyController;
import main.java.author.util.GroupButtonUtil;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.global_constants.ObjectEditorConstants;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.tdobjects.TDObjectSchema;

/**
 * @author garysheng The tab that corresponds to the creation of enemies. Talks
 *         to a tab controller to pass information to the model
 */
public class EnemyEditorTab extends ObjectEditorTab {

	public EnemyEditorTab(TabController towerController, String objectName) {
		super(towerController, objectName);
	}

	private JSpinner healthSpinner, speedSpinner, damageSpinner, rewardSpinner;
	private ImageCanvas monsterImageCanvas, collisionImageCanvas;
	private JButton monsterImageButton, collisionImageButton;
	private JRadioButton smallTileButton, mediumTileButton, largeTileButton,
			flyingButton, groundButton;
	private List<JRadioButton> allButtons;
	private ButtonGroup tileSizeGroup, flyingOrGroundGroup;
	private List<MonsterSchema> monsterSchemas;

	@Override
	public void saveTabData() {
		EnemyController controller = (EnemyController) myController;

		monsterSchemas = new ArrayList<MonsterSchema>();
		for (TDObjectSchema monster : objectMap.values()) {
			SimpleMonsterSchema monsterSchema = new SimpleMonsterSchema();
			Map<String, Object> monsterAttributes = monster
					.getAttributesMap();

			for (String attribute : monsterAttributes.keySet()) {
//				Serializable castedAttribute = addCastToAttribute(monsterAttributes
//						.get(attribute));
//				monsterSchema.addAttribute(attribute, castedAttribute);
				monsterSchema.addAttribute(attribute, monsterAttributes
						.get(attribute));
			}
			monsterSchemas.add(monsterSchema);
		}
		controller.addEnemies(monsterSchemas);
	}

	/**
	 * @return the list of monster schemas created so far in this tab
	 */
	public List<MonsterSchema> getMonsterSchemas() {
		return monsterSchemas;
	}

	/**
	 * @return the list of
	 */
	public String[] getEnemyNamesArray() {
		int size = objectMap.keySet().size();
		return objectMap.keySet().toArray(new String[size]);
	}

	@Override
	protected void updateSchemaDataFromView() {
		super.updateSchemaDataFromView();
		TDObjectSchema myCurrentObject = getSelectedObject();
		myCurrentObject.addAttribute(MonsterSchema.FLYING_OR_GROUND,
				GroupButtonUtil.getSelectedButtonText(flyingOrGroundGroup));
		myCurrentObject.addAttribute(MonsterSchema.TILE_SIZE,
				GroupButtonUtil.getSelectedButtonText(tileSizeGroup));

	}

	@Override
	protected void updateViewWithSchemaData(Map<String, Object> map) {
		super.updateViewWithSchemaData(map);
		for (JRadioButton button : allButtons) {
			if (button.getText()
					.equals(map.get(MonsterSchema.FLYING_OR_GROUND))
					|| button.getText()
							.equals(map.get(MonsterSchema.TILE_SIZE))) {
				;
				button.setSelected(true);
			}
		}
		tileSizeGroup.getSelection();
	}

	@Override
	protected void addListeners() {
		super.addListeners();
		monsterImageButton.addActionListener(new FileChooserListener(
				monsterImageCanvas));
		/*collisionImageButton.addActionListener(new FileChooserListener(
				collisionImageCanvas));*/
		for (JRadioButton button : allButtons) {
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					updateSchemaDataFromView();
				}
			});
		}
	}

	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		return new SimpleMonsterSchema(name);
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new EnemyTabViewBuilder(this);
	}

	/**
	 * @author garysheng Concrete subclass of ObjectTabViewBuilder that creates
	 *         specific view parts for the EnemyEditorTab
	 */
	private class EnemyTabViewBuilder extends ObjectTabViewBuilder {

		public EnemyTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
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
		/*	collisionImageCanvas = new ImageCanvas(true,
					MonsterSchema.COLLISION_IMAGE_NAME);*/

			JSpinner[] spinners = { healthSpinner, speedSpinner, damageSpinner,
					rewardSpinner };
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));

			ImageCanvas[] canvases = { monsterImageCanvas };
			imageCanvases = new ArrayList<ImageCanvas>(Arrays.asList(canvases));

		}

		@Override
		protected JComponent makeFieldPane() {
			JPanel result = new JPanel(new GridLayout(0, 2));
			for (JSpinner spinner : spinnerFields) {
				result.add(makeFieldTile(spinner));
			}
			result.add(makeFieldTile(makeButtonGroupPanel(tileSizeGroup,
					"Tile Size")));
			result.add(makeFieldTile(makeButtonGroupPanel(flyingOrGroundGroup,
					"Flying Or Ground Type")));

			return result;
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

		@Override
		protected JComponent makeSecondaryImagesGraphicPane() {
			/*JPanel result = new JPanel();
			result.setLayout(new BorderLayout());

			collisionImageCanvas.setSize(new Dimension(
					ObjectEditorConstants.IMAGE_CANVAS_SIZE,
					ObjectEditorConstants.IMAGE_CANVAS_SIZE));
			collisionImageCanvas.setBackground(Color.BLACK);
			result.add(collisionImageCanvas, BorderLayout.CENTER);
			collisionImageButton = makeChooseGraphicsButton("Set " + objectName
					+ " Collision Image");
			result.add(collisionImageButton, BorderLayout.SOUTH);
			//return result;
*/			return null;
		}


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

	}

}