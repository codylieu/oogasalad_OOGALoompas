package main.java.player;

import java.io.IOException;
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
import main.java.exceptions.engine.TowerCreationFailureException;
import main.java.player.panels.ITowerChooser;
import main.java.player.panels.TowerChooser;
import main.java.player.util.CursorState;
import main.java.player.util.Observing;
import main.java.player.util.Subject;
import main.java.player.util.TowerGhost;
import net.lingala.zip4j.exception.ZipException;


public class TDPlayerEngine extends JGEngine implements Subject, Observing{

	public static final String LIFE_SAVER = "LifeSaver";
	public static final String INSTANT_FREEZE = "InstantFreeze";
	public static final String ANNIHILATOR = "Annihilator";
	public static final String ROW_BOMB = "RowBomb";
	private static final long serialVersionUID = 1L;
	public static int FRAME_RATE_DELTA = 5;
	public static int DEFAULT_FRAME_RATE = 45;
	public static int LEFT_CLICK = 1;
	public static int RIGHT_CLICK = 3;

	private ITowerChooser towerChooser;
	private Model model;
	private List<Observing> observers;
	private CursorState cursorState;
	private boolean hasChanged;
	private boolean isFullScreen;
	private String towerName;
	private ResourceBundle hotkeys = ResourceBundle.getBundle("main.resources.hotkeys");

	public TDPlayerEngine() {
		super();
		initEngineComponent(960, 640);
		observers = new ArrayList<Observing>();
		hasChanged = true;
		isFullScreen = false;
		cursorState = CursorState.None;
	}

	@Override
	public void initCanvas() {
		setCanvasSettings(25, 20, 32, 32, null, JGColor.black, null);
	}

	@Override
	public void initGame() {
		setFrameRate(DEFAULT_FRAME_RATE, 1);
		this.model = new Model(this);
	}

	public void speedUp() {
		setFrameRate(getFrameRate() + FRAME_RATE_DELTA, 1);
		System.out.println(getFrameRate());
	}

	/*
	 * Returns whether the game was slowed down or not
	 */
	public boolean slowDown() {
		if (getFrameRate() - FRAME_RATE_DELTA > 0) {
			setFrameRate(getFrameRate() - FRAME_RATE_DELTA, 1);
			return true;
		}
		return false;
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
		if (mousePos.x < pfWidth() && mousePos.x > 0 && mousePos.y < pfHeight() && mousePos.y > 0) {
			if (cursorState == CursorState.AddTower) {
				if (model.isTowerPresent(mousePos.x, mousePos.y)) {
					color = JGColor.red;
				}
				else {
					color = JGColor.green;
				}

			}
			else {
				if (model.isTowerPresent(mousePos.x, mousePos.y)) {
					color = JGColor.orange;
				}
			}

		}

		this.drawRect(curXTilePos, curYTilePos, tileWidth(), tileHeight(), false, false, 1.0, color);
	}

	/*private void displayGameStats() {
		this.drawString("Score: "+model.getScore(), 50, 25, -1);
		this.drawString("Lives left: "+model.getPlayerLives(), 50, 50, -1);
		this.drawString("Money: "+model.getMoney(), 50, 75, -1);
		this.drawString("Game clock: "+model.getGameClock(), 50, 100, -1);
	}*/

	/*public TDObject getSelectedObject() {
		JGPoint mousePos = getMousePos();
		int curXTilePos = mousePos.x/tileWidth() * tileWidth();
		int curYTilePos = mousePos.y/tileHeight() * tileHeight();
		if (mousePos.x < pfWidth() && mousePos.x > 0 && mousePos.y < pfHeight() && mousePos.y > 0)
			if (model.isTowerPresent(mousePos.x, mousePos.y))
				return ;
	}*/

	public String getCurrentDescription() {
		/*JGPoint mousePos = getMousePos();
		if (mousePos.x < pfWidth() && mousePos.x > 0 && mousePos.y < pfHeight() && mousePos.y > 0) {
			if (model.isTowerPresent(mousePos.x, mousePos.y)){
				System.out.println(getObjects("tower", 0, true, null).size());
				return getObjects("tower", 0, true, null).get(1).toString();//remove null to bounding box
			}
			else
				return "";
		}
		else*/
		return "";

	}


	@Override
	public void doFrame() {
		super.doFrame();
		if (cursorState == CursorState.AddTower) {
			if (getMouseButton(LEFT_CLICK)) {
				model.placeTower(getMouseX(), getMouseY(), towerName);
				setCursorState(CursorState.None);
				removeObjects("TowerGhost", 0);
				clearMouseButton(LEFT_CLICK);
			}
			else
				drawTowerGhost();
		}
		else if (cursorState == CursorState.None) {
			if (getMouseButton(LEFT_CLICK) && getKey(Integer.parseInt(hotkeys.getString("UpgradeTower")))) {
				try {
					model.upgradeTower(getMouseX(), getMouseY());
				} catch (TowerCreationFailureException e) {
					e.printStackTrace();
				}

				clearMouseButton(LEFT_CLICK);
				clearKey(Integer.parseInt(hotkeys.getString("UpgradeTower")));
			}

			setItem(LEFT_CLICK, ROW_BOMB);
			setItem(LEFT_CLICK, ANNIHILATOR); // n key
			setItem(LEFT_CLICK, INSTANT_FREEZE); // i key
			setItem(LEFT_CLICK, LIFE_SAVER); // l key
		}

		notifyObservers();

		checkKeys();

		if (getMouseButton(RIGHT_CLICK)) {
			model.checkAndRemoveTower(getMouseX(), getMouseY());
			clearMouseButton(3);
		}
		try {
			model.updateGame();
		} catch (MonsterCreationFailureException e) {
			e.printStackTrace();
		}
		moveObjects();
		model.checkCollisions();
	}

	private void setItem(int clickName ,String itemName){
		if (getMouseButton(clickName) && getKey(Integer.parseInt(hotkeys.getString(itemName)))) {
			try {
				model.placeItem(itemName, getMouseX(), getMouseY());
			} catch (Exception e) {
				e.printStackTrace();
			}
			clearKey(Integer.parseInt(hotkeys.getString(itemName)));
		}
	}

	public void update(){
		System.out.println(towerChooser);
		System.out.println(towerChooser.getTowerName());
		towerName = towerChooser.getTowerName();
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

		if (getKey(Integer.parseInt(hotkeys.getString("FullScreen")))){
			toggleFullScreen();
			clearKey(Integer.parseInt(hotkeys.getString("FullScreen")));
		}

	}

	public void toggleFullScreen(){
		if(!isFullScreen){
			initEngineComponent(0,0);
			isFullScreen = true;
		}
		else{
			initEngineComponent(960, 640);
			isFullScreen = false;
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

	public List<String> getPossibleTowers(){
		return model.getPossibleTowers();
	}

	public void loadBlueprintFile(String fileName) throws ClassNotFoundException, IOException, ZipException {
		model.loadGameBlueprint(fileName);
		//model.loadMapTest(fileName);
	}

	public Map<String, String> getGameAttributes() {
		hasChanged = true;
		Map<String, String> gameStats = new HashMap<String, String>();
		gameStats.put("Score", "Score: " + model.getScore());
		gameStats.put("Lives", "Lives left: " + model.getPlayerLives());
		gameStats.put("Money", "Money: " + model.getMoney());
		gameStats.put("Time", "Game clock: " + model.getGameClock());
		return gameStats;
	}

	@Override
	public void setSubject(Subject s) {
		towerChooser = (ITowerChooser) s;
	}
}
