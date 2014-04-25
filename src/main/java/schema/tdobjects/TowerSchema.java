package main.java.schema.tdobjects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import main.java.author.view.tabs.tower.TowerViewConstants;
import main.java.engine.objects.tower.SimpleTower;
import main.java.engine.objects.tower.TowerBehaviors;


public class TowerSchema extends TDObjectSchema {
    public static final String LOCATION = "Location";
    public static final String HEALTH = "Health";
    public static final String DAMAGE = "Damage";
    public static final String SHRAPNEL_DAMAGE = "Bomb Shrapnel Damage";
    public static final String FIRING_SPEED = "Firing Speed (per sec)";
    public static final String RANGE = "Range";
    public static final String COST = "Cost";
    public static final String MONEY_GRANTED = "Money Granted Per Interval";
  
    /**
     * Money will be granted at every N frames, where N = MONEY_GRANT_INTERVAL
     */
    public static final String MONEY_GRANT_INTERVAL = "Money Grant Interval";

    /**
     * A percentage, e.g. 20 would decrease monster's speed to 20% of it's previous speed at each
     * hit
     */
    public static final String FREEZE_SLOWDOWN_PROPORTION = "Freeze Slowdown (%)";
    public static final String TILE_SIZE = "Size";
    public static final String BUILDUP = "Build Up Time (sec)";
    public static final String BULLET_IMAGE_NAME = "Bullet Image Name";
    public static final String SHRAPNEL_IMAGE_NAME = "Bomb's Shrapnel Image Name";

    public static final String TOWER_BEHAVIORS = "Tower Behaviors";

    /**
     * This is the name of a tower to be upgraded into.
     */
    public static final String UPGRADE_PATH = "Upgrade Tower";

    /**
     * Do not use this, UPGRADE_PATH is enough. Model will know there is no
     * upgrade if UPGRADE_PATH is not put into the map, or if its value is the
     * empty string.
     */
    public TowerSchema () {
        super(SimpleTower.class);
        // things author doesnt touch
        myAttributeSet.add(TILE_SIZE);
        myAttributeSet.add(LOCATION);
        // things author handles
        myAttributeSet.add(BUILDUP);
        myAttributeSet.add(BULLET_IMAGE_NAME);
        myAttributeSet.add(COST);
        myAttributeSet.add(DAMAGE);
        myAttributeSet.add(FIRING_SPEED);
        myAttributeSet.add(HEALTH);
        myAttributeSet.add(RANGE);
        myAttributeSet.add(NAME);
        myAttributeSet.add(TOWER_BEHAVIORS);
        myAttributeSet.add(MONEY_GRANTED);
        myAttributeSet.add(MONEY_GRANT_INTERVAL);
        myAttributeSet.add(SHRAPNEL_DAMAGE);
        myAttributeSet.add(SHRAPNEL_IMAGE_NAME);
        myAttributeSet.add(FREEZE_SLOWDOWN_PROPORTION);
        myAttributeSet.add(UPGRADE_PATH);
    }

    public TowerSchema (String name) {
        this();
        populateDefaultAttributes(name);
    }

    @Override
    public void populateDefaultAttributes (String name) {
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
        addAttribute(TDObjectSchema.IMAGE_NAME,
                     TowerViewConstants.TOWER_DEFAULT_IMAGE);
        addAttribute(TowerSchema.SHRAPNEL_IMAGE_NAME,
                     TowerViewConstants.SHRAPNEL_DEFAULT_IMAGE);
        addAttribute(TowerSchema.BULLET_IMAGE_NAME,
                     TowerViewConstants.BULLET_DEFAULT_IMAGE);
        addAttribute(TowerSchema.UPGRADE_PATH, "");
        addAttribute(TowerSchema.TOWER_BEHAVIORS,
                     (Serializable) Arrays.asList(TowerBehaviors.values()));
    }

    @Override
    protected Set<String> populateAdditionalAttributes () {
        return new HashSet<>(); // No additional attributes
    }
}
