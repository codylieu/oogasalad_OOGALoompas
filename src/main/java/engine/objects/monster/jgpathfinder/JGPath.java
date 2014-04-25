package main.java.engine.objects.monster.jgpathfinder;

import jgame.JGPoint;

import java.util.Iterator;
import java.util.LinkedList;

public class JGPath implements Iterable<JGPoint> {
    private LinkedList<JGPoint> path;

    /**
     * Create a new, empty JGPath.
     */
    public JGPath() {
        path = new LinkedList<JGPoint>();
    }

    /**
     * Create a new copy of the given path.
     *
     * @param path The path you want a copy of
     */
    public JGPath(JGPath path) {
        this.path = new LinkedList<JGPoint>(path.path);
    }

    /**
     * Add a point at the end of the path.
     *
     * @param point The point (tile index) to be added
     */
    public void add(JGPoint point) {
		if (point == null) {
			throw new NullPointerException("Cannot add null point to path");
		}

        path.add(point);
    }

    /**
     * Add specified point to the beginning of the path.
     *
     * @param point Tile index to be added
     */
    public void addFirst(JGPoint point) {
		if (point == null) {
			throw new NullPointerException("Cannot add null point to path");
		}

        path.addFirst(point);
    }

    /**
     * Returns if the path has another point.
     *
     * @return If the path has another point
     */
    public boolean hasNext() {
        return (path.peek() != null);
    }

    /**
     * Retrieves and removes, the next point of the path.
     *
     * @return The tile index of the next point in the path
     */
    public JGPoint getNext() {
        return path.poll();
    }

    /**
     * Retrieves, but does not remove, the next point of the path. Returns
     * null if there is no more points in the path.
     *
     * @return The tile index of the next point in the path
     */
    public JGPoint peek() {
        return path.peek();
    }

	/**
	 * Iterates through each JGPoint in the JGPath.
	 *
	 * @return An iterator for JGPath
	 */
	@Override
	public Iterator<JGPoint> iterator() {
		return path.iterator();
	}
}
