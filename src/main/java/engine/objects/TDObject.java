package main.java.engine.objects;

import main.resources.jgame.JGObject;

/**
 * Abstract extension of JGObject. Represents monsters, towers, etc.
 */
public abstract class TDObject extends JGObject {

    //TODO: abstract methods?
    
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
    

    
}
