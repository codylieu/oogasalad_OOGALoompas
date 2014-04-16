package main.java.engine.objects.detector.monsterdetector;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.detector.TDDetector;
import main.java.engine.objects.monster.Monster;

/**
 * A detector that finds the monster nearest to the exit, 
 * given that it is within the range
 * 
 *  * @author Lawrence
 *
 */
public class MonsterClosestToExitDetector extends TDDetector {
	
	@Override
	public List<Object> findTarget(double x, double y,
			double range, EnvironmentKnowledge environmentKnowledge) {
		List<Object> targetMonster = new ArrayList<Object>();
		Point2D towerCoordinate = new Point2D.Double(x, y);
		Point2D exitCoordinate = environmentKnowledge.getExit().getLocation();
		double minDistance = Double.MAX_VALUE;

		for (Monster m : environmentKnowledge.getAllMonsters()) {
			if (isWithinDistance(m.getCurrentCoor(), exitCoordinate, minDistance) &&
					isWithinDistance(m.getCurrentCoor(), towerCoordinate, range)) {
				minDistance = m.getCurrentCoor().distance(exitCoordinate);
				// a tower should only target one monster at a time
				targetMonster.clear();
				targetMonster.add(centerCoordinate(m));
			}
		}

		return targetMonster;
	}
	
}
