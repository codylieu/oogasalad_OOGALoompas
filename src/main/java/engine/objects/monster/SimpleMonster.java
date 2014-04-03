package main.java.engine.objects.monster;

import java.awt.geom.Point2D;

public class SimpleMonster extends Monster {
    
    public static String MONSTER_GFX = "monster";
    
    public SimpleMonster (Point2D entrance, Point2D exit) {
        super("Simple", entrance, 100, 10, 10, 10, MONSTER_GFX, exit);
//        myPathFinder = new StraightLinePath(this);
    }

}
