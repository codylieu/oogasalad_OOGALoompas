package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.util.Map;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;
import main.java.schema.tdobjects.TowerSchema;


/**
 *
 * Base Tower class 
 * It is wrapped around ("decorated") with tower behaviors such as shooting, money-farming ability, etc.
 *
 */
public class BaseTower extends TDObject implements ITower {

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
    /**
     * Its image name is defined to be exactly the same as its name.
     */
    protected String myImage; 
    protected double myHealth;
    

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
    public BaseTower (Point2D location, double health, double damage,
                  double range, double cost, double buildup, String name) {
        super("tower", location.getX(), location.getY(), TOWER_CID, name);
        myHealth = health;
        myImage = name;
        myDamage = damage;
        myRange = range;
        myCost = cost;
        myBuildUpTime = buildup;
    }
    
    public BaseTower (Map<String, Object> attributes) {
        this(
             (Point2D) getValueOrDefault(attributes, TowerSchema.LOCATION, new Point2D.Double(0, 0)),
             (double) getValueOrDefault(attributes, TowerSchema.HEALTH, DEFAULT_HEALTH),
             (double) getValueOrDefault(attributes, TowerSchema.DAMAGE, DEFAULT_DAMAGE),
             (double) getValueOrDefault(attributes, TowerSchema.RANGE, DEFAULT_RANGE),
             (double) getValueOrDefault(attributes, TowerSchema.COST, DEFAULT_COST),
             (double) getValueOrDefault(attributes, TowerSchema.BUILDUP, DEFAULT_BUILDUPTIME),
             (String) attributes.get(TowerSchema.NAME));     
}

    /**
     * Call every frame.
     * 
     * Will call action specific to the type of tower if and only if past the build up time.
     */
    public void doFrame(EnvironmentKnowledge environ){
        myTimingCounter++;

        if (myTimingCounter <= myBuildUpTime) {
            flash();
            return;   
            // do no further behavior if still building up
        }
        
        callTowerActions(environ);
    }

    public void callTowerActions (EnvironmentKnowledge environ) {
        // do nothing, base tower just sits there.
    }

    /**
     * Flash by setting image to null based on FLASH_INTERVAL
     */
    private void flash () {
        if (myTimingCounter % FLASH_INTERVAL == 0) {
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

    public String toString () {
        return "Damage: " + myDamage + "\n"
               + "Range: " + myRange + "\n"
               + "Cost: " + myCost + "\n";
    }
    
    /**
     * Checks if this tower's internal counter is at the interval passed in.
     * @param intervalFrequency how frequently this method should return true (1 means every frame)
     * @return
     */
    public boolean atInterval(int intervalFrequency) {
        return myTimingCounter % intervalFrequency == 0;
    }

    @Override
    public double getXCoordinate () {
        return x;
    }

    @Override
    public double getYCoordinate () {
        return y;
    }

}
