package main.java.engine.factories;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jgame.impl.JGEngineInterface;
import main.java.engine.objects.tower.SimpleTower;

import main.java.engine.objects.tower.Tower;
import main.java.exceptions.engine.InvalidTowerCreationParametersException;
import main.java.schema.TowerSchema;

public class TowerFactory {
    private JGEngineInterface engine;
    private Map<String, TowerSchema> towerSchemas;

    public TowerFactory(JGEngineInterface engine) {
        this.engine = engine;
    }

    /**
     * Loads a list of TowerSchemas into the factory
     *
     * @param schemas List of TowerSchemas to load
     */
    public void loadSchemas(List<TowerSchema> schemas) {
        towerSchemas = new HashMap<String, TowerSchema>();
        for (TowerSchema s : schemas) {
            towerSchemas.put(s.getMyName(), s);
        }
    }

    public Tower placeTower(Point2D location, String towerName) throws InvalidTowerCreationParametersException {
        Point2D tileOrigin = findTileOrigin(location);

        try {
            TowerSchema schema = towerSchemas.get(towerName);
            return new SimpleTower(tileOrigin, schema.getMyDamage(), schema.getMyRange(), schema.getMyImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Point2D findTileOrigin(Point2D location) {
        int curXTilePos = (int) location.getX()/engine.tileWidth() * engine.tileWidth();
        int curYTilePos = (int) location.getY()/engine.tileHeight() * engine.tileHeight();
        return new Point2D.Double(curXTilePos, curYTilePos);
    }
}
