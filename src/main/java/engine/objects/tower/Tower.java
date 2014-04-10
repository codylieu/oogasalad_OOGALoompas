package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import main.java.engine.objects.TDObject;


/**
 * @author user
 * 
 */
public abstract class Tower extends TDObject {

    public static final int TOWER_CID = 0;

    public static final int FLASH_INTERVAL = 5;

    protected double myDamage;
    protected double myRange;
    protected double myCost;
    protected double myBuildUpTime;
    protected String myImage;

    /**
     * This should be a number from 1 (slowest) to 10 (fastest);
     */
    protected double myFiringSpeed;
    /**
     * Internal timer shooting at intervals and timing build up phase.
     */
    protected double myTimingCounter;

    /**
     * Create a new tower
     * 
     * @param location point2d x,y coordinate
     * @param tower_gfx image to be used
     * @param damage damage of tower's projectiles
     * @param range range of tower's aim
     * @param cost money cost of creating tower
     * @param buildup time for this tower's construction
     */
    public Tower (Point2D location, String tower_gfx, double damage,
                  double range, double cost, double buildup) {
        super("tower", location.getX(), location.getY(), TOWER_CID, tower_gfx);
        myImage = tower_gfx;
        myDamage = damage;
        myRange = range;
        myCost = cost;
        myBuildUpTime = buildup;
    }

    /**
     * Shoot a projectile in the direction of the specified x,y target
     * coordinates, if it is within firing interval. Call every frame
     * 
     * @param target
     *        coordinate of target
     * @return
     */
    public void checkAndfireProjectile (Point2D target) {
        myTimingCounter++;

        if (myTimingCounter <= myBuildUpTime) {
            flash();
            return;
        }
        if (target == null) { return; }
        Point2D currCoor = new Point2D.Double(x, y);
        if (inFiringInterval() && target.distance(currCoor) < myRange) {
            fireProjectile(target);
        }
        return;
    }

    /**
     * Flash by setting image to null based on FLASH_INTERVAL
     */
    private void flash () {
        if (myTimingCounter % SimpleTower.FLASH_INTERVAL == 0) {
            this.setImage(myImage);
        }
        else {
            this.setImage(null);
        }
    }

    /**
     * Returns whether or not it is time for the tower to fire, based on its
     * firing speed
     * 
     * @return
     */
    private boolean inFiringInterval () {
        return myTimingCounter % Math.max(myFiringSpeed, 10) / 10 == 0;
    }

    /**
     * Get this tower's cost
     * 
     * @param target
     */
    public double getCost () {
        return myCost;
    }

    abstract void fireProjectile (Point2D target);

    public String toString () {
        return "Damage: " + myDamage + "\n"
               + "Range: " + myRange + "\n"
               + "Cost: " + myCost + "\n";
    }

}
