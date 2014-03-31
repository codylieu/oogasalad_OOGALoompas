package main.java.engine.objects;

import jgame.JGObject;
import ProjectPoseidon.Constants;
import main.java.engine.objects.TDObject;


public class Tower extends TDObject {

    public static final String TOWER_GFX = "tower";
    public static final int TOWER_CID = 0;

    private double health;
    private double damage;
    private double range;

    /**
     * Create a tower at the specified x,y coordinate.
     * @param x
     * @param y
     */
    public Tower (double x, double y) {
        super("tower", x, y, TOWER_CID, TOWER_GFX);
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
        new Projectile(this.x, this.y, angle);
    }

}
