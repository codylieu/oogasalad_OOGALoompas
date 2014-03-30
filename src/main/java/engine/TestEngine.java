package main.java.engine;

import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.platform.StdGame;

public class TestEngine extends JGEngine {
    private static TestEngine engine;

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
        Model model = new Model();
        model.setEngine(this);
        model.loadMap("testmap.json");
    }
}
