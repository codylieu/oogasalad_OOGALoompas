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
        this.model = new Model(this);
        model.addNewPlayer();
        model.loadMap("testmap.json");
        defineMedia("/main/resources/media.tbl");
        model.setTemporaryWaveSchema();
    }

    @Override
    public void doFrame() {
        super.doFrame();
        if (getMouseButton(1)) {
            model.placeTower(getMouseX(), getMouseY());
            clearMouseButton(1);
        }
        model.updateGame();
        moveObjects();
        model.checkCollisions();
//        model.spawnMonster(100, 150);
    }

    @Override
    public void paintFrame() {
        highlightMouseoverTile();
        displayGameStats();
    }

    private void highlightMouseoverTile() {
        JGPoint mousePos = getMousePos();
        int curXTilePos = mousePos.x/tileWidth() * tileWidth();
        int curYTilePos = mousePos.y/tileHeight() * tileHeight();

        this.drawRect(curXTilePos, curYTilePos, tileWidth(), tileHeight(), false, false, 1.0, JGColor.yellow);
    }
    
    private void displayGameStats() {
    	this.drawString("Score: "+model.getScore(), 50, 25, -1);
    	this.drawString("Lives left: "+model.getPlayerLife(), 50, 50, -1);
    	this.drawString("Money: "+model.getMoney(), 50, 75, -1);
    	this.drawString("Game clock: "+model.getGameClock(), 50, 100, -1);
    }
}
