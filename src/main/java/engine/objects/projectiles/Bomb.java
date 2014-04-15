package main.java.engine.objects.projectiles;

import main.java.engine.objects.monster.Monster;
import jgame.JGObject;


public class Bomb extends Projectile {

    public static final double[] BOMB_SPRAY_X = { 0.0, 0.0, -4.0, 4.0, 2.0 -2.0, 2.0, -2.0, 4.0};
    public static final double[] BOMB_SPRAY_Y = { -4.0, 4.0, 0.0, 0.0, 2.0, -2.0, -2.0, 2.0, 4.0};

    private String shrapnelImage;
    private double shrapnelDamage;

    /**
     * Create bomb with specific src coordinates, xspeed, and yspeed
     * Bombs explode into shower of shrapnel projectiles upon hitting a monster.
     * 
     * @param x src x-coordinate
     * @param y src y-coordinate
     * @param xspeed
     * @param yspeed
     * @param bombDamage
     * @param shrapnelDamage
     * @param bombImage
     * @param shrapnelImage
     */
    public Bomb (double x,
                 double y,
                 double angle,
                 double bombDamage,
                 double shrapnelDamage,
                 String bombImage,
                 String shrapnelImage) {
        super(x, y, angle, bombDamage, bombImage);
        this.shrapnelDamage = shrapnelDamage;
        this.shrapnelImage = shrapnelImage;
    }

    @Override
    public void hit (JGObject obj) {
        if (and(obj.colid, Monster.MONSTER_CID)) {
            double explodeXcoordinate = this.x + randomOffset();
            double explodeYcoordinate = this.y + randomOffset();
            for (int i = 0; i < BOMB_SPRAY_X.length; i++) {
                new Projectile(explodeXcoordinate, explodeYcoordinate, BOMB_SPRAY_X[i],
                               BOMB_SPRAY_Y[i], shrapnelDamage, shrapnelImage);
            }
            this.remove();
        }
    }

    private double randomOffset () {
        return (Math.random()-0.5)*100;
    }

}
