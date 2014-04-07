package main.java.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import main.java.engine.Model;
import main.java.exceptions.engine.MonsterCreationFailureException;

public class TDPlayerEngine extends JGEngine implements Subject {
	private Model model;
	private List<Observing> observers;
	private CursorState cursorState;
	private boolean hasChanged;
	private ResourceBundle hotkeys = ResourceBundle.getBundle("main.resources.hotkeys");

	public TDPlayerEngine() {
		super();
		initEngineComponent(960, 640);
		observers = new ArrayList<Observing>();
		hasChanged = true;
		cursorState = CursorState.None;
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
		model.loadSchemas("testtowers");
	}

	@Override
	public void paintFrame() {
		highlightMouseoverTile();
		//displayGameStats();
	}

	public void setCursorState(CursorState newCursorState) {
		cursorState = newCursorState;
	}

	public CursorState getCursorState() {
		return cursorState;
	}

	private void highlightMouseoverTile() {
		JGPoint mousePos = getMousePos();
		int curXTilePos = mousePos.x/tileWidth() * tileWidth();
		int curYTilePos = mousePos.y/tileHeight() * tileHeight();
		JGColor color = JGColor.yellow;
		if (mousePos.x < pfWidth() && mousePos.x > 0 && mousePos.y < pfHeight() && mousePos.y > 0)
			if (cursorState == CursorState.AddTower)
				if (model.isTowerPresent(mousePos.x, mousePos.y))
					color = JGColor.red;
				else
					color = JGColor.green;
		this.drawRect(curXTilePos, curYTilePos, tileWidth(), tileHeight(), false, false, 1.0, color);
	}

	private void displayGameStats() {
		this.drawString("Score: "+model.getScore(), 50, 25, -1);
		this.drawString("Lives left: "+model.getPlayerLife(), 50, 50, -1);
		this.drawString("Money: "+model.getMoney(), 50, 75, -1);
		this.drawString("Game clock: "+model.getGameClock(), 50, 100, -1);
	}

	/*public TDObject getSelectedObject() {
		JGPoint mousePos = getMousePos();
		int curXTilePos = mousePos.x/tileWidth() * tileWidth();
		int curYTilePos = mousePos.y/tileHeight() * tileHeight();
		if (mousePos.x < pfWidth() && mousePos.x > 0 && mousePos.y < pfHeight() && mousePos.y > 0)
			if (model.isTowerPresent(mousePos.x, mousePos.y))
				return ;
	}*/

	@Override
	public void doFrame() {
		super.doFrame();
		if (cursorState == CursorState.AddTower){
			if (getMouseButton(1)) {
				model.placeTower(getMouseX(), getMouseY());
				setCursorState(CursorState.None);
				clearMouseButton(1);
			}
			else
				drawTowerGhost();
		}

		notifyObservers();

		checkKeys();

		if (getMouseButton(3)) { // right click
			model.checkAndRemoveTower(getMouseX(), getMouseY());
			clearMouseButton(3);
		}
		try {
			model.updateGame();
		} catch (MonsterCreationFailureException e) {
			// TODO Implement exception
			e.printStackTrace();
		}
		moveObjects();
		model.checkCollisions();
		//        model.spawnMonster(100, 150);
	}

	public void toggleAddTower() {
		if (getCursorState() == CursorState.AddTower){
			setCursorState(CursorState.None);
			removeObjects("TowerGhost", 0);
		}
		else
			setCursorState(CursorState.AddTower);
	}

	private void checkKeys() {
		if (getKey(Integer.parseInt(hotkeys.getString("AddTower")))){
			toggleAddTower();
			clearKey(Integer.parseInt(hotkeys.getString("AddTower")));
		}

		//THIS ONLY PAUSES FOR NOW
		if (getKey(Integer.parseInt(hotkeys.getString("ToggleRunning")))){
			toggleRunning();
			clearKey(Integer.parseInt(hotkeys.getString("ToggleRunning")));
		}
	}

	public void toggleRunning() {
		if (isRunning())
			stop();
		else
			start();
	}

	private void drawTowerGhost() {
		JGPoint mousePos = getMousePos();
		new TowerGhost(mousePos.x/tileWidth() * tileWidth(), mousePos.y/tileHeight() * tileHeight());
	}

	@Override
	public void register(Observing o) {
		if(!observers.contains(o)) observers.add(o);
	}

	@Override
	public void unregister(Observing o) {
		if(observers.contains(o)) observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		List<Observing> localObservers = null;
		if(!hasChanged) return;
		localObservers = new ArrayList<Observing>(observers);
		hasChanged = false;
		for(Observing o: localObservers){
			o.update();
		}
	}


	public Map<String, String> getGameAttributes() {
		hasChanged = true;
		Map<String, String> gameStats = new HashMap<String, String>();
		gameStats.put("Score", "Score: " + model.getScore());
		gameStats.put("Lives", "Lives left: " + model.getPlayerLife());
		gameStats.put("Money", "Money: " + model.getMoney());
		gameStats.put("Time", "Game clock: " + model.getGameClock());
		return gameStats;
	}
}
