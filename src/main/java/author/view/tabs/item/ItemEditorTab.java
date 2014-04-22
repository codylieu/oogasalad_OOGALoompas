package main.java.author.view.tabs.item;

import java.awt.Component;
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
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.ItemController;
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
	private JSpinner costSpinner, damageSpinner, rangeSpinner;
	

	private List<ItemSchema> itemSchemas;
	
	private JComboBox<String> typeDropDown;
	
	//protected ImageCanvas 
	
	public ItemEditorTab(TabController itemController, String objectName) {
		super(itemController, objectName);
	}
	
//	myAttributeSet.add(ItemSchema.BUILDUP_TIME);
//	myAttributeSet.add(ItemSchema.COST);
//	myAttributeSet.add(ItemSchema.DAMAGE);
//	myAttributeSet.add(ItemSchema.FLASH_INTERVAL);
//	myAttributeSet.add(ItemSchema.IMAGE_NAME);
//	myAttributeSet.add(ItemSchema.NAME);
//	myAttributeSet.add(ItemSchema.LOCATION);

	
	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		//return new ItemSchema(name);
		return null;
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
	
	private class ItemTabViewBuilder extends ObjectTabViewBuilder {

		public ItemTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}

		@Override
		protected JComponent makePrimaryObjectGraphicPane() {
			return null;
		}

		@Override
		protected void instantiateAndClumpFields() {
			//spinners
			//radio buttons
			//dropdown
		}

		@Override
		protected JComponent makeFieldPane() {
			return null;
		}

		@Override
		protected JComponent makeSecondaryImagesGraphicPane() {
			return null;
		}}
}
