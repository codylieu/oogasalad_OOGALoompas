package main.java.engine.objects.detector;

import java.awt.geom.Point2D;
import java.util.List;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.monster.Monster;

/**
 * Abstract super class designed specifically for 
 * tower defense detectors. 
 * Contains some common methods to avoid duplication
 * 
 * @author Lawrence
 *
 */
public abstract class TDDetector implements TargetDetectorInterface{

	/**
	 * Returns the center of the object for targeting
	 * 
	 * @param m object coordinate
	 * @return the center of the objects image according to the imageBBox
	 */
	protected Point2D centerCoordinate (Monster m) {
//		        return new Point2D.Double(m.getCurrentCoor().getX() + m.getImageBBoxConst().width / 2,
//		                                  m.getCurrentCoor().getY() + m.getImageBBoxConst().height / 2);
		return new Point2D.Double(m.getCurrentCoor().getX(),
				m.getCurrentCoor().getY());
	}

	/**
	 * Check whether the distance between target and object
	 * is smaller than distance passed in
	 * @param obj
	 * @param target
	 * @param distance
	 * @return true if the distance between target and object
	 * is smaller than distance passed in
	 */
	protected boolean isWithinDistance(Point2D obj, Point2D target, double distance) {
		return (obj.distance(target) < distance);
	}

}

