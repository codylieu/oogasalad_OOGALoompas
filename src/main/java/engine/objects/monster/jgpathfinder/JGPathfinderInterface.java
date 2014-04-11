package main.java.engine.objects.monster.jgpathfinder;

import jgame.JGPoint;

public interface JGPathfinderInterface {
    /**
     * Implement this interface to create a custom path-finding algorithm.
     *
     * @param source Start x and y coordinate
     * @param target Destination x and y coordinate
     * @return A Path for an object to follow
     */
    public JGPath getPath(JGPoint source, JGPoint target);
}
