package main.java.engine.objects;

import java.awt.geom.Point2D;
import java.util.Map;

import main.java.engine.objects.tower.SimpleTower;
import jgame.JGObject;


/**
 * Abstract extension of JGObject. Represents monsters, towers, etc.
 */
public abstract class TDObject extends JGObject{

    // TODO: abstract methods?

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
    public static Object getValueOrDefault (Map<String, Object> attributes,
                                            String attributeName,
                                            Object defaultValue) {
        return attributes.containsKey(attributeName) ? attributes.get(attributeName) : defaultValue;
    }

    /**
     * Get current coordinate in a Point2D
     * 
     * @return Current coordinate
     */
    public Point2D getCurrentCoor () {
        return new Point2D.Double(this.x, this.y);
    }
    
    /**
     * Flash by setting image to null based on flashInterval
     * 
     * @param myTimingCounter: timer
     * @param flashInterval: the period at which the object should flash
     * @param myImage: the image of the object when it's done flashing
     */
    protected void flash (double myTimingCounter, double flashInterval, String myImage) {
        if (myTimingCounter % flashInterval == 0) {
            this.setImage(myImage);
        }
        else {
            this.setImage(null);
        }
    }
    
    /**
     * Find the center coordinates of the tower
     * which will be used as the origin of shooting
     * 
     * @return a Point2D object representing center coordinates
     */
    public Point2D centerCoordinate () {
        return new Point2D.Double(x + this.getImageBBoxConst().width / 2,
                                  y + this.getImageBBoxConst().height / 2);
    }
}
