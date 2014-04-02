package main.java.engine.factories;

import jgame.impl.JGEngineInterface;
import main.java.engine.objects.Monster;

public class MonsterFactory {
   private JGEngineInterface engine;

   public MonsterFactory(JGEngineInterface engine) {
        this.engine = engine;
    }

   public void placeMoster(double x, double y) {
       new Monster("monster", x, y, 100, 5, 10);
   }
}
