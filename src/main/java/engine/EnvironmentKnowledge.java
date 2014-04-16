package main.java.engine;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.ITower;


/**
 * 
 * Use EnvironmentKnowledge to abstract the passing of limited information into Tower and/or Monster
 * behavior method calls.
 * 
 * Goal is to give access to information about the environment of objects,
 * and change their state with controlled methods,
 * but not the ability nor access to directly modify the environmental objects themselves.
 * 
 * Contains helper methods to return info which relies on knowledge unknown to individual objects
 * (typically aggregate info about the entire field of towers and monsters)
 * and methods to change state of environmental objects in a controlled manner.
 * 
 * @author Austin
 * 
 */
public class EnvironmentKnowledge {

    List<Monster> allMonsters;
    Player currentPlayer;
    ITower[][] allTowers;

    /**
     * Add necessary state info.
     * 
     * @param allMonsters
     */
    public EnvironmentKnowledge (List<Monster> allMonsters, Player currentPlayer, ITower[][] towers) {

        // add more parameters as necessary

        this.allMonsters = allMonsters;
        this.currentPlayer = currentPlayer;
        this.allTowers = towers;

    }

    /**
     * Returns the coordinate of the monster nearest to the coordinate passed in
     * 
     * @param x tower x-coor
     * @param y tower y-coor
     * @return coordinates of the nearest monster in the form of a Point2D object
     */
//    public Point2D getNearestMonsterCoordinate (double x, double y) {
//
//        double minDistance = Double.MAX_VALUE;
//        Point2D closestMonsterCoor = null;
//        Point2D towerCoordinate = new Point2D.Double(x, y);
//
//        for (Monster m : allMonsters) {
//            if (m.getCurrentCoor().distance(towerCoordinate) < minDistance) {
//                minDistance = m.getCurrentCoor().distance(towerCoordinate);
//                closestMonsterCoor = centerCoordinate(m);
//            }
//        }
//
//        return closestMonsterCoor;
//    }

    public List<ITower> getTowerCoordinatesInRange (double x, double y, double range) {
        Point2D towerCoordinate = new Point2D.Double(x, y);
        List<ITower> nearbyTowersList = new ArrayList<ITower>();
        for (ITower[] tArray : allTowers) {
            for (ITower t : tArray) {
                if (t != null &&
                    new Point2D.Double(t.getXCoordinate(), t.getYCoordinate())
                            .distance(towerCoordinate) < range) {
                    nearbyTowersList.add(t);
                }
            }
        }
        return nearbyTowersList;
    }

    /**
     * Returns the center of the object for targeting
     * 
     * @param m object coordinate
     * @return the center of the objects image according to the imageBBox
     */
//    private Point2D centerCoordinate (Monster m) {
//        return new Point2D.Double(m.getCurrentCoor().getX() + m.getImageBBoxConst().width / 2,
//                                  m.getCurrentCoor().getY() + m.getImageBBoxConst().height / 2);
//    }

    /**
     * Give the current player a specified amount of money.
     * 
     * @param grantedMoney money to be given to player
     */
    public void grantPlayerMoney (int grantedMoney) {
        currentPlayer.addMoney(grantedMoney);
    }
    
    /**
     * Returns the list of all the monsters on map
     * 
     * @return list of monsters
     */
    public List<Monster> getAllMonsters() {
    	return allMonsters;
    }
    
    /**
     * Returns the array representing all towers on map
     * 
     * @return an array of towers
     */
    public ITower[][] getAllTowers() {
    	return allTowers;
    }

}
