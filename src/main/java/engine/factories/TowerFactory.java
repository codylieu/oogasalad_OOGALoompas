package main.java.engine.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;
import main.java.engine.objects.tower.SimpleTower;
import main.java.exceptions.engine.InvalidTowerCreationParametersException;

public class TowerFactory {
    private JGEngineInterface engine;
    private Map<String, List<Object>> userTowerParametersMap = new HashMap<String, List<Object>>();
    private Map<String, String> userTowerConcreteTypeMap = new HashMap<String, String>();

    public TowerFactory(JGEngineInterface engine) {
        this.engine = engine;
        //Add code for reflection mapping of towers from JSON
        List<Object> parameterValuesForPunyTower = new ArrayList<Object>();
        parameterValuesForPunyTower.add(new Double(1));
        parameterValuesForPunyTower.add(new Double(2));
        parameterValuesForPunyTower.add("SimpleTower");
        userTowerParametersMap.put("PunyTower", parameterValuesForPunyTower);
        userTowerConcreteTypeMap.put("PunyTower", "SimpleTower");
    }

    public void placeTower(String userTowerName, double x, double y) throws InvalidTowerCreationParametersException {
        JGPoint tileOrigin = findTileOrigin(x, y);
        try {
            Object[] towerParameters = addLocationToTowerParameters(new ArrayList<Object>(userTowerParametersMap.get(userTowerName)), tileOrigin.x, tileOrigin.y);
        	Reflection.createInstance("main.java.engine.objects.tower."+userTowerConcreteTypeMap.get(userTowerName), towerParameters);

        }
        catch (Exception e) {
        	throw new InvalidTowerCreationParametersException();
        }
    }
    
    private Object[] addLocationToTowerParameters(List<Object> parameters, double x, double y) {
    	parameters.add(0, y);
    	parameters.add(0,x);
    	return parameters.toArray();
    }

    public JGPoint findTileOrigin(double x, double y) {
        int curXTilePos = (int) x/engine.tileWidth() * engine.tileWidth();
        int curYTilePos = (int) y/engine.tileHeight() * engine.tileHeight();
        return new JGPoint(curXTilePos, curYTilePos);
    }
}
