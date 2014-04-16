package main.java.engine.objects.detector.monsterdetector;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.detector.TargetDetectorInterface;
import main.java.engine.objects.monster.Monster;

/**
 * A detector that finds targets based on distance
 *
 */
public class NearestMonsterDetector implements TargetDetectorInterface{

	@Override
	public List<TDObject> findTarget(double x, double y, EnvironmentKnowledge environmentKnowledge) {
		double minDistance = Double.MAX_VALUE;
		List<TDObject> closestMonster = new ArrayList<TDObject>();
		Point2D towerCoordinate = new Point2D.Double(x, y);

		for (Monster m : environmentKnowledge.getAllMonsters()) {
			if (m.getCurrentCoor().distance(towerCoordinate) < minDistance) {
				minDistance = m.getCurrentCoor().distance(towerCoordinate);
				closestMonster.clear();
				closestMonster.add(m);
			}
		}

		return closestMonster;

	}

}
