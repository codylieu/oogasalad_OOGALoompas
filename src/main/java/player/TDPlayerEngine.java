package main.java.player;

import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import main.java.engine.Model;

public class TDPlayerEngine extends JGEngine {
    private Model model;
	
	public TDPlayerEngine() {
		super();
		initEngineComponent(960, 640);
	}
	
	@Override
	public void initCanvas() {
		setCanvasSettings(15, 10, 32, 32, null, JGColor.black, null);
	}

	@Override
	public void initGame() {
		setFrameRate(45, 1);
        this.model = new Model(this);
        model.addNewPlayer();
        model.loadMap("testmap.json");
        defineMedia("/main/resources/media.tbl");
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
    }
}
