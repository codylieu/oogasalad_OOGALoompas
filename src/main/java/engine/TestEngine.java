package main.java.engine;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.platform.StdGame;
import main.java.exceptions.engine.MonsterCreationFailureException;

import javax.swing.*;
import main.java.player.util.CursorState;
import main.java.player.util.TowerGhost;

public class TestEngine extends JGEngine {
    private static TestEngine engine;
    private CursorState cursorState;
    private Model model;
    private List<String> towers;
    private int currTower = 0;

    public TestEngine() {
        initEngineApplet();
        //cursorState = CursorState.None;
        cursorState = CursorState.AddTower;
    }

    public TestEngine(JGPoint size) {
        initEngine(800, 600);
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
        towers = model.getPossibleTowers();
    }
    
    @Override
    public void doFrame() {
        super.doFrame();
        displayTowerGhostIfNecessary();
        if(getKey(KeyEvent.VK_1)) {
            currTower++;
            clearKey(KeyEvent.VK_1);
        }
        if (getMouseButton(1)) {
            model.placeTower(getMouseX(), getMouseY(), towers.get(currTower % towers.size()));
            clearMouseButton(1);
        }
        if (getMouseButton(3)) { // right click
        	model.checkAndRemoveTower(getMouseX(), getMouseY());
//        	model.upgradeTower(getMouseX(), getMouseY());
        	clearMouseButton(3);
        }
        if (getKey(KeyEvent.VK_R)) {
        	try {
				model.placeItem("RowBomb", getMouseX(), getMouseY());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	clearKey(KeyEvent.VK_R);
        }
        if (getKey(KeyEvent.VK_A)) {
        	try {
				model.placeItem("Annihilator", getMouseX(), getMouseY());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	clearKey(KeyEvent.VK_A);
        }
        if (getKey(KeyEvent.VK_I)) {
        	try {
				model.placeItem("InstantFreeze", getMouseX(), getMouseY());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	clearKey(KeyEvent.VK_I);
        }
        if (getKey(KeyEvent.VK_L)) {
        	try {
				model.placeItem("LifeSaver", getMouseX(), getMouseY());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	clearKey(KeyEvent.VK_L);
        }
        if (getKey(KeyEvent.VK_M)) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                try {
                    model.loadMapTest(file.getAbsolutePath());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } // TODO: replace to load game blueprint
                System.out.println("FILE CHOSEN: " + file.getName());
            }
        }
        try {
			model.updateGame();
		} catch (MonsterCreationFailureException e) {
			// TODO Implement exception
			e.printStackTrace();
		}
        moveObjects();
        model.checkCollisions();}
//        model.spawnMonster(100, 150);

    @Override
    public void paintFrame() {
        highlightMouseoverTile();
        displayGameStats();
        drawString(towers.get(currTower%towers.size()), pfWidth()/2, 0, 0);
    }
    
    private void displayTowerGhostIfNecessary() {
    	//System.out.println(cursorState);
    	if (cursorState == CursorState.AddTower) {
    	//	System.out.println("displaytower");
    		new TowerGhost(getMouseX(), getMouseY());
    	}
    }

    private void highlightMouseoverTile() {
        JGPoint mousePos = getMousePos();
        int curXTilePos = mousePos.x/tileWidth() * tileWidth();
        int curYTilePos = mousePos.y/tileHeight() * tileHeight();
        JGColor color = JGColor.yellow;
        if (model.isTowerPresent(mousePos.x, mousePos.y)) {
        	color = JGColor.green;
        }
        this.drawRect(curXTilePos, curYTilePos, tileWidth(), tileHeight(), false, false, 1.0, color);
    }
    
    private void displayGameStats() {
    	this.drawString("Score: "+model.getScore(), 50, 25, -1);
    	this.drawString("Lives left: "+model.getPlayerLives(), 50, 50, -1);
    	this.drawString("Money: "+model.getMoney(), 50, 75, -1);
    	this.drawString("Game clock: "+model.getGameClock(), 50, 100, -1);
    }
}
