package main.java.author.view.tabs.tower;

import java.io.Serializable;

/**
 * @author garysheng Some default constants for the TowerEditorTab
 * 
 */
public class TowerViewConstants {

	public static final int HEALTH_DEFAULT = 100;
	public static final int COST_DEFAULT = 20;
	public static final int DAMAGE_DEFAULT = 10;
	public static final int BUILDUP_DEFAULT = 5;
	public static final int RANGE_DEFAULT = 100;
	public static final int MONEY_GRANTED_DEFAULT = 100;
	/**
	 * A monster's speed becomes this percentage of it's previous speed once hit with freeze projectile.
	 * Valid values are [0-100]
	 */
	public static final int FREEZE_SLOWDOWN_DEFAULT = 80;
	public static final Serializable FIRING_SPEED_DEFAULT = 3;
	public static final Serializable MONEY_GRANT_INTERVAL_DEFAULT = 100;
	public static final String TOWER_BEHAVIOR_FREEZES = "Deals Frost Damage";
	public static final String TOWER_BEHAVIOR_SHOOTS = "Can Shoot";
	public static final String TOWER_BEHAVIOR_FARMS_MONEY = "Farms Money";
	public static final String TOWER_BEHAVIOR_BOMBS = "Creates Shrapnel";
	public static final String TOWER_DEFAULT_IMAGE = "tower.gif";
	public static final String BULLET_DEFAULT_IMAGE = "red_bullet.png";
	public static final String SHRAPNEL_DEFAULT_IMAGE = "blue_bullet.png";

}
