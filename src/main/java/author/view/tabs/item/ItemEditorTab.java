package main.java.author.view.tabs.item;

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
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.ItemController;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.global_constants.ObjectEditorConstants;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.items.AnnihilatorItemSchema;
import main.java.schema.tdobjects.items.AreaBombItemSchema;
import main.java.schema.tdobjects.items.InstantFreezeItemSchema;
import main.java.schema.tdobjects.items.LifeSaverItemSchema;
import main.java.schema.tdobjects.items.RowBombItemSchema;

public class ItemEditorTab extends ObjectEditorTab {
	private JSpinner timeSpinner, costSpinner, damageSpinner, flashSpinner;
	private List<ItemSchema> itemSchemas;
	private List<JRadioButton> allButtons;
	private JButton itemImageButton;
	private ImageCanvas itemImageCanvas;
	
	public ItemEditorTab(TabController itemController, String objectName) {
		super(itemController, objectName);
	}
	
	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		//testing for one specific powerup
		return new AreaBombItemSchema(name);
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new ItemTabViewBuilder(this);
	}

	@Override
	public void saveTabData() {
		ItemController controller = (ItemController) myController;
		itemSchemas = new ArrayList<ItemSchema>();
		
		
		for (TDObjectSchema item : objectMap.values()) {
			ItemSchema itemSchema = null;
			if (item instanceof AnnihilatorItemSchema) {
				itemSchema = new AnnihilatorItemSchema();
			}
			if (item instanceof AreaBombItemSchema) {
				itemSchema = new AreaBombItemSchema();
			}
			if (item instanceof InstantFreezeItemSchema) {
				itemSchema = new InstantFreezeItemSchema();
			}
			if (item instanceof LifeSaverItemSchema) {
				itemSchema = new LifeSaverItemSchema();
			}
			if (item instanceof RowBombItemSchema){
				itemSchema = new RowBombItemSchema();
			}

			Map<String, Serializable> itemAttributes = item.getAttributesMap();
			
			for (String attribute : itemAttributes.keySet()) {
				itemSchema.addAttribute(attribute, itemAttributes.get(attribute));	
			}
			
			itemSchema.addAttribute(TDObjectSchema.IMAGE_NAME, "item.gif");
			itemSchemas.add(itemSchema);
		}
		controller.addItems(itemSchemas);
	}
	
	public List<ItemSchema> getItemSchemas() {
		return itemSchemas;
	}
	
	@Override
	protected void updateSchemaDataFromView() {
		super.updateSchemaDataFromView();
		
		TDObjectSchema obj = getSelectedObject();
		
	}
	
	@Override
	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
		super.updateViewWithSchemaData(map);
		//update TowerEditor upgrade dropdown
	}
	
	@Override
	protected void addListeners() {
		super.addListeners();
		itemImageButton.addActionListener(new FileChooserListener(itemImageCanvas));
		for (JRadioButton button : allButtons) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					updateSchemaDataFromView();
				}
			});
		}
	}
	
	private class ItemTabViewBuilder extends ObjectTabViewBuilder {

		public ItemTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}

		@Override
		protected JComponent makePrimaryObjectGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());

			itemImageCanvas.setSize(new Dimension(
					ObjectEditorConstants.IMAGE_CANVAS_SIZE,
					ObjectEditorConstants.IMAGE_CANVAS_SIZE));
			itemImageCanvas.setBackground(Color.BLACK);
			result.add(itemImageCanvas, BorderLayout.CENTER);
			itemImageButton = makeChooseGraphicsButton("Set " + objectName
					+ " Image");
			result.add(itemImageButton, BorderLayout.SOUTH);
			return result;
		}

		@Override
		protected JComponent makeSecondaryImagesGraphicPane() {
			return null;
		}
	
		@Override
		protected void instantiateAndClumpFields() {
			costSpinner = makeAttributeSpinner(ItemSchema.COST);
			timeSpinner = makeAttributeSpinner(ItemSchema.BUILDUP_TIME);
			damageSpinner = makeAttributeSpinner(ItemSchema.DAMAGE);
			flashSpinner = makeAttributeSpinner(ItemSchema.FLASH_INTERVAL);
			
			JSpinner[] spinners = {costSpinner, timeSpinner, damageSpinner, flashSpinner};
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
			itemImageCanvas = new ImageCanvas(true, ItemSchema.IMAGE_NAME);
		}

		@Override
		protected JComponent makeFieldPane() {
			JPanel result = new JPanel(new GridLayout(0, 2));
			for (JSpinner spinner : spinnerFields) {
				result.add(makeFieldTile(spinner));
			}
			return result;
		}
	}
}
