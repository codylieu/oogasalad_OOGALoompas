package main.java.engine.objects;

import java.awt.geom.Point2D;
import java.io.Serializable;
import main.java.engine.Model;
import main.java.engine.objects.monster.Monster;
import jgame.JGObject;


public class Exit extends JGObject implements Serializable {

    public static final int EXIT_CID = 2;

    private Model myModel;
    private Point2D myLocation;

    /**
     * Create an exit at the x,y coordinate. Will use JGame collision detection with monster
     * decrement player's lives.
     * 
     * @param x
     * @param y
     * @param model
     */
    public Exit (double x, double y, Model model) {
        super("Exit", true, x, y, EXIT_CID, null);
        myLocation = new Point2D.Double(x,y);
        myModel = model;
    }

    @Override
    public void hit (JGObject obj) {
        if (and(obj.colid, Monster.MONSTER_CID)) {
            myModel.decrementLives();
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
