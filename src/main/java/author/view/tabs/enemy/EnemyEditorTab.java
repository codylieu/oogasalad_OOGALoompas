package main.java.author.view.tabs.enemy;

import java.awt.Component;
import java.awt.GridLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.EnemyController;
import main.java.author.util.GroupButtonUtil;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.tdobjects.TDObjectSchema;

//SplitPaneDemo itself is not a visible component.
@SuppressWarnings("serial")
public class EnemyEditorTab extends ObjectEditorTab {

	public EnemyEditorTab(TabController towerController, String objectName) {
		super(towerController, objectName);
		// TODO Auto-generated constructor stub
	}

	private JSpinner healthSpinner, speedSpinner, damageSpinner, rewardSpinner;


	@Override
	public void saveTabData() {
		EnemyController controller = (EnemyController) myController;

	}

	public List<String> getEnemyList() {

		return (List<String>) objectMap.keySet();
	}

	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		return new SimpleMonsterSchema();
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new EnemyTabViewBuilder(this);
	}

	@Override
	protected void updateSchemaDataFromView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateViewWithSchemaData(
			Map<String, Serializable> attributesMap) {
		// TODO Auto-generated method stub

	}
	

	private class EnemyTabViewBuilder extends ObjectTabViewBuilder {

		public EnemyTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void instantiateAndClumpFields() {
			healthSpinner = makeAttributeSpinner(MonsterSchema.HEALTH);
			//healthSpinner, speedSpinner, damageSpinner, rewardSpinner;

		}

		@Override
		protected JComponent makeFieldPane() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected JComponent makeSecondaryImagesGraphicPane() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected JComponent makePrimaryObjectGraphicPane() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected String getObjectGraphicKey() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}