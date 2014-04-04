package main.java.engine.factory;

import java.awt.geom.Point2D;

import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.monster.SimpleMonster;
import jgame.impl.JGEngineInterface;

public class MonsterFactory {
   private JGEngineInterface engine;

   public MonsterFactory(JGEngineInterface engine) {
        this.engine = engine;
    }
   
   public MonsterFactory() {
	   this(null);
   }

   public Monster placeMonster(Point2D entrance, Point2D exit) {
       return new SimpleMonster(entrance, exit);
   }
 
}
