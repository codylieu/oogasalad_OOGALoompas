package main.java.engine.objects.projectile;

import main.java.engine.objects.TDObject;
import main.java.engine.objects.monster.Monster;
import jgame.JGObject;


public class FreezeProjectile extends TDObject {

    public static final int TOWER_PROJECTILE_CID = 10;
    public static final double DEFAULT_SPEED = 20;

    private double mySlowdownSpeed;
    
    /**
     * Create projectile with specific src coordinates, xspeed, and yspeed
     * @param x src x-coor
     * @param y src y-coor
     * @param xspeed
     * @param yspeed
     */
    public FreezeProjectile (double x, double y, double xspeed, double yspeed, double mySlowdownSpeed) {
        super("projectile", x, y, TOWER_PROJECTILE_CID, "red_bullet", xspeed, yspeed,
              JGObject.expire_off_view);
        this.mySlowdownSpeed = mySlowdownSpeed;
    }

    /**
     * Creates projectile heading in given angle with default speed.
     * 
     * @param x src x-coor
     * @param y src y-coor
     * @param angle Math.atan2(destX - srcX, destY - srcY)
     */
    public FreezeProjectile (double x, double y, double angle, double mySlowdownSpeed, String img) {
        super("projectile", x, y, TOWER_PROJECTILE_CID, img,
              DEFAULT_SPEED * Math.sin(angle),
              DEFAULT_SPEED * Math.cos(angle),
              JGObject.expire_off_view);
        this.mySlowdownSpeed = mySlowdownSpeed;
    }
    
    
    @Override
    public void hit (JGObject obj) {
        if (and(obj.colid, Monster.MONSTER_CID)) {
            ((Monster) obj).reduceSpeed(mySlowdownSpeed);
            this.remove();
        }
    }

}
