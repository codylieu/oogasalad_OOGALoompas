package main.java.schema;

import java.util.HashSet;
import java.util.Set;

import main.java.author.view.tabs.tower.TowerViewConstants;
import main.java.engine.objects.tower.SimpleTower;

/**
 * 
 * This is a settings object for a specific type of Tower like a Fire Tower, and
 * at a high level is a wrapper for a bunch of key value pairs that the Engine
 * will need to reference to create TDObjects. This class is not a specific
 * instance of a Tower. Please refer to the Game Engine's TDObjects for the
 * objects related to ones you will see onscreen.
 */
public class SimpleTowerSchema extends TowerSchema {
	private static final Class<SimpleTower> MY_CONCRETE_TYPE = SimpleTower.class;

	public SimpleTowerSchema() {
		super(MY_CONCRETE_TYPE);
	}

	public SimpleTowerSchema(String name) {
		this();

		populateDefaultAttributes(name);

	}

	public void populateDefaultAttributes(String name) {

		// this is a hack
		addAttribute(TowerSchema.NAME, name);
		addAttribute(TowerSchema.HEALTH, TowerViewConstants.HEALTH_DEFAULT);
		addAttribute(TowerSchema.COST, TowerViewConstants.COST_DEFAULT);
		addAttribute(TowerSchema.DAMAGE, TowerViewConstants.DAMAGE_DEFAULT);
		addAttribute(TowerSchema.BUILDUP, TowerViewConstants.BUILDUP_DEFAULT);
		addAttribute(TowerSchema.TILE_SIZE,
				TowerViewConstants.TILE_SIZE_DEFAULT);
		addAttribute(TowerSchema.RANGE,
				TowerViewConstants.RANGE_DEFAULT);
		addAttribute(TowerSchema.TOWER_IMAGE_NAME, "");
		addAttribute(TowerSchema.BULLET_IMAGE_NAME, "");

	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		// empty set, no new attributes
		return new HashSet<String>();
	}

	@Override
	public String defineName() {
		return "SimpleTower";
	}

}
