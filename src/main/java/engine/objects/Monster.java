package main.java.engine.objects;

import jgame.JGObject;
import jgame.JGPoint;


public class Monster extends TDObject {

    public static final String MONSTER_GFX = "monster";
    public static final int MONSTER_CID = 1;

    private double myDamage;
    private double myMoveSpeed;
    private double myMoneyValue;
    private JGPoint myTargetExit = new JGPoint(0, 0);

    /**
     * 
     * @param name
     * @param unique_id
     * @param x
     * @param y
     * @param collisionid
     * @param gfxname
     */
    public Monster (String name,
                    double x,
                    double y,
                    double health,
                    double moveSpeed,
                    double damage,
                    double moneyValue) {
        super(name, x, y, MONSTER_CID, MONSTER_GFX);
        myHealth = health;
        myDamage = damage;
        myMoveSpeed = moveSpeed;
        myMoneyValue = moneyValue;
    }

    @Override
    public void move () {
        checkDeath();
        // naively move toward exit in straight line path.
        if (x > myTargetExit.x) {
            x--;
        }
        else {
            x++;
        }

        if (y > myTargetExit.y) {
            y--;
        }
        else {
            y++;
        }
    }

    private void checkDeath () {
        if (myHealth <= 0) {
            this.remove();
        }
    }
    
    @Override
    public void hit (JGObject obj) {
        if (and(obj.colid, Tower.TOWER_CID)) {
            ((Tower) obj).takeDamage(myDamage);
        }
    }
}
