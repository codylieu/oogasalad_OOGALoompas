package main.java.engine.objects.detector.monsterdetector;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.monster.Monster;

public class MonsterClosestToExitDetector extends MonsterDetector {
	
	@Override
	public List<Object> findTarget(double x, double y,
			double range, EnvironmentKnowledge environmentKnowledge) {
		List<Object> targetMonster = new ArrayList<Object>();
		Point2D towerCoordinate = new Point2D.Double(x, y);
		Point2D exitCoordinate = getExitCoordinate(environmentKnowledge);
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
	
	private Point2D getExitCoordinate(EnvironmentKnowledge environmentKnowledge) {
		double exitX = environmentKnowledge.getExit().x;
		double exitY = environmentKnowledge.getExit().y;
		return new Point2D.Double(exitX, exitY);
	}
	
}
