package main.java.engine.objects;

import main.java.engine.objects.TDObject;


public class Tower extends TDObject {

    public static final String TOWER_GFX = "tower";
    public static final int TOWER_CID = 0;

    public static final double DEFAULT_DAMAGE = 50;
    public static final double DEFAULT_RANGE = 50;

    private double myDamage;
    private double myRange;

    /**
     * Create a tower at the specified x,y coordinate.
     * Currently sets default instance vars.
     * @param x
     * @param y
     */
    public Tower (double x, double y) {
        super("tower", x, y, TOWER_CID, TOWER_GFX);
        myDamage = DEFAULT_DAMAGE;
        myRange = DEFAULT_RANGE;
    }

    /**
     * Shoot a projectile in the direction of the specified x,y target coordinates
     * 
     * @param targetX
     * @param targetY
     */
    public void fireProjectile (double targetX, double targetY) {
        /* trigonometry from Guardian JGame example */
        double angle = Math.atan2(targetX - this.x, targetY - this.y);
        new Projectile(this.x, this.y, angle, myDamage);
    }

}
