package main.java.engine.objects.monster;

import java.awt.geom.Point2D;

import main.java.engine.objects.TDObject;


public abstract class Monster extends TDObject {

    public static final int MONSTER_CID = 1;
    
    protected double myHealth;
    protected double myDamage;
    protected double myMoveSpeed;
    protected double myMoneyValue;
    protected IMonsterPath myPathFinder = new StraightLinePath(this);

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
                    double moneyValue,
                    String graphic) {
        super(name, x, y, MONSTER_CID, graphic);
        myHealth = health;
        myDamage = damage;
        myMoveSpeed = moveSpeed;
        myMoneyValue = moneyValue;
    }

    @Override
    public void move () {
        isDead();
        myPathFinder.navigateMonster();
    }

    /**
     *  Check if this object has died and should be removed
     */
    public boolean isDead () {
        return myHealth <= 0;
    }
    
    /**
     * Reduce the health of this object by a damage amount.
     * @param damage afflicting object's damage
     */
    public void takeDamage (double damage) {
        myHealth -= damage;
    }
    
    /**
     * Get current coordinate in a Point2D
     * @return Current coordinate
     */
    public Point2D getCurrentCoor() {
    	return new Point2D.Double(this.x, this.y);
    }
}
