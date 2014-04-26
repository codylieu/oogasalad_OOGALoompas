package main.java.engine.objects.projectile;

import java.util.HashSet;
import java.util.Set;

import main.java.engine.objects.monster.Monster;
import jgame.JGObject;

/**
 * 
 * Projectiles that pierce through a certain number of enemies and deal damage
 * before being removed. 
 *
 */
public class PiercingProjectile extends DamageProjectile {

    public static final int TOWER_PROJECTILE_CID = 10;
    public static final double DEFAULT_SPEED = 20;
    private static final int DEFAULT_MONSTER_TO_PIERCE = 2;

    private double myDamage;
    private Set<String> hitList;
    

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
        initialize();
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
    	initialize();
    }
    
    private void initialize() {
         hitList = new HashSet<String>();
    }
    
    @Override
    public void hit (JGObject obj) {
        if (and(obj.colid, Monster.MONSTER_CID) && 
        		(!hitList.contains(obj.getName()))) {
            ((Monster) obj).takeDamage(myDamage);
            hitList.add(obj.getName());
            if (hitList.size() >= DEFAULT_MONSTER_TO_PIERCE) this.remove();
        }
    }
}
