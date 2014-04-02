package main.java.engine;

import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.platform.StdGame;

import java.awt.event.MouseEvent;

public class TestEngine extends JGEngine {
    private static TestEngine engine;
    private Model model;

    public TestEngine() {
        initEngineApplet();
    }

    public TestEngine(JGPoint size) {
        initEngine(size.x,size.y);
    }


    public static void main(String[] args) {
        engine = new TestEngine(StdGame.parseSizeArgs(args,0));
    }

    @Override
    public void initCanvas() {
        setCanvasSettings(25, 20, 32, 32, null, JGColor.black, null);
    }

    @Override
    public void initGame() {
        setFrameRate(45, 1);
        this.model = new Model();
        model.setEngine(this);
        model.loadMap("testmap.json");
        defineMedia("/main/resources/media.tbl");
    }

    @Override
    public void doFrame() {
        super.doFrame();
        if (getMouseButton(1)) {
            model.placeTower(getMouseX(), getMouseY());
        }
    }

    @Override
    public void paintFrame() {
        highlightMouseoverTile();
    }

    private void highlightMouseoverTile() {
        JGPoint mousePos = getMousePos();
        int curXTilePos = mousePos.x/tileWidth() * tileWidth();
        int curYTilePos = mousePos.y/tileHeight() * tileHeight();

        this.drawRect(curXTilePos, curYTilePos, tileWidth(), tileHeight(), false, false, 1.0, JGColor.yellow);
    }
}
