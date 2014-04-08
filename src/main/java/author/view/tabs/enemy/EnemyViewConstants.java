package main.java.author.view.tabs.enemy;

import javax.swing.JSpinner;

import main.java.schema.MonsterSchema;

public class EnemyViewConstants {
	// string constants
	public static final String HEALTH_STRING = "Health";
	public static final String SPEED_STRING = "Speed";
	public static final String DAMAGE_STRING = "Damage";
	public static final String REWARD_STRING = "Bounty";
	public static final String TYPE_STRING = "Unit Type";
	public static final String TILE_SIZE_STRING = "Tile Size";

	public static final int HEALTH_DEFAULT = 20;
	public static final int SPEED_DEFAULT = 20;
	public static final int DAMAGE_DEFAULT = 20;
	public static final int REWARD_DEFAULT = 20;

	public static final int IMAGE_CANVAS_SIZE = 235;

	public static final int DIVIDER_LOCATION = 300;

	// font info
	public static final float MEDIUM_FONT_SIZE = 25f;
	public static final float LARGE_FONT_SIZE = 50f;
	public static final float X_LARGE_FONT_SIZE = 100f;
	public static final String FLYING_OR_GROUND_DEFAULT = MonsterSchema.FLYING_OR_GROUND_GROUND;
	public static final String TILE_SIZE_DEFAULT = MonsterSchema.TILE_SIZE_SMALL;
}
