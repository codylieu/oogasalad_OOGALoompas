package main.java.author.view.tabs.item.subtabs;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.item.AbstractItemEditorSubTab;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.items.LifeSaverItemSchema;

public class LifeSaverItemEditorTab extends AbstractItemEditorSubTab {

	public LifeSaverItemEditorTab(TabController itemController,
			String objectName) {
		super(itemController, objectName);
	}

	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		return new LifeSaverItemSchema(name);
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new LifeSaverItemTabViewBuilder(this);
	}

	private class LifeSaverItemTabViewBuilder extends
			AbstractItemTabViewBuilder {

		public LifeSaverItemTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}

		@Override
		protected void instantiateAndClumpFields() {
			super.instantiateAndClumpFields();
			costSpinner = makeAttributeSpinner(ItemSchema.COST);
			buildUpSpinner = makeAttributeSpinner(ItemSchema.BUILDUP_TIME);
			flashSpinner = makeAttributeSpinner(ItemSchema.FLASH_INTERVAL);

			JSpinner[] spinners = { costSpinner, buildUpSpinner,
					flashSpinner };
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
			
		}
	}
}
