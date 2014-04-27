package main.java.engine;

import jgame.JGPoint;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.monster.jgpathfinder.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class PathfinderManager implements Serializable {
	private JGTileMapInterface tileMap;
	private JGPathfinderInterface pathfinder;
	private JGPathfinderHeuristicInterface pathfinderHeuristic;

	public PathfinderManager(JGTileMapInterface tileMap,
							 JGPathfinderHeuristicInterface pathfinderHeuristic) {
		this.tileMap = tileMap;
		this.pathfinderHeuristic = pathfinderHeuristic;
	}

	public JGPath getPath(JGPoint source, JGPoint target,
						  Set<Integer> blocked) throws NoPossiblePathException {
		pathfinder = new JGPathfinder(tileMap, pathfinderHeuristic);
		tileMap.setBlockedTiles(blocked);

		return pathfinder.getPath(source, target);
	}

	public void updatePaths(List<Monster> monsters) {
		for (Monster m : monsters) {
			m.updatePath();
		}
	}
}
