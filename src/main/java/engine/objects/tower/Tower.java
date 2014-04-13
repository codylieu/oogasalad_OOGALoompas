package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;


/**
 * Abstract Tower class
 *
 */
public abstract class Tower extends TDObject {

    public static final double DEFAULT_DAMAGE = 10;
    public static final double DEFAULT_HEALTH = 100;
    public static final double DEFAULT_RANGE = 200;
    public static final double DEFAULT_FIRING_SPEED = 5;
    public static final double DEFAULT_COST = 100;
    public static final double DEFAULT_BUILDUPTIME = 100;

    public static final int TOWER_CID = 0;

    public static final int FLASH_INTERVAL = 5;

    protected double myDamage;
    protected double myDamageOffset;
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
     * Call every frame.
     * 
     * Does action specific to the type of tower.
     * 
     * Towers that shoot will fire a projectile in the direction of the specified x,y target
     * coordinates, if it is within firing interval.
     * 
     * MoneyTowers will grant player money if a regeneration time has passed.
     * 
     * @param environ
     *        coordinate of target
     * @return
     */
    public void callTowerActions (EnvironmentKnowledge environ) {
        myTimingCounter++;

        if (myTimingCounter <= myBuildUpTime) {
            flash();
            return;   
            // do no further behavior if still building up
        }


    }

    /**
     * Flash by setting image to null based on FLASH_INTERVAL
     */
    private void flash () {
        if (myTimingCounter % ShootingTower.FLASH_INTERVAL == 0) {
            this.setImage(myImage);
        }
        else {
            this.setImage(null);
        }
    }

    /**
     * Get this tower's cost
     * 
     * @param target
     */
    public double getCost () {
        return myCost;
    }
    
    /**
     * Sets the the tower's damage offset as a proportion of original damage.
     * @param offsetProportion
     * @return offset proportion
     */
    public double setTowerDamageOffset (double offsetProportion) {
    	myDamageOffset = myDamageOffset;
    	return myDamageOffset;
    }

    public String toString () {
        return "Damage: " + myDamage + "\n"
               + "Range: " + myRange + "\n"
               + "Cost: " + myCost + "\n";
    }

}
