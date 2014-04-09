package main.java.engine.factory;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.engine.Model;
import main.java.engine.objects.Exit;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.Tower;
import main.java.engine.util.Reflection;
import main.java.exceptions.engine.MonsterCreationFailureException;
import main.java.exceptions.engine.TowerCreationFailureException;
import main.java.schema.TDObjectSchema;
import jgame.impl.JGEngineInterface;


public class TDObjectFactory {
    private JGEngineInterface engine;
    private Map<String, TDObjectSchema> tdObjectSchemaMap;

    public TDObjectFactory (JGEngineInterface engine) {
        this.engine = engine;
        tdObjectSchemaMap = new HashMap<>();
    }

    public void loadTDObjectSchemas (List<TDObjectSchema> schemas) {
        // TODO: Get rid of repetition in loading schemas
        for (TDObjectSchema s : schemas) {
            String objName = (String) s.getAttributesMap().get(TDObject.NAME);
            String objImagePath = Model.RESOURCE_PATH + s.getAttributesMap().get(TDObjectSchema.IMAGE_NAME);
            engine.defineImage(objName, "-", 1, objImagePath, "-");
            tdObjectSchemaMap.put(objName, s);
        }
    }

    /**
     * Place tower at a given location's tile.
     * 
     * @param location The coordinates to place the tower at
     * @param towerName The name of the tower to place
     * @return The new Tower object
     * @throws TowerCreationFailureException
     */
    public Tower placeTower (Point2D location, String towerName)
                                                                throws TowerCreationFailureException {
        Point2D tileOrigin = findTileOrigin(location);
        try {
            TDObjectSchema schema = tdObjectSchemaMap.get(towerName);
            schema.addAttribute(Tower.LOCATION, (Serializable) tileOrigin);
            Object[] towerParameters = { schema.getAttributesMap() };

            return (Tower) placeObject(schema.getMyConcreteType(), towerParameters);
        }
        catch (Exception e) {
            throw new TowerCreationFailureException();
        }
    }

    public Monster placeMonster (Point2D entrance, Exit exit, String monsterName)
                                                                                    throws MonsterCreationFailureException {
        try {
            TDObjectSchema schema = tdObjectSchemaMap.get(monsterName);
            schema.addAttribute(Monster.ENTRANCE_LOCATION, (Serializable) entrance);
            schema.addAttribute(Monster.EXIT_LOCATION, exit);
            Object[] monsterParameters = { schema.getAttributesMap() };
            return (Monster) placeObject(schema.getMyConcreteType(), monsterParameters);
        }
        catch (Exception e) {
            throw new MonsterCreationFailureException();
        }
    }

    private Object placeObject (Class<?> objectType, Object[] parameters) {
        return Reflection.createInstance(objectType.getName(), parameters);
    }

    /**
     * Find the top-left corner associated with the tile associated with the given location. Used to
     * place
     * new objects and images.
     * 
     * @param location Coordinate of the map used to find the associated file
     * @return The top left corner of the tile at the given coordinate
     */
    private Point2D findTileOrigin (Point2D location) {
        int curXTilePos = (int) location.getX() / engine.tileWidth() * engine.tileWidth();
        int curYTilePos = (int) location.getY() / engine.tileHeight() * engine.tileHeight();
        return new Point2D.Double(curXTilePos, curYTilePos);
    }
}
