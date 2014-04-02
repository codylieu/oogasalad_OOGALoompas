package main.java.engine.objects;

import jgame.JGPoint;


public class Monster extends TDObject {

    public static final String MONSTER_GFX = "monster";
    public static final int MONSTER_CID = 1;

    private double myHealth;
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
                    double moneyValue) {
        super(name, x, y, MONSTER_CID, MONSTER_GFX);
        myHealth = health;
        myMoveSpeed = moveSpeed;
        myMoneyValue = moneyValue;
    }

    @Override
    public void move () {
        //naively move toward exit in straight line path.
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

}
