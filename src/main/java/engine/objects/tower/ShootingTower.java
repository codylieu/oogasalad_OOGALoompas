package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.Projectile;
import main.java.engine.objects.TDObject;
import main.java.schema.tdobjects.TowerSchema;


public class ShootingTower extends TowerBehaviorDecorator {

    public static final double DEFAULT_DAMAGE = 10;
    public static final double DEFAULT_RANGE = 200;
    public static final double DEFAULT_FIRING_SPEED = 5;
    
    private static final int FIRING_INTERVAL_STEP = 2;
    private static final int MIN_FIRING_INTERVAL = 21;
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
    public ShootingTower (ITower baseTower, double damage, double firingSpeed, double range) {
        super(baseTower);
        myDamage = damage;
        myFiringSpeed = firingSpeed;
        myRange = range;
    }

    public ShootingTower (ITower baseTower, Map<String, Serializable> attributes) {
        this(
             baseTower,
             (double) TDObject.getValueOrDefault(attributes, TowerSchema.DAMAGE, DEFAULT_DAMAGE),
             (double) TDObject.getValueOrDefault(attributes, TowerSchema.FIRING_SPEED, DEFAULT_FIRING_SPEED),
             (double) TDObject.getValueOrDefault(attributes, TowerSchema.RANGE, DEFAULT_RANGE));     
    }

    @Override
    void doDecoratedBehavior (EnvironmentKnowledge environ) {
        // fire at the nearest enemy!
        doTowerFiring(environ.getNearestMonsterCoordinate(getXCoordinate(), getYCoordinate()));
    }

    /**
     * 
     * Calls the tower's firing method, if any, when within the appropriate firing interval.
     * 
     * @param target
     * 
     */
    private void doTowerFiring (Point2D target) {
        if (target == null) { return; }
        Point2D currCoor = new Point2D.Double(getXCoordinate(), getYCoordinate());
        if (inFiringInterval() && target.distance(currCoor) < myRange) {
            fireProjectile(target);
        }
    }

    /**
     * Returns whether or not it is time for the tower to fire, based on its
     * firing speed
     * 
     * @return
     */
    private boolean inFiringInterval () {
        return atInterval(MIN_FIRING_INTERVAL - FIRING_INTERVAL_STEP * (int) Math.min(myFiringSpeed, 10));
    }

    /**
     * Fires projected at a target with the tower's damage factor
     */
    public void fireProjectile (Point2D target) {
        /* trigonometry from Guardian JGame example */
        double angle =
                Math.atan2(target.getX() - getXCoordinate(), target.getY() - getYCoordinate());
        new Projectile(getXCoordinate(), getYCoordinate(), angle, myDamage);
    }

}
