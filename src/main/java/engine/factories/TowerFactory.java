package main.java.engine.factories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;
import main.java.author.model.TowerSchema;
import main.java.engine.objects.tower.SimpleTower;

import main.java.exceptions.engine.InvalidTowerCreationParametersException;

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

    public void placeTower(double x, double y, String towerName) throws InvalidTowerCreationParametersException {
        JGPoint tileOrigin = findTileOrigin(x, y);
        try {
            TowerSchema schema = towerSchemas.get(towerName);
            new SimpleTower(tileOrigin.x, tileOrigin.y, schema.getMyDamage(), schema.getMyRange(), schema.getMyImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JGPoint findTileOrigin(double x, double y) {
        int curXTilePos = (int) x/engine.tileWidth() * engine.tileWidth();
        int curYTilePos = (int) y/engine.tileHeight() * engine.tileHeight();
        return new JGPoint(curXTilePos, curYTilePos);
    }
}
