package main.java.engine.factories;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;
import main.java.engine.objects.tower.SimpleTower;
import main.java.engine.objects.tower.Tower;
import main.java.exceptions.engine.InvalidTowerCreationParametersException;

public class TowerFactory {
    private JGEngineInterface engine;
    private Map<String, List<Object>> userTowerParametersMap = new HashMap<String, List<Object>>();
    private Map<String, String> userTowerConcreteTypeMap = new HashMap<String, String>();

    public TowerFactory(JGEngineInterface engine) {
        this.engine = engine;
        //Add code for reflection mapping of towers from JSON
        List<Object> parameterValuesForPunyTower = new ArrayList<Object>();
        parameterValuesForPunyTower.add(SimpleTower.DEFAULT_DAMAGE);
        parameterValuesForPunyTower.add(SimpleTower.DEFAULT_RANGE);
        parameterValuesForPunyTower.add("SimpleTower");
        userTowerParametersMap.put("PunyTower", parameterValuesForPunyTower);
        userTowerConcreteTypeMap.put("PunyTower", "SimpleTower");
    }

    public void placeTower(String userTowerName, Point2D location) throws InvalidTowerCreationParametersException {
        Point2D tileOrigin = findTileOrigin(location);
        try {
            Object[] towerParameters = addLocationToParameters(new ArrayList<Object>(userTowerParametersMap.get(userTowerName)), tileOrigin);
        	Reflection.createInstance("main.java.engine.objects.tower."+userTowerConcreteTypeMap.get(userTowerName), towerParameters);
        }
        catch (Exception e) {
        	throw new InvalidTowerCreationParametersException();
        }
    }
    
    private Object[] addLocationToParameters(List<Object> parameters, Point2D location) {
    	parameters.add(0,location);
    	return parameters.toArray();
    }

    public Point2D findTileOrigin(Point2D location) {
        int curXTilePos = (int) location.getX()/engine.tileWidth() * engine.tileWidth();
        int curYTilePos = (int) location.getY()/engine.tileHeight() * engine.tileHeight();
        return new Point2D.Double(curXTilePos, curYTilePos);
    }
}
