package main.java.engine.objects;

public class Monster extends TDObject {
	
	/** Create Monster Objects
	 * @param name
	 * @param x
	 * @param y
	 * @param gfxname
	 * @param health
	 * @param damage
	 * @param range
	 */

    public Monster(String name, boolean unique_id, double x, double y, int collisionid, String gfxname) {
        super(name, unique_id, x, y, collisionid, gfxname);
    }
}
