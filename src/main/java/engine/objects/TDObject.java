package engine.objects;

import jgame.JGObject;

public abstract class TDObject extends JGObject {
    protected double health;
    protected double damage;
    protected double range;

    public TDObject(String name, boolean unique_id, double x, double y, int collisionid, String gfxname) {
        super(name, unique_id, x, y, collisionid, gfxname);
    }
}
