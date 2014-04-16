package main.java.engine.objects.detector.monsterdetector;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.monster.Monster;

public class MonsterClosestToExitDetector extends MonsterDetector {
	private double range;
	
	@Override
	public List<Object> findTarget(double x, double y,
			double range, EnvironmentKnowledge environmentKnowledge) {
		List<Object> targetMonster = new ArrayList<Object>();
		Point2D towerCoordinate = new Point2D.Double(x, y);
//		Point2D tower

		for (Monster m : environmentKnowledge.getAllMonsters()) {
			if (m.getCurrentCoor().distance(towerCoordinate) < minDistance) {
				minDistance = m.getCurrentCoor().distance(towerCoordinate);
				// a tower should only target one monster at a time
				targetMonster.clear();
				targetMonster.add(centerCoordinate(m));
			}
		}

		return targetMonster;
	}
	
	public List<Object> findTarget(double x, double y, 
			EnvironmentKnowledge environmentKnowledge, double range) {
		this.range = range;
		return findTarget(x, y, environmentKnowledge);
	}

}
