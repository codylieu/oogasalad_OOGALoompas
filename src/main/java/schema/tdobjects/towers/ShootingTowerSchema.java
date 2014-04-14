package main.java.schema.tdobjects.towers;

import java.util.HashSet;
import java.util.Set;

import main.java.author.view.tabs.tower.TowerViewConstants;
import main.java.engine.objects.tower.ShootingTower;
import main.java.schema.tdobjects.TowerSchema;

/**
 * 
 * This is a settings object for a specific type of Tower like a Fire Tower, and
 * at a high level is a wrapper for a bunch of key value pairs that the Engine
 * will need to reference to create TDObjects. This class is not a specific
 * instance of a Tower. Please refer to the Game Engine's TDObjects for the
 * objects related to ones you will see onscreen.
 */
public class ShootingTowerSchema extends TowerSchema {
	private static final Class<ShootingTower> MY_CONCRETE_TYPE = ShootingTower.class;
    public static final String DAMAGE = "Damage";

	public ShootingTowerSchema() {
		super(MY_CONCRETE_TYPE);
	}

	public ShootingTowerSchema(String name) {
		this();
		populateDefaultAttributes(name);
	}

	public void populateDefaultAttributes(String name) {
		addAttribute(TowerSchema.NAME, name);
		addAttribute(TowerSchema.HEALTH, TowerViewConstants.HEALTH_DEFAULT);
		addAttribute(TowerSchema.COST, TowerViewConstants.COST_DEFAULT);
		addAttribute(ShootingTowerSchema.DAMAGE, TowerViewConstants.DAMAGE_DEFAULT);
		addAttribute(TowerSchema.BUILDUP, TowerViewConstants.BUILDUP_DEFAULT);
		addAttribute(TowerSchema.TILE_SIZE, TowerViewConstants.TILE_SIZE_DEFAULT);
		addAttribute(TowerSchema.RANGE, TowerViewConstants.RANGE_DEFAULT);
		addAttribute(TowerSchema.TOWER_IMAGE_NAME, "");
		addAttribute(TowerSchema.BULLET_IMAGE_NAME, "");
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		myAttributeSet.add(ShootingTowerSchema.DAMAGE);
		return myAttributeSet;
	}
}
