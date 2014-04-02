package main.java.engine.factories;

import jgame.JGPoint;
import jgame.impl.JGEngineInterface;
import main.java.engine.objects.Tower;

public class TowerFactory {
    private JGEngineInterface engine;

    public TowerFactory(JGEngineInterface engine) {
        this.engine = engine;
    }

    public void placeTower(double x, double y) {
        JGPoint tileOrigin = findTileOrigin(x, y);
        new Tower(tileOrigin.x, tileOrigin.y);
    }

    public JGPoint findTileOrigin(double x, double y) {
        int curXTilePos = (int) x/engine.tileWidth() * engine.tileWidth();
        int curYTilePos = (int) y/engine.tileHeight() * engine.tileHeight();

        return new JGPoint(curXTilePos, curYTilePos);
    }
}
