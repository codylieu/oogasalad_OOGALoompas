package main.java.engine.objects.monster;

import java.awt.geom.Point2D;

import main.java.engine.objects.Exit;
import main.java.engine.objects.TDObject;
import main.java.schema.MonsterSchema;


public abstract class Monster extends TDObject {

<<<<<<< HEAD
=======
	public static final int MONSTER_CID = 1;
	
	public static final String HEALTH = "health";
	public static final String SPEED = "speed";
	public static final String MONEY_VALUE = "moneyValue";
	public static final String ENTRANCE_LOCATION = "entrance";
	public static final String EXIT_LOCATION = "exit";
	
>>>>>>> FETCH_HEAD
	protected double myHealth;
	protected double myMoveSpeed;
	protected double myMoneyValue;
	protected IMonsterPath myPathFinder;
	protected Point2D myEntrance;
	protected Exit myExit;

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
			Exit exit,
			double health,
			double moveSpeed,
			double rewardAmount,
			String graphic) {
	    //TODO make factory add the spread between monsters in the same wave, and remove random from initial x,y
		super("monster", entrance.getX() + Math.random() * 100, entrance.getY() + Math.random() * 100, MonsterSchema.MONSTER_CID, graphic);
		myHealth = health;
		myMoveSpeed = moveSpeed;
		myMoneyValue = rewardAmount;
		myPathFinder = new StraightLinePath(this, exit.getLocation());
	}

	@Override
	public void move () {
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
