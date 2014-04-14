package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.Projectile;


public class ShootingTower extends TowerBehaviorDecorator {

    double myDamage;
    double myFiringSpeed;
    double myRange;
    
    /**
     * Create a new tower from a map of attributes. Should be called by factory.
     * 
     * @param attributes key value map of attributes as defined by TowerSchema
     */
    public ShootingTower (ITower baseTower, double damage, double firingSpeed, double range) {
        super(baseTower);
        myDamage = damage;
        myFiringSpeed = firingSpeed;
        myRange = range;
    }


    @Override
    void doDecoratedBehavior (EnvironmentKnowledge environ) {
        //fire at the nearest enemy!
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
        return atInterval(21-2* (int) Math.min(myFiringSpeed, 10));
    }

    /**
     * Fires projected at a target with the tower's damage factor
     */
    public void fireProjectile (Point2D target) {
        /* trigonometry from Guardian JGame example */
        double angle = Math.atan2(target.getX() - getXCoordinate(), target.getY() - getYCoordinate());
        new Projectile(getXCoordinate(), getYCoordinate(), angle, myDamage);
    }

}
