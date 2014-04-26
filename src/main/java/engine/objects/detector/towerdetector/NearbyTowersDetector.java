package main.java.engine.objects.detector.towerdetector;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.detector.TDDetector;
import main.java.engine.objects.detector.TargetDetectorInterface;
import main.java.engine.objects.tower.ITower;

/**
 * A detector that finds the list of towers within the range
 * of the target tower
 * 
 * @author Lawrence
 *
 */
public class NearbyTowersDetector extends TDDetector {

	@Override
	public List<Point2D> findTarget(double x, double y, double range,
			EnvironmentKnowledge environmentKnowledge) {
        Point2D currentTowerCoordinate = new Point2D.Double(x, y);
        List<Point2D> nearbyTowersList = new ArrayList<Point2D>();
        for (ITower[] tArray : environmentKnowledge.getAllTowers()) {
            for (ITower t : tArray) {
            	Point2D targetTowerCoor = new Point2D.Double(t.getXCoordinate(), t.getYCoordinate());
                if (t != null &&
                		isWithinDistance(targetTowerCoor, 
                				currentTowerCoordinate, range)) {
                    nearbyTowersList.add(targetTowerCoor);
                }
            }
        }
        return nearbyTowersList;
	}

}
