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
	public List<Object> findTarget(double x, double y, double range,
			EnvironmentKnowledge environmentKnowledge) {
        Point2D towerCoordinate = new Point2D.Double(x, y);
        List<Object> nearbyTowersList = new ArrayList<Object>();
        for (ITower[] tArray : environmentKnowledge.getAllTowers()) {
            for (ITower t : tArray) {
                if (t != null &&
                		isWithinDistance(new Point2D.Double(t.getXCoordinate(), t.getYCoordinate()), 
                				towerCoordinate, range)) {
                    nearbyTowersList.add(t);
                }
            }
        }
        return nearbyTowersList;
	}

}
