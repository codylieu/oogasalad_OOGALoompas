package main.java.author.view.tabs.item;

import java.awt.BorderLayout;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;
import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.ItemController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.item.subtabs.AnnihilatorItemEditorTab;
import main.java.author.view.tabs.item.subtabs.AreaBombItemEditorTab;
import main.java.author.view.tabs.item.subtabs.InstantFreezeItemEditorTab;
import main.java.author.view.tabs.item.subtabs.LifeSaverItemEditorTab;
import main.java.author.view.tabs.item.subtabs.RowBombItemEditorTab;
import main.java.schema.tdobjects.ItemSchema;

/**
 * @author dennis park, garysheng Overarching item editor for the X different
 *         types of items you can build. Must create some sort of
 *         ItemSubTabEditor if you want to add a new type of item
 */
public class ItemEditorTab extends EditorTab {
	private JTabbedPane tabbedPane = new JTabbedPane();
	private AbstractItemEditorSubTab annihilatorTab, areabombTab, instantTab,
			lifesaverTab, rowbombTab;

	public ItemEditorTab(TabController tabController) {
		super(tabController);

		annihilatorTab = new AnnihilatorItemEditorTab(tabController,
				ItemViewConstants.ANNIHILATOR);
		areabombTab = new AreaBombItemEditorTab(tabController,
				ItemViewConstants.AREA_BOMB);
		instantTab = new InstantFreezeItemEditorTab(tabController,
				ItemViewConstants.INSTANT_FREEZE);
		lifesaverTab = new LifeSaverItemEditorTab(tabController,
				ItemViewConstants.LIFE_SAVER);
		rowbombTab = new RowBombItemEditorTab(tabController,
				ItemViewConstants.ROW_BOMB);

		tabbedPane.addTab(ItemViewConstants.ANNIHILATOR, annihilatorTab);
		tabbedPane.addTab(ItemViewConstants.AREA_BOMB, areabombTab);
		tabbedPane.addTab(ItemViewConstants.INSTANT_FREEZE, instantTab);
		tabbedPane.addTab(ItemViewConstants.LIFE_SAVER, lifesaverTab);
		tabbedPane.addTab(ItemViewConstants.ROW_BOMB, rowbombTab);

		annihilatorTab.setPreferredSize(ItemViewConstants.DIM);

		add(tabbedPane, BorderLayout.CENTER);
		setVisible(true);
	}

	@Override
	public void saveTabData() {
		List<ItemSchema> allItemsFromAllTabs = new ArrayList<ItemSchema>();
		for (Component tab : tabbedPane.getComponents()) {
			AbstractItemEditorSubTab subTab = (AbstractItemEditorSubTab) tab;
			allItemsFromAllTabs.addAll(subTab.getItemSchemas());
		}
		ItemController tabController = (ItemController) myController;
		tabController.addItems(allItemsFromAllTabs);
	}

}
