package main.java.engine.objects.projectile;

import main.java.engine.objects.monster.Monster;
import jgame.JGObject;

/**
 * 
 * Projectiles that pierce through enemies
 * and deal damage to all enemies along that path
 *
 */
public class PiercingProjectile extends DamageProjectile {

    public static final int TOWER_PROJECTILE_CID = 10;
    public static final double DEFAULT_SPEED = 20;

    private double myDamage;

    /**
     * Creates projectile heading in given angle with default speed.
     * 
     * @param x src x-coor
     * @param y src y-coor
     * @param angle Math.atan2(destX - srcX, destY - srcY)
     */
    public PiercingProjectile (
    		double x, 
    		double y, 
    		double angle, 
    		double damage, 
    		String img) {
    	super(x, y, angle, damage, img);
        myDamage = damage;
    }
    
    /**
     * Create projectile with specific src coordinates, xspeed, and yspeed
     * @param x src x-coor
     * @param y src y-coor
     * @param xspeed
     * @param yspeed
     */
    public PiercingProjectile (
    		double x, 
    		double y, 
    		double xspeed, 
    		double yspeed, 
    		double damage, 
    		String img) {
    	super(x, y, xspeed, yspeed, damage, img);
    }
    
    @Override
    public void hit (JGObject obj) {
        if (and(obj.colid, Monster.MONSTER_CID)) {
            ((Monster) obj).takeDamage(myDamage);
        }
    }
}
