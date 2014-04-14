package main.java.schema.tdobjects;

import java.util.HashSet;
import java.util.Set;
import main.java.author.view.tabs.tower.TowerViewConstants;
import main.java.engine.objects.tower.SimpleTower;


public class TowerSchema extends TDObjectSchema {
    public static final String LOCATION = "Location";
    public static final String HEALTH = "Health";
    public static final String DAMAGE = "Damage";
    public static final String FIRING_SPEED = "Firing Speed";
    public static final String RANGE = "Range";
    public static final String COST = "Cost";
    public static final String TILE_SIZE = "Size";
    public static final String BUILDUP = "BuildUp";
    public static final String TOWER_IMAGE_NAME = "Tower Image Name";
    public static final String BULLET_IMAGE_NAME = "Bullet Image Name";
    public static final String TOWER_BEHAVIORS = "Tower Behaviors USE CHECKBOXES?";

    public static final String TILE_SIZE_SMALL = "Small Tile";
    public static final String TILE_SIZE_LARGE = "Large Tile";
    public static final String RANGE_SMALL = "Small";
    public static final String RANGE_MEDIUM = "Medium";
    public static final String RANGE_LARGE = "Large";

    public TowerSchema () {
        super(SimpleTower.class);
        myAttributeSet.add(BUILDUP);
        myAttributeSet.add(TILE_SIZE);
        myAttributeSet.add(TOWER_IMAGE_NAME);
        myAttributeSet.add(BULLET_IMAGE_NAME);
        myAttributeSet.add(COST);
        myAttributeSet.add(DAMAGE);
        myAttributeSet.add(FIRING_SPEED);
        myAttributeSet.add(HEALTH);
        myAttributeSet.add(RANGE);
        myAttributeSet.add(LOCATION);
        myAttributeSet.add(NAME);
        myAttributeSet.add(TOWER_BEHAVIORS);
    }

    public void populateDefaultAttributes (String name) {
        addAttribute(TowerSchema.NAME, name);
        addAttribute(TowerSchema.HEALTH, TowerViewConstants.HEALTH_DEFAULT);
        addAttribute(TowerSchema.COST, TowerViewConstants.COST_DEFAULT);
        addAttribute(TowerSchema.DAMAGE, TowerViewConstants.DAMAGE_DEFAULT);
        addAttribute(TowerSchema.BUILDUP, TowerViewConstants.BUILDUP_DEFAULT);
        addAttribute(TowerSchema.TILE_SIZE, TowerViewConstants.TILE_SIZE_DEFAULT);
        addAttribute(TowerSchema.RANGE, TowerViewConstants.RANGE_DEFAULT);
        addAttribute(TowerSchema.TOWER_IMAGE_NAME, "");
        addAttribute(TowerSchema.BULLET_IMAGE_NAME, "");
    }

    @Override
    protected Set<String> populateAdditionalAttributes () {
        return new HashSet<>(); // No additional attributes
    }
}
