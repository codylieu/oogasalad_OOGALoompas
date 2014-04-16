package main.java.engine.objects.monster.jgpathfinder;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;

import java.util.HashSet;
import java.util.PriorityQueue;

public class JGPathfinder implements JGPathfinderInterface {
    private JGEngineInterface engine;
    private JGPathfinderHeuristicInterface pathfinderHeuristic;
    private JGTileMap tileMap;
    private NodeMap nodeMap;

    /**
     * Create a new pathfinder with the given tilemap and heuristic.
     *
     * @param tileMap The tilemap to use to find a path
     * @param pathfinderHeuristic The heuristic used to calculate
     *                            the next tile to use.
     * @param engine The engine being played on.
     */
    public JGPathfinder(JGTileMap tileMap, JGPathfinderHeuristicInterface pathfinderHeuristic,
                        JGEngineInterface engine) {
        this.engine = engine;
        this.pathfinderHeuristic = pathfinderHeuristic;
        this.tileMap = tileMap;
        this.nodeMap = new NodeMap(engine);
    }

    @Override
    public JGPath getPath(JGPoint source, JGPoint target) throws NoPossiblePathException {
        PriorityQueue<Node> open = new PriorityQueue<Node>();
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

        JGPath path = new JGPath(engine);
        current = open.poll();
        while (current.parent != null) {
            path.addFirst(current.index);
            current = current.parent;
        }

        return path;
    }

    private class NodeMap {
        private Node[][] nodeMap;

        public NodeMap(JGEngineInterface engine) {
            int numXTiles = engine.pfTilesX();
            int numYTiles = engine.pfTilesY();

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
            if (o == null || !(o instanceof Node)) {
                return 0; // TODO: throw exception?
            }

            Node target = (Node) o;

            double f = heuristicCost + pathCost;
            double targetF = target.heuristicCost + target.pathCost;

            return Double.compare(f, targetF);
        }
    }
}
