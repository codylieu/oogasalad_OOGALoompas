package main.java.engine.objects;

import java.util.Map;
import jgame.JGObject;

/**
 * Abstract extension of JGObject. Represents monsters, towers, etc.
 */
public abstract class TDObject extends JGObject {

    //TODO: abstract methods?
    
    public static final String NAME = "name";

	public TDObject (String name,
                     double x,
                     double y,
                     int cid,
                     String gfxname) {
        super(name, true, x, y, cid, gfxname);
    }

    public TDObject (String name,
                     double x,
                     double y,
                     int cid,
                     String gfxname,
                     double xspeed,
                     double yspeed,
                     int expireOffView) {
       super(name, true, x, y, cid, gfxname, xspeed, yspeed, expireOffView);
    }
    
    /**
     * Within an attribute map, returns the value of the attributeName or returns the defaultValue
     * otherwise
     * 
     * @param attributes map of attributes from a schema
     * @param attributeName desired attribute
     * @param defaultValue default value of attribute if not in attributes map
     * @return
     */
    protected static Object getValueOrDefault (Map<String, Object> attributes,
                                             String attributeName,
                                             Object defaultValue) {
        return attributes.containsKey(attributeName) ? attributes.get(attributeName) : defaultValue;
    }
    
}
