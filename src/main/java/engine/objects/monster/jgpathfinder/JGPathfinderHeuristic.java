package main.java.engine.objects.monster.jgpathfinder;

import jgame.JGPoint;

public class JGPathfinderHeuristic implements JGPathfinderHeuristicInterface {

    /**
     * Calculate the heuristic cost by giving tiles closer to the target a higher value.
     *
     * @param source Start tile index
     * @param target Target tile index
     * @return The heuristic cost of moving between the source and target tiles
     */
    @Override
    public double calculateHeuristicCost(JGPoint source, JGPoint target) {
        return distanceBetween(source, target);
    }

    private double distanceBetween(JGPoint source, JGPoint target) {
        double dx = Math.abs(source.x - target.x);
        double dy = Math.abs(source.y - target.y);

        final double D = 1; // TODO: find wtf d should be

        return D * (dx + dy);
    }
}
