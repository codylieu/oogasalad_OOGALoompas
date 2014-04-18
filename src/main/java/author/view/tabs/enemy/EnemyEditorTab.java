package main.java.author.view.tabs.enemy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultButtonModel;
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

	@Override
	public void saveTabData() {
		EnemyController controller = (EnemyController) myController;

	}

	public List<String> getEnemyList() {

		return (List<String>) objectMap.keySet();
	}

	protected void updateSchemaDataFromView() {
		super.updateSchemaDataFromView();
		TDObjectSchema myCurrentObject = getSelectedObject();
		myCurrentObject.addAttribute(MonsterSchema.FLYING_OR_GROUND,
				GroupButtonUtil.getSelectedButtonText(flyingOrGroundGroup));
		myCurrentObject.addAttribute(MonsterSchema.TILE_SIZE,
				GroupButtonUtil.getSelectedButtonText(tileSizeGroup));

	}

	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
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
		collisionImageButton.addActionListener(new FileChooserListener(
				collisionImageCanvas));
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

	private class EnemyTabViewBuilder extends ObjectTabViewBuilder {

		public EnemyTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
			// TODO Auto-generated constructor stub
		}

		@Override
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
					MonsterSchema.ENEMY_IMAGE_NAME);
			collisionImageCanvas = new ImageCanvas(true,
					MonsterSchema.COLLISION_IMAGE_NAME);

			JSpinner[] spinners = { healthSpinner, speedSpinner, damageSpinner,
					rewardSpinner };
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));

			ImageCanvas[] canvases = { monsterImageCanvas, collisionImageCanvas };
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
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());

			collisionImageCanvas.setSize(new Dimension(
					ObjectEditorConstants.IMAGE_CANVAS_SIZE,
					ObjectEditorConstants.IMAGE_CANVAS_SIZE));
			collisionImageCanvas.setBackground(Color.BLACK);
			result.add(collisionImageCanvas, BorderLayout.CENTER);
			collisionImageButton = makeChooseGraphicsButton("Set " + objectName
					+ " Collision Image");
			result.add(collisionImageButton, BorderLayout.SOUTH);
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

	}

}