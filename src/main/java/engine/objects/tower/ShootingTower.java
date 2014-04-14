package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.Projectile;
import main.java.engine.objects.TDObject;
import main.java.schema.tdobjects.TowerSchema;


/**
 * Towers decoration make the base tower fire a projectile at nearest enemies
 * 
 * @author Austin
 *
 */
public class ShootingTower extends TowerBehaviorDecorator {

    public static final double DEFAULT_DAMAGE = 10;
    public static final double DEFAULT_RANGE = 200;
    public static final double DEFAULT_FIRING_SPEED = 5;

    double myDamage;
    double myFiringSpeed;
    double myRange;

    /**
     * Create a new tower by adding shooting behavior to an existing tower
     * 
     * @param baseTower tower to be expanded with shooting behavior
     * @param damage
     * @param firingSpeed a value from 0.0 - 10.0, where 10 is the fastest firing speed.
     * @param range how far away tower can find targets to shoot at
     */
    public ShootingTower (ITower baseTower, double damage, double firingSpeed, double range, String img) {
        super(baseTower, new ProjectileLauncher(baseTower, damage, firingSpeed, range, img));
        myDamage = damage;
        myFiringSpeed = firingSpeed;
        myRange = range;
    }

    /**
     * Constructor used by the factory in decorating a final tower.
     * @param baseTower
     * @param attributes
     */
    public ShootingTower (ITower baseTower, Map<String, Serializable> attributes, String img) {
        this(
             baseTower,
             (double) TDObject.getValueOrDefault(attributes, TowerSchema.DAMAGE, DEFAULT_DAMAGE),
             (double) TDObject.getValueOrDefault(attributes, TowerSchema.FIRING_SPEED,
                                                 DEFAULT_FIRING_SPEED),
             (double) TDObject.getValueOrDefault(attributes, TowerSchema.RANGE, DEFAULT_RANGE), img);
    }

    @Override
    void doDecoratedBehavior (EnvironmentKnowledge environ) {

    }

}
