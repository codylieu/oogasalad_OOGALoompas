package main.java.engine.factory;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jgame.impl.JGEngineInterface;
import main.java.engine.objects.tower.SimpleTower;

import main.java.engine.objects.tower.Tower;
import main.java.schema.SimpleTowerSchema;

public class TowerFactory {
    private JGEngineInterface engine;
    private Map<String, SimpleTowerSchema> towerSchemas;

    public TowerFactory(JGEngineInterface engine) {
        this.engine = engine;
    }

    /**
     * Loads a list of TowerSchemas into the factory
     *
     * @param schemas List of TowerSchemas to load
     */
    public void loadSchemas(List<SimpleTowerSchema> schemas) {
        towerSchemas = new HashMap<String, SimpleTowerSchema>();

        for (SimpleTowerSchema s : schemas) {
            towerSchemas.put(s.getMyName(), s);
        }
    }

    public Tower placeTower(Point2D location, String towerName) {
        Point2D tileOrigin = findTileOrigin(location);

        try {
            SimpleTowerSchema schema = towerSchemas.get(towerName);
            return new SimpleTower(tileOrigin, schema.getMyDamage(), schema.getMyRange(), schema.getMyImage(), schema.getMyCost());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Point2D findTileOrigin(Point2D location) {
        int curXTilePos = (int) location.getX()/engine.tileWidth() * engine.tileWidth();
        int curYTilePos = (int) location.getY()/engine.tileHeight() * engine.tileHeight();

        return new Point2D.Double(curXTilePos, curYTilePos);
    }
}
