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

import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.ItemController;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.items.AnnihilatorItemSchema;

public class ItemEditorTab extends ObjectEditorTab {
	private JSpinner costSpinner, damageSpinner;

	private List<ItemSchema> itemSchemas;
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTabData() {
		ItemController controller = (ItemController) myController;
		itemSchemas = new ArrayList<ItemSchema>();
		
		
		for (TDObjectSchema item : objectMap.values()) {
			ItemSchema itemSchema = new AnnihilatorItemSchema();
			Map<String, Serializable> itemAttributes = item.getAttributesMap();
			
			for (String attribute : itemAttributes.keySet()) {
				itemSchema.addAttribute(attribute, itemAttributes.get(attribute));	
			}
		}
		
	}

}
