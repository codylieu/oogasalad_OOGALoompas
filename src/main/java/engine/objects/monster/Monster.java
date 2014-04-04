package main.java.engine.objects.monster;

import java.awt.geom.Point2D;

import main.java.engine.objects.TDObject;


public abstract class Monster extends TDObject {

    public static final int MONSTER_CID = 1;
    
    protected double myHealth;
    protected double myDamage;
    protected double myMoveSpeed;
    protected double myMoneyValue;
    protected IMonsterPath myPathFinder;
    protected Point2D myEntrance;
    protected Point2D myExit;

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
//                    double x,
//                    double y,
    				Point2D entrance,
                    double health,
                    double moveSpeed,
                    double damage,
                    double moneyValue,
                    String graphic,
                    Point2D exit) {
        super(name, entrance.getX(), entrance.getY(), MONSTER_CID, graphic);
        myHealth = health;
        myDamage = damage;
        myMoveSpeed = moveSpeed;
        myMoneyValue = moneyValue;
        myPathFinder = new StraightLinePath(this, exit);
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
    
    /**
     * Get money value received upon death of this monster
     * @return
     */
    public double getMoneyValue() {
    	return myMoneyValue;
    }
}
