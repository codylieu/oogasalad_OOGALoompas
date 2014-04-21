package main.java.engine.objects.monster.jgpathfinder;

import jgame.JGPoint;

import java.util.HashSet;
import java.util.PriorityQueue;

public class JGPathfinder implements JGPathfinderInterface {
    private JGPathfinderHeuristicInterface pathfinderHeuristic;
    private JGTileMapInterface tileMap;
    private NodeMap nodeMap;

    /**
     * Create a new pathfinder with the given tilemap and heuristic.
     *
     * @param tileMap The tilemap to use to find a path
     * @param pathfinderHeuristic The heuristic used to calculate
     *                            the next tile to use.
     */
    public JGPathfinder(JGTileMapInterface tileMap, JGPathfinderHeuristicInterface pathfinderHeuristic) {
        this.pathfinderHeuristic = pathfinderHeuristic;
        this.tileMap = tileMap;
        this.nodeMap = new NodeMap(tileMap);
    }

    @Override
    public JGPath getPath(JGPoint source, JGPoint target) throws NoPossiblePathException {
        OpenQueue<Node> open = new OpenQueue<Node>();
        HashSet<Node> closed = new HashSet<Node>();
        open.add(nodeMap.getNode(source));

        Node current = null;
        while (open.peek() != nodeMap.getNode(target) && open.size() > 0) {
            current = open.poll();
            closed.add(current);

            for (JGPoint n :  tileMap.getNeighbors(current.index)) {
                Node neighbor = nodeMap.getNode(n);
                double cost = current.pathCost + tileMap.getCostToMove(neighbor.index);

                if (open.contains(neighbor) && cost < neighbor.pathCost) {
                    open.remove(neighbor);
                }
                if (closed.contains(neighbor) && cost < neighbor.pathCost) {
                    closed.remove(neighbor);
                }
                if (!open.contains(neighbor) && !closed.contains(neighbor)) {
                    neighbor.pathCost = cost;
                    neighbor.heuristicCost = pathfinderHeuristic.calculateHeuristicCost(neighbor.index, target);
                    open.add(neighbor);
                    neighbor.setParent(current);
                }
            }
        }

        if (open.peek() == null) {
            throw new NoPossiblePathException();
        }

        JGPath path = new JGPath();
        current = open.poll();
        while (current.parent != null) {
            path.addFirst(current.index);
            current = current.parent;
        }

        return path;
    }

	/**
	 * Private inner class that represents the list of open nodes as a priority
	 * queue for log(n) insertion and remove-best and a hash set for constant
	 * time membership test.
	 */
	private class OpenQueue<E> {
		private PriorityQueue<E> openQueue;
		private HashSet<E> openSet;

		public OpenQueue() {
			openQueue = new PriorityQueue<E>();
			openSet = new HashSet<E>();
		}

		public void add(E e) {
			openQueue.add(e);
			openSet.add(e);
		}

		public void remove(E e) {
			openQueue.remove(e);
			openSet.remove(e);
		}

		public E peek() {
			return openQueue.peek();
		}

		public E poll() {
			E e = openQueue.poll();
			openSet.remove(e);
			return e;
		}

		public boolean contains(E e) {
			return openSet.contains(e);
		}

		public int size() {
			return openSet.size();
		}
	}

	/**
	 * Private inner class that contains each node associated with a given
	 * tile index. Each node in the node map corresponds with a given tile
	 * in the tilemap.
	 */
    private class NodeMap {
        private Node[][] nodeMap;

        public NodeMap(JGTileMapInterface tileMap) {
            int numXTiles = tileMap.getNumXTiles();
            int numYTiles = tileMap.getNumYTiles();

            nodeMap = new Node[numXTiles][numYTiles];
            for (int i = 0; i < numXTiles; i++) {
                 for (int j = 0; j < numYTiles; j++) {
                    nodeMap[i][j] = new Node(new JGPoint(i, j));
                }
            }
        }

        /**
         * Get node associated with the given tile index.
         *
         * @param index Tile index to get the node of
         * @return The Node object at the index
         */
        public Node getNode(JGPoint index) {
            return nodeMap[index.x][index.y];
        }
    }

	/**
	 * Private inner class that associates extra information with each tile index
	 * that allows for pathfinding algorithms.
	 */
    private class Node implements Comparable {
        private JGPoint index;
        private Node parent;
        private double pathCost;
        private double heuristicCost;
        private int depth;

        public Node(JGPoint index) {
            this.index = index;
        }

        public void setParent(Node parent) {
            this.parent = parent;
            this.depth = parent.depth + 1;
        }

        @Override
        public int compareTo(Object o) {
            Node target = (Node) o;

            double f = heuristicCost + pathCost;
            double targetF = target.heuristicCost + target.pathCost;

            return Double.compare(f, targetF);
        }
    }
}
