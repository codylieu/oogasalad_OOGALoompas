package main.java.schema.tdobjects;

import java.util.HashSet;
import java.util.Set;

import main.java.author.view.tabs.tower.TowerViewConstants;
import main.java.engine.objects.tower.SimpleTower;

public class TowerSchema extends TDObjectSchema {
	public static final String LOCATION = "Location";
	public static final String HEALTH = "Health";
	public static final String DAMAGE = "Damage";
	public static final String SHRAPNEL_DAMAGE = "Bomb Shrapnel Damage";
	public static final String FIRING_SPEED = "Firing Speed (per sec)";
	public static final String RANGE = "Range";
	public static final String COST = "Cost";
	public static final String MONEY_GRANTED = "Money Granted Per Interval";
	public static final String MONEY_GRANT_INTERVAL = "Money Grant Interval (sec)";
	public static final String FREEZE_SLOWDOWN_PROPORTION = "Freeze Slowdown (%)";
	public static final String TILE_SIZE = "Size";
	public static final String BUILDUP = "Build Up Time (sec)";
	public static final String TOWER_IMAGE_NAME = "Tower Image Name";
	public static final String BULLET_IMAGE_NAME = "Bullet Image Name";
	public static final String SHRAPNEL_IMAGE_NAME = "Bomb's Shrapnel Image Name";

	public static final String TOWER_BEHAVIORS = "Tower Behaviors";
	public static final String TOWER_BEHAVIOR_FREEZES = "Deals Frost Damage";
	public static final String TOWER_BEHAVIOR_SHOOTS = "Can Shoot";
	public static final String TOWER_BEHAVIOR_FARMS_MONEY = "Farms Money";
	public static final String TOWER_BEHAVIOR_BOMBS = "Creates Shrapnel";

	public static final String UPGRADE_PATH = "Upgrade Tower";
	public static final String UPGRADE_PATH_NONE = "No Upgrade";


	public TowerSchema() {
		super(SimpleTower.class);
		//things author doesnt touch
		myAttributeSet.add(TILE_SIZE);
		myAttributeSet.add(LOCATION);
		//things author handles
		myAttributeSet.add(BUILDUP);
		myAttributeSet.add(TOWER_IMAGE_NAME);
		myAttributeSet.add(BULLET_IMAGE_NAME);
		myAttributeSet.add(COST);
		myAttributeSet.add(DAMAGE);
		myAttributeSet.add(FIRING_SPEED);
		myAttributeSet.add(HEALTH);
		myAttributeSet.add(RANGE);
		myAttributeSet.add(NAME);
		myAttributeSet.add(TOWER_BEHAVIORS);
		myAttributeSet.add(TOWER_BEHAVIOR_BOMBS);
		myAttributeSet.add(TOWER_BEHAVIOR_FARMS_MONEY);
		myAttributeSet.add(TOWER_BEHAVIOR_FREEZES);
		myAttributeSet.add(TOWER_BEHAVIOR_SHOOTS);
		myAttributeSet.add(MONEY_GRANTED);
		myAttributeSet.add(MONEY_GRANT_INTERVAL);
		myAttributeSet.add(SHRAPNEL_DAMAGE);
		myAttributeSet.add(SHRAPNEL_IMAGE_NAME);
		myAttributeSet.add(FREEZE_SLOWDOWN_PROPORTION);
		myAttributeSet.add(UPGRADE_PATH);
	}

	public TowerSchema(String name) {
		this();
		populateDefaultAttributes(name);
	}

	public void populateDefaultAttributes(String name) {
		addAttribute(TowerSchema.NAME, name);
		addAttribute(TowerSchema.HEALTH, TowerViewConstants.HEALTH_DEFAULT);
		addAttribute(TowerSchema.COST, TowerViewConstants.COST_DEFAULT);
		addAttribute(TowerSchema.DAMAGE, TowerViewConstants.DAMAGE_DEFAULT);
		addAttribute(TowerSchema.RANGE, TowerViewConstants.RANGE_DEFAULT);
		addAttribute(TowerSchema.BUILDUP, TowerViewConstants.BUILDUP_DEFAULT);
		addAttribute(TowerSchema.FIRING_SPEED,
				TowerViewConstants.FIRING_SPEED_DEFAULT);
		addAttribute(TowerSchema.MONEY_GRANTED,
				TowerViewConstants.MONEY_GRANTED_DEFAULT);
		addAttribute(TowerSchema.MONEY_GRANT_INTERVAL,
				TowerViewConstants.MONEY_GRANT_INTERVAL_DEFAULT);
		addAttribute(TowerSchema.FREEZE_SLOWDOWN_PROPORTION,
				TowerViewConstants.FREEZE_SLOWDOWN_DEFAULT);
		addAttribute(TowerSchema.SHRAPNEL_DAMAGE,
				TowerViewConstants.BUILDUP_DEFAULT);
		addAttribute(TowerSchema.TOWER_IMAGE_NAME, "");
		addAttribute(TowerSchema.SHRAPNEL_IMAGE_NAME, "");
		addAttribute(TowerSchema.BULLET_IMAGE_NAME, "");
		addAttribute(TowerSchema.UPGRADE_PATH, "");
		addAttribute(TowerSchema.TOWER_BEHAVIOR_BOMBS, true);
		addAttribute(TowerSchema.TOWER_BEHAVIOR_FARMS_MONEY, true);
		addAttribute(TowerSchema.TOWER_BEHAVIOR_FREEZES, true);
		addAttribute(TowerSchema.TOWER_BEHAVIOR_SHOOTS, true);
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		return new HashSet<>(); // No additional attributes
	}
}
