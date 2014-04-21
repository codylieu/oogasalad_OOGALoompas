package main.java.author.view;

import java.awt.Component;

import main.java.author.controller.TabController;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.schema.tdobjects.TDObjectSchema;

public class ItemEditorTab extends ObjectEditorTab {

	public ItemEditorTab(TabController itemController, String objectName) {
		super(itemController, objectName);
		// TODO Auto-generated constructor stub
	}

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
		// TODO Auto-generated method stub
		
	}

}
