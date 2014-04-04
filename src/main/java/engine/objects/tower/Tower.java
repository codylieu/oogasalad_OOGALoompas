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

	/**
	 * This should be a number from 1 (slowest) to 10 (fastest);
	 */
	protected double myFiringSpeed;
	/**
	 * Internal timer shooting at intervals and timing build up phase.
	 */
	protected double myTimingCounter;

	/**
	 * Create a tower at the specified x,y coordinate. Currently sets default
	 * instance vars.
	 * 
	 * @param x
	 * @param y
	 */

	public Tower(Point2D location, String tower_gfx, double damage,
			double range, double cost) {
		super("tower", location.getX(), location.getY(), TOWER_CID, tower_gfx);
		myDamage = damage;
		myRange = range;
		myCost = cost;
		myBuildUpTime = SimpleTower.DEFAULT_BUILDUPTIME;
	}

	/**
	 * Shoot a projectile in the direction of the specified x,y target
	 * coordinates, if it is within firing interval. Call every frame
	 * 
	 * @param target
	 *            coordinate of target
	 * @return
	 */
	public void checkAndfireProjectile(Point2D target) {
		myTimingCounter++;
		
		if(myTimingCounter <= myBuildUpTime) {
			flash();
			return;
		} 
		if (target == null) {
			return;
		}
		Point2D currCoor = new Point2D.Double(x, y);
		if (inFiringInterval() && target.distance(currCoor) < myRange) {
			fireProjectile(target);
		}
		return;
	}
	
	/**
	 *  Flash by setting image to null based on FLASH_INTERVAL
	 */
	private void flash() {
		if (myTimingCounter % SimpleTower.FLASH_INTERVAL == 0) {
			this.setImage(SimpleTower.DEFAULT_GRAPHICS);
		} else {
			this.setImage(null);
		}
	}
	
	
	/**
	 * Returns whether or not it is time for the tower to fire, based on its
	 * firing speed
	 * 
	 * @return
	 */
	private boolean inFiringInterval() {
		return myTimingCounter % Math.max(myFiringSpeed, 10) / 10 == 0;
	}

	/**
	 * Get this tower's cost
	 * 
	 * @param target
	 */
	public double getCost() {
		return myCost;
	}

	abstract void fireProjectile(Point2D target);

}
