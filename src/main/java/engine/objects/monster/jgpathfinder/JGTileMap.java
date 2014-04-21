package main.java.engine.objects.monster.jgpathfinder;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JGTileMap implements JGTileMapInterface {
	public static final int DEFAULT_COST = 1;

	private JGEngineInterface engine;
    private JGPoint[][] tileMap;
    private Map<Integer, Integer> costMap;
    private Set<Integer> blockedCIDs;

    /**
     * Create a tilemap with no costs to moving between tiles and no blocked tiles.
     *
     * @param engine The JGEngine used in the game
     */
    public JGTileMap(JGEngineInterface engine) {
        this(engine, null, null);
    }

    /**
     * Create a tilemap with the specified costs to move between tiles in the costMap
     * the blocked tile cids.
     *
     * @param engine The JGEngine used in the game
     * @param costMap A map with the costs to move between given tiles
     * @param blockedCIDs A set of blocked tile cids where the object cannot move to
     */
    public JGTileMap(JGEngineInterface engine, Map<Integer, Integer> costMap, Set<Integer> blockedCIDs) {
        this.engine = engine;
        this.costMap = costMap;
        this.blockedCIDs = blockedCIDs;

        init();
    }

    /**
     * Initializes the internal tilemap.
     */
    private void init() {
        int numXTiles = engine.pfTilesX();
        int numYTiles = engine.pfTilesY();
        tileMap = new JGPoint[numXTiles][numYTiles];

        for (int i = 0; i < numXTiles; i++) {
            for (int j = 0; j < numYTiles; j++) {
                tileMap[i][j] = new JGPoint(i, j);
            }
        }
    }

	@Override
	public int getNumXTiles() {
		return engine.pfTilesX();
	}

	@Override
	public int getNumYTiles() {
		return engine.pfTilesY();
	}

	/**
	 * Get the indexes of the direct neighbors (no diagonals) of the given tile.
	 *
	 * @param tile The tile index you want the neighbors of
	 * @return A list of indexes of the neighboring tiles
	 */
    @Override
	public List<JGPoint> getNeighbors(JGPoint tile) {
        List<JGPoint> neighbors = new ArrayList<JGPoint>();

        int[] xDirs = new int[]{0, 1, 0, -1};
        int[] yDirs = new int[]{1, 0, -1, 0};
        for (int i = 0; i < xDirs.length; i++) {
            int neighborX = tile.x + xDirs[i];
            int neighborY = tile.y + yDirs[i];

            if ((neighborX < tileMap.length && neighborX >= 0) &&
                    (neighborY < tileMap[0].length && neighborY >= 0) &&
                    !isTileBlocked(tileMap[neighborX][neighborY])) {
                neighbors.add(tileMap[neighborX][neighborY]);
            }
        }

        return neighbors;
    }

    @Override
	public int getCostToMove(JGPoint source) {
        // If no cost map specified, return a default value
        if (costMap == null) {
            return DEFAULT_COST;
        }

		// If no cost specified for a given tile, return a default value
        if (!costMap.containsKey(engine.getTileCid(source.x, source.y))) {
            return DEFAULT_COST;
        }

        return costMap.get(engine.getTileCid(source.x, source.y));
    }

	/**
	 * Check if the given tile index is blocked from allowing movement.
	 *
	 * @param tile Tile to be checked if it is blocked
	 * @return Whether the tile is blocked
	 */
	private boolean isTileBlocked(JGPoint tile) {
		// If no blocked list, return false
		if (blockedCIDs == null) {
			return false;
		}

		return blockedCIDs.contains(engine.getTileCid(tile.x, tile.y));
	}
}
