package main.java.engine.objects.monster;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;

import jgame.JGColor;
import jgame.JGPoint;
import main.java.engine.objects.Exit;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.monster.jgpathfinder.*;


public abstract class Monster extends TDObject {

	public static final int MONSTER_CID = 1;

	/*public static final String HEALTH = "health";
	public static final String SPEED = "speed";
	public static final String MONEY_VALUE = "moneyValue";
	public static final String ENTRANCE_LOCATION = "entrance";
	public static final String EXIT_LOCATION = "exit";*/

	protected double myHealth;
	protected double myMoveSpeed;
	protected double myMoneyValue;
	protected JGPathfinderInterface myPathFinder;
	protected Point2D myEntrance;
	protected Exit myExit;
	protected JGPath myPath;

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
			List<Integer> blocked,
			double health,
			double moveSpeed,
			double rewardAmount,
			String graphic) {
		//TODO make factory add the spread between monsters in the same wave, and remove random from initial x,y
		super("monster", entrance.getX() + Math.random() * 100, entrance.getY() + Math.random() * 100, MONSTER_CID, graphic);
		myHealth = health;
		myMoveSpeed = moveSpeed;
		myMoneyValue = rewardAmount;
		myEntrance = entrance;
		myExit = exit;
		myPathFinder = new JGPathfinder(new JGTileMap(eng, null, new HashSet<Integer>(blocked)), new JGPathfinderHeuristic(), eng); // TODO: clean up
		JGPoint pathEntrance = new JGPoint(eng.getTileIndex(x, y)); // TODO: move into diff method
		JGPoint pathExit = new JGPoint(myExit.getCenterTile());
        try {
            myPath = myPathFinder.getPath(pathEntrance, pathExit);
        } catch (NoPossiblePathException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void move () {
		if (myPath.peek() != null) {
			JGPoint waypoint = eng.getTileCoord(myPath.peek());

			// TODO: refactor, quick implementation to test - jordan
			if (((int) (x + 10) >= waypoint.x && (int) (x - 10) <= waypoint.x) &&
					((int) (y + 10) >= waypoint.y && (int) (y - 10) <= waypoint.y)) {
				waypoint = myPath.getNext();
			}

			xdir = Double.compare(waypoint.x, x);
			ydir = Double.compare(waypoint.y, y);
			setDirSpeed(xdir, ydir, myMoveSpeed);
		} else {
			setSpeed(0);
		}
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

	public void reduceSpeed (double speed) {
		myMoveSpeed *= speed;
	}

	/**
	 * Set the monster to be dead immediately, 
	 * effectively removing it from the game
	 */
	public void setDead() {
		myHealth = 0;
		myMoneyValue = 0;
	}


	/**
	 * Get money value received upon death of this monster
	 * @return
	 */
	public double getMoneyValue() {
		return myMoneyValue;
	}

    @Override
    public void paint() {
        if (myPath != null) {
            myPath.paint(5, JGColor.pink);
        }
    }
}
