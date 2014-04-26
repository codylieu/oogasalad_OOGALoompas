package main.java.engine.objects;

import java.awt.geom.Point2D;
import java.io.Serializable;
import main.java.engine.Model;
import main.java.engine.LevelManager;
import main.java.engine.objects.monster.Monster;
import jgame.JGObject;


/**
 * Exit object designated as the destination of monsters.
 *
 */
public class Exit extends JGObject implements Serializable {

    public static final String IMAGE_NAME = "exit.png";
    public static final String NAME = "exit";
    public static final int EXIT_CID = 2;
    
    /**
     * Wait time allowed before enemy at the exit can keep taking another life from player
     */
    private static final double GRACE_TIME = 200;

    private LevelManager mySpawnManager;
    private Point2D myLocation;
    private double myGraceTime;

    /**
     * Create an exit at the x,y coordinate. Will use JGame collision detection with monster
     * decrement player's lives.
     * 
     * @param x
     * @param y
     * @param model
     */
    public Exit (double x, double y, LevelManager spawnManager) {
        super("Exit", true, x, y, EXIT_CID, NAME);
        myLocation = new Point2D.Double(x,y);
        mySpawnManager = spawnManager;
        //first contact is immediate loss of player life
        myGraceTime = 0;
    }

    @Override
    public void hit (JGObject obj) {
        if (and(obj.colid, Monster.MONSTER_CID)) {
            decreasePlayerLives();  
            ((Monster) obj).setDead();
            obj.remove();
        }
    }
    
    @Override
    public void move() {
    	if (myGraceTime>=0) myGraceTime--;
    }
    
    
    /**
     * Decreases player lives if grace time has passed. 
     */
    private void decreasePlayerLives () {
    	if (!withinGracePeriod()) {
          mySpawnManager.monsterExitAction();
          //refill grace time
          myGraceTime = GRACE_TIME;
    	}
    }
    
    /**
     * @return true we are in the grace period
     */
    private boolean withinGracePeriod() {
    	return myGraceTime>0;
    }

    /**
     * Get this exists x,y coordinate in Point2D
     * @return
     */
    public Point2D getLocation(){
        return myLocation;
    }

}
