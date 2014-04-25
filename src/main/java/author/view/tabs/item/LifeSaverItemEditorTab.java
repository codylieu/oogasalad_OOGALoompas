package main.java.author.view.tabs.item;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.items.LifeSaverItemSchema;

public class LifeSaverItemEditorTab extends AbstractItemEditorTab{

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

	private class LifeSaverItemTabViewBuilder extends AbstractItemTabViewBuilder {

		public LifeSaverItemTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}

		@Override
		protected void instantiateAndClumpFields() {
			costSpinner = makeAttributeSpinner(ItemSchema.COST);
			timeSpinner = makeAttributeSpinner(ItemSchema.BUILDUP_TIME);
			damageSpinner = makeAttributeSpinner(ItemSchema.DAMAGE);
			flashSpinner = makeAttributeSpinner(ItemSchema.FLASH_INTERVAL);
			
			
			JSpinner[] spinners = {costSpinner, timeSpinner, damageSpinner, flashSpinner, freezeSpinner};
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
			itemImageCanvas = new ImageCanvas(true, ItemSchema.IMAGE_NAME);
		}
	}
}
