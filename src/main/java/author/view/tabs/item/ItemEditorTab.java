package main.java.author.view.tabs.item;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.EnemyController;
import main.java.author.controller.tabbed_controllers.GameSettingsController;
import main.java.author.controller.tabbed_controllers.ItemController;
import main.java.author.controller.tabbed_controllers.TerrainController;
import main.java.author.controller.tabbed_controllers.TowerController;
import main.java.author.controller.tabbed_controllers.WaveController;
import main.java.author.view.menubar.BasicMenuBar;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.GameSettingsEditorTab;
import main.java.author.view.tabs.enemy.EnemyEditorTab;
import main.java.author.view.tabs.item.subtabs.AnnihilatorItemEditorTab;
import main.java.author.view.tabs.item.subtabs.AreaBombItemEditorTab;
import main.java.author.view.tabs.item.subtabs.InstantFreezeItemEditorTab;
import main.java.author.view.tabs.item.subtabs.LifeSaverItemEditorTab;
import main.java.author.view.tabs.item.subtabs.RowBombItemEditorTab;
import main.java.author.view.tabs.terrain.TerrainEditorTab;
import main.java.author.view.tabs.tower.TowerEditorTab;
import main.java.author.view.tabs.wave_editor.WaveEditorTab;
import main.java.exceptions.data.InvalidGameBlueprintException;
import main.java.schema.tdobjects.ItemSchema;

public class ItemEditorTab extends EditorTab {
	private JTabbedPane tabbedPane = new JTabbedPane();
	private AbstractItemEditorSubTab annihilatorTab, areabombTab, instantTab, lifesaverTab, rowbombTab;
	
	public ItemEditorTab(TabController tabController) {
		super(tabController);
		
		annihilatorTab	= new AnnihilatorItemEditorTab(tabController, ItemViewConstants.ANNIHILATOR);
		areabombTab		= new AreaBombItemEditorTab(tabController, ItemViewConstants.AREA_BOMB);
		instantTab		= new InstantFreezeItemEditorTab(tabController, ItemViewConstants.INSTANT_FREEZE);
		lifesaverTab	= new LifeSaverItemEditorTab(tabController, ItemViewConstants.LIFE_SAVER);
		rowbombTab		= new RowBombItemEditorTab(tabController, ItemViewConstants.ROW_BOMB);
		
		tabbedPane.addTab(ItemViewConstants.ANNIHILATOR, annihilatorTab);
		tabbedPane.addTab(ItemViewConstants.AREA_BOMB, areabombTab);
		tabbedPane.addTab(ItemViewConstants.INSTANT_FREEZE, instantTab);
		tabbedPane.addTab(ItemViewConstants.LIFE_SAVER, lifesaverTab);
		tabbedPane.addTab(ItemViewConstants.ROW_BOMB, rowbombTab);
		
		annihilatorTab.setPreferredSize(new Dimension(1000,500));
		System.out.println(this.getSize() + "\t" + super.getSize());
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
