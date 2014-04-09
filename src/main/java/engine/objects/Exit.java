package main.java.engine.objects;

import java.awt.geom.Point2D;
import java.io.Serializable;
import main.java.engine.Model;
import main.java.engine.objects.monster.Monster;
import jgame.JGObject;


public class Exit extends JGObject implements Serializable {

    private static final String EXIT_GRAPHIC = "exit";

    public static final int EXIT_CID = 2;
    
    /**
     * Wait time allowed before enemy at the exit can keep taking another life from player
     */
    private static final double GRACE_TIME = 100;

    private Model myModel;
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
    public Exit (double x, double y, Model model) {
        super("Exit", true, x, y, EXIT_CID, EXIT_GRAPHIC);
        myLocation = new Point2D.Double(x,y);
        myModel = model;
        //first contact is immediate loss of player life
        myGraceTime = 0;
    }

    @Override
    public void hit (JGObject obj) {
        if (and(obj.colid, Monster.MONSTER_CID)) {
            decreasePlayerLives();
        }
    }
    
    /**
     * Decreases player lives if grace time has passed. 
     */
    private void decreasePlayerLives () {
        if(myGraceTime-- <= 0){
            myModel.decrementLives();
            //refill grace time
            myGraceTime = GRACE_TIME;
        }
    }

    /**
     * Get this exists x,y coordinate in Point2D
     * @return
     */
    public Point2D getLocation(){
        return myLocation;
    }

}
