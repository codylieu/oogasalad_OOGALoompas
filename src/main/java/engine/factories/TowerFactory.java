package main.java.engine.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;
import main.java.engine.objects.tower.SimpleTower;
import main.java.exceptions.engine.InvalidTowerCreationParameters;

public class TowerFactory {
    private JGEngineInterface engine;
    private Map<String, List<Object>> towerParameters = new HashMap<String, List<Object>>();

    public TowerFactory(JGEngineInterface engine) {
        this.engine = engine;
        //Add code for reflection mapping of towers from JSON
        List<Object> parameterValuesForPunyTower = new ArrayList<Object>();
        parameterValuesForPunyTower.add(new Double(1));
        parameterValuesForPunyTower.add(new Double(2));
        parameterValuesForPunyTower.add("SimpleTower");
        towerParameters.put("PunyTower", parameterValuesForPunyTower);
    }

    public void placeSimpleTower(String simpleTowerType, double x, double y) throws InvalidTowerCreationParameters {
        JGPoint tileOrigin = findTileOrigin(x, y);
        List<Object> simpleTowerParameters = towerParameters.get(simpleTowerType);
        new SimpleTower(tileOrigin.x, tileOrigin.y, (Double) simpleTowerParameters.get(0),
        		(Double) simpleTowerParameters.get(1), (String) simpleTowerParameters.get(2));
        
    /*
        try {
        List<Object> simpleTowerParameters = towerParameters.get(simpleTowerType);
        new SimpleTower(tileOrigin.x, tileOrigin.y, (Double) simpleTowerParameters.get(0),
        		(Double) simpleTowerParameters.get(1), (String) simpleTowerParameters.get(2));
        }
        catch (Exception e) {
        	throw new InvalidTowerCreationParameters();
        }
        */
    }

    public JGPoint findTileOrigin(double x, double y) {
        int curXTilePos = (int) x/engine.tileWidth() * engine.tileWidth();
        int curYTilePos = (int) y/engine.tileHeight() * engine.tileHeight();

        return new JGPoint(curXTilePos, curYTilePos);
    }
}
