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

	public ItemEditorTab(TabController tabController) {
		super(tabController);
		
		tabbedPane.addTab("Annihilators", new AnnihilatorItemEditorTab(tabController, "Annihilators"));
		tabbedPane.addTab("Area Bombs", new AreaBombItemEditorTab(tabController, "Area Bombs"));
		tabbedPane.addTab("Instant Freezes", new InstantFreezeItemEditorTab(tabController, "Instant Freezes"));
		tabbedPane.addTab("Life Savers", new LifeSaverItemEditorTab(tabController, "Life Savers"));
		tabbedPane.addTab("Row Bombs", new RowBombItemEditorTab(tabController, "Row Bombs"));
		
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
