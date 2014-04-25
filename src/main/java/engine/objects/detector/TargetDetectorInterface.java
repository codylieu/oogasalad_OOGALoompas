package main.java.engine.objects.detector;

import java.awt.geom.Point2D;
import java.util.List;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;

/**
 * Interface for the object's targeting behavior
 * The object can be a tower, a monster, or an item
 * 
 * @author Lawrence
 */
public interface TargetDetectorInterface {
	
	/**
	 * Find the appropriate target(s)
	 * @param x: x-coor
	 * @param y: y-coor
	 * @param range: range of effect
	 * @param environmentKnowledge
	 * @return list of target locations
	 */
	public List<Point2D> findTarget(double x, double y, double range, EnvironmentKnowledge environmentKnowledge);
	
}
