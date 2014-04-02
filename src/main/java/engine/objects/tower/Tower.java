package main.java.engine.objects.tower;

import java.awt.geom.Point2D;

import main.java.engine.objects.TDObject;


public abstract class Tower extends TDObject {

    public static final int TOWER_CID = 0;

    protected double myDamage;
    protected double myRange;

    /**
     * Create a tower at the specified x,y coordinate.
     * Currently sets default instance vars.
     * @param x
     * @param y
     */
    public Tower (double x, double y, String tower_gfx, double damage, double range) {
        super("tower", x, y, TOWER_CID, tower_gfx);
        myDamage = damage;
        myRange = range;
    }
   
    /**
     * Shoot a projectile in the direction of the specified x,y target coordinates
     * 
     * @param Point2D coordinate of target
     * @return 
     */
    public void checkAndfireProjectile(Point2D target) {
    	if(target == null) return;
    	Point2D currCoor = new Point2D.Double(x, y);
    	if(target.distance(currCoor) < myRange ) {
    		fireProjectile(target);
    	}
    }
    
    abstract void fireProjectile(Point2D target);

}
