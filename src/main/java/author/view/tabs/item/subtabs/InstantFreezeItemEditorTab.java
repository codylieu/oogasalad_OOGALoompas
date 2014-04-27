package main.java.author.view.tabs.item.subtabs;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.item.AbstractItemEditorSubTab;
import main.java.author.view.tabs.item.AbstractItemEditorSubTab.AbstractItemTabViewBuilder;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.items.AreaBombItemSchema;
import main.java.schema.tdobjects.items.InstantFreezeItemSchema;

public class InstantFreezeItemEditorTab extends AbstractItemEditorSubTab{

	public InstantFreezeItemEditorTab(TabController itemController,
			String objectName) {
		super(itemController, objectName);
	}

	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		return new InstantFreezeItemSchema(name);
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new InstantFreezeItemTabViewBuilder(this);
	}

	private class InstantFreezeItemTabViewBuilder extends AbstractItemTabViewBuilder {

		public InstantFreezeItemTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}

		@Override
		protected void instantiateAndClumpFields() {
			super.instantiateAndClumpFields();
			costSpinner = makeAttributeSpinner(ItemSchema.COST);
			damageSpinner = makeAttributeSpinner(ItemSchema.DAMAGE);
			flashSpinner = makeAttributeSpinner(ItemSchema.FLASH_INTERVAL);
			freezeSpinner = makeAttributeSpinner(InstantFreezeItemSchema.FREEZE_DURATION);
			
			JSpinner[] spinners = {costSpinner, damageSpinner, flashSpinner, freezeSpinner};
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
			itemImageCanvas = new ImageCanvas(true, ItemSchema.IMAGE_NAME);
		}
	}
}
