package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.detector.TargetDetectorInterface;
import main.java.engine.objects.detector.monsterdetector.*;
import main.java.engine.objects.projectile.DamageProjectile;
import main.java.engine.objects.projectile.PiercingProjectile;
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
    public static final int FIRING_INTERVAL_STEP = 2;
    public static final int MIN_FIRING_INTERVAL = 21;

    protected double myDamage;
    protected double myFiringSpeed;
    protected double myRange;
    protected String myBulletImage;
    
    private TargetDetectorInterface myDetector = new MonsterClosestToExitDetector();

    /**
     * Create a new tower by adding shooting behavior to an existing tower
     * 
     * @param baseTower tower to be expanded with shooting behavior
     * @param damage
     * @param firingSpeed a value from 0.0 - 10.0, where 10 is the fastest firing speed.
     * @param range how far away tower can find targets to shoot at
     */
    public ShootingTower (ITower baseTower,
                          double damage,
                          double firingSpeed,
                          double range,
                          String bulletImage) {
        super(baseTower);
        myDamage = damage;
        myFiringSpeed = firingSpeed;
        myRange = range;
        myBulletImage = bulletImage;
    }

    /**
     * Constructor used by the factory in decorating a final tower.
     * 
     * @param baseTower
     * @param attributes
     */
    public ShootingTower (ITower baseTower, Map<String, Serializable> attributes) {
        this(
             baseTower,
             (double) TDObject.getValueOrDefault(attributes, TowerSchema.DAMAGE, DEFAULT_DAMAGE),
             (double) TDObject.getValueOrDefault(attributes, TowerSchema.FIRING_SPEED,
                                                 DEFAULT_FIRING_SPEED),
             (double) TDObject.getValueOrDefault(attributes, TowerSchema.RANGE,
                                                 DEFAULT_RANGE),
             (String) TDObject.getValueOrDefault(attributes, TowerSchema.BULLET_IMAGE_NAME, ""));
    }

    @Override
    void doDecoratedBehavior (EnvironmentKnowledge environ) {
//        fire(environ.getNearestMonsterCoordinate(getXCoordinate(), getYCoordinate()));
    	List<Object> target = myDetector.findTarget(getXCoordinate(), getYCoordinate(), myRange, environ);
    	if (target.size() < 1) return;
    	fire((Point2D) target.get(0));
    }

    private void fire (Point2D target) {
        if (target == null) { return; }
//        Point2D currCoor = new Point2D.Double(getXCoordinate(), getYCoordinate());
//        if (inFiringInterval() && target.distance(currCoor) < myRange) {
        if (inFiringInterval()) {
            /* trigonometry from Guardian JGame example */
            double angle =
                    Math.atan2(target.getX() - getXCoordinate(), target.getY() - getYCoordinate());
            fireProjectile(angle);
        }
    }

    /**
     * Returns whether or not it is time for the tower to fire, based on its
     * firing speed
     * 
     * @return
     */
    private boolean inFiringInterval () {
        return baseTower.atInterval(MIN_FIRING_INTERVAL - FIRING_INTERVAL_STEP *
                                    (int) Math.min(myFiringSpeed, 10));
    }

    /**
     * Fires projected at a target angle with the tower's damage factor
     */

    public void fireProjectile (double angle) {

        new PiercingProjectile(getXCoordinate(), getYCoordinate(), angle, myDamage, myBulletImage);

    }

    /**
     * Fires projected at a target x and y speed with the tower's damage factor
     */
    public void fireProjectile (double xspeed, double yspeed) {
        new PiercingProjectile(getXCoordinate(), getYCoordinate(), xspeed, yspeed, myDamage,
                             myBulletImage);
    }
    
    protected Point2D centerCoordinate () {
        return new Point2D.Double(getXCoordinate() + ((SimpleTower) baseTower).getImageBBoxConst().width / 2,
                                  getYCoordinate() + ((SimpleTower) baseTower).getImageBBoxConst().height / 2);
    }
    
    
}
