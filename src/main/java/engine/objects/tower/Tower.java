package main.java.engine.objects.tower;

import java.awt.geom.Point2D;

import main.java.engine.objects.TDObject;


/**
 * @author user
 *
 */
public abstract class Tower extends TDObject {

    public static final int TOWER_CID = 0;

    protected double myDamage;
    protected double myRange;
    protected double myCost;

    /**
     * This should be a number from 1 (slowest) to 10 (fastest);
     */
    protected double myFiringSpeed;
    protected double myFiringCounter;
    
    /**
     * Create a tower at the specified x,y coordinate.
     * Currently sets default instance vars.
     * @param x
     * @param y
     */
    public Tower (Point2D location, String tower_gfx, double damage, double range, double cost) {
        super("tower", location.getX(), location.getY(), TOWER_CID, tower_gfx);
        myDamage = damage;
        myRange = range;
        myCost = cost;
    }
   
    /**
     * Shoot a projectile in the direction of the specified x,y target coordinates
     * 
     * @param Point2D coordinate of target
     * @return 
     */
    public boolean checkAndfireProjectile(Point2D target) {
    	myFiringCounter++;
    	if(target == null) {
    		return false;
    	}
    	Point2D currCoor = new Point2D.Double(x, y);
    	System.out.println(target.distance(currCoor) + " " + myRange);
    	if(inFiringInterval() && target.distance(currCoor) < myRange) {
    		fireProjectile(target);
    		return true;
    	}
    	return false;
    }
    
    /**
     * Returns whether or not it is time for the tower to fire, based on its firing speed
     * @return
     */
    private boolean inFiringInterval() {
		 return myFiringCounter % Math.max(myFiringSpeed, 10)/10 == 0;
	}
    
    /**
     * Get this tower's cost
     * @param target
     */
    public double getCost() {
    	return myCost;
    }
    
	abstract void fireProjectile(Point2D target);

}
