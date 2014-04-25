package main.java.author.view.tabs.item;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.item.AbstractItemEditorTab.AbstractItemTabViewBuilder;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.items.RowBombItemSchema;

public class RowBombItemEditorTab extends AbstractItemEditorTab{

	public RowBombItemEditorTab(TabController itemController, String objectName) {
		super(itemController, objectName);
	}

	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		return new RowBombItemSchema(name);
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new RowBombItemTabViewBuilder(this);
	}
	
	private class RowBombItemTabViewBuilder extends AbstractItemTabViewBuilder {

		public RowBombItemTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}

		@Override
		protected void instantiateAndClumpFields() {
			costSpinner = makeAttributeSpinner(ItemSchema.COST);
			timeSpinner = makeAttributeSpinner(ItemSchema.BUILDUP_TIME);
			damageSpinner = makeAttributeSpinner(ItemSchema.DAMAGE);
			flashSpinner = makeAttributeSpinner(ItemSchema.FLASH_INTERVAL);
			rangeSpinner = makeAttributeSpinner(RowBombItemSchema.RANGE);
			
			JSpinner[] spinners = {costSpinner, timeSpinner, damageSpinner, flashSpinner, rangeSpinner};
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
			itemImageCanvas = new ImageCanvas(true, ItemSchema.IMAGE_NAME);
		}
	}

}
