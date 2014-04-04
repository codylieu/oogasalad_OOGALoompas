package main.java.engine.objects.monster;

import java.awt.geom.Point2D;

import main.java.engine.objects.TDObject;


public abstract class Monster extends TDObject {

	public static final int MONSTER_CID = 1;

	protected double myHealth;
	protected double myMoveSpeed;
	protected double myMoneyValue;
	protected IMonsterPath myPathFinder;
	protected Point2D myEntrance;
	protected Point2D myExit;

	/* TODO: Clean up/move instance variables to appropriate concrete classes
	 */
	/**
	 * 
	 * @param name
	 * @param unique_id
	 * @param x
	 * @param y
	 * @param collisionid
	 * @param gfxname
	 */
	public Monster (//double x,
			//double y,
			Point2D entrance,
			Point2D exit,
			double health,
			double moveSpeed,
			double rewardAmount,
			String graphic) {
		super("monster", entrance.getX(), entrance.getY(), MONSTER_CID, graphic);
		myHealth = health;
		myMoveSpeed = moveSpeed;
		myMoneyValue = rewardAmount;
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
