package main.java.engine.objects.tower;

import java.io.Serializable;
import java.util.Map;

import main.java.engine.objects.TDObject;
import main.java.engine.objects.projectile.FreezeProjectile;
import main.java.schema.tdobjects.TowerSchema;


/**
 * 
 * Towers that shoot bullets to slow monsters down
 *
 */
public class FreezeTower extends ShootingTower {

    public static final double DEFAULT_FREEZE_SLOWDOWN_PROPORTION = 0.5;

    protected double myFreezeSlowdownProportion;

    /**
     * Create a new freeze tower by adding freezing effect to
     * its bullet
     * 
     * @param baseTower tower to be expanded with shooting behavior
     * @param attributes a map of attributes associated with this type of tower
     */
    public FreezeTower (ITower baseTower, Map<String, Serializable> attributes) {
        super(baseTower, attributes);
        myFreezeSlowdownProportion =
                (double) TDObject.getValueOrDefault(attributes,
                                                    TowerSchema.FREEZE_SLOWDOWN_PROPORTION,
                                                    DEFAULT_FREEZE_SLOWDOWN_PROPORTION);
    }

    @Override
    public void fireProjectile (double angle) {
        new FreezeProjectile(
        		((SimpleTower) baseTower).centerCoordinate().getX(),
        		((SimpleTower) baseTower).centerCoordinate().getY(),
        		angle, myFreezeSlowdownProportion, myBulletImage);
    }

}
