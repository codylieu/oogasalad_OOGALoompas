package main.java.engine.objects.monster.jgpathfinder;

import jgame.JGPoint;

public interface JGPathfinderHeuristicInterface {
    /**
     * Calculate the heuristic cost of moving from the source tile index to
     * the target tile index.
     *
     * @param source Start tile index
     * @param target Target tile index
     * @return The heuristic cost of moving between the source and target tiles
     */
    public double calculateHeuristicCost(JGPoint source, JGPoint target);
}
