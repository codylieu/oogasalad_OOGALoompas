package main.java.player;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import main.java.data.DataHandler;
import main.java.engine.IModel;
import main.java.engine.Model;
import main.java.engine.util.leapmotion.gamecontroller.LeapGameController;
import main.java.exceptions.engine.InvalidSavedGameException;
import main.java.exceptions.engine.MonsterCreationFailureException;
import main.java.exceptions.engine.TowerCreationFailureException;
import main.java.player.util.CursorState;
import main.java.player.util.Observing;
import main.java.player.util.Subject;
import main.java.player.util.TowerGhost;
import main.java.schema.CanvasSchema;
import main.java.schema.GameBlueprint;
import main.java.schema.GameSchema;
import main.java.schema.map.GameMapSchema;
import net.lingala.zip4j.exception.ZipException;

/**
 * Subclass of JGEngine
 * @author Kevin
 *
 */

public class TDPlayerEngine extends JGEngine implements Subject, ITDPlayerEngine{

	public static final String LIFE_SAVER = "LifeSaver";
	public static final String INSTANT_FREEZE = "InstantFreeze";
	public static final String ANNIHILATOR = "Annihilator";
	public static final String ROW_BOMB = "RowBomb";
	private static final long serialVersionUID = 1L;
	public static int FRAME_RATE_DELTA = 5;
	public static int DEFAULT_FRAME_RATE = 45;
	public static int LEFT_CLICK = 1;
	public static int RIGHT_CLICK = 3;
	public static int TILE_WIDTH = 32;
	public static int TILE_HEIGHT = 32;

	private int xtiles, ytiles;
	private IModel model;
	private List<Observing> observerList;
	private CursorState cursorState;
	private boolean isFullScreen;
	private String pathToBlueprint;
	private String pathToMusic;
	private String towerName;
	private ResourceBundle hotkeys = ResourceBundle.getBundle("main.resources.hotkeys");
	private JGPoint lastClickedObject;
	private LeapGameController leapController;
	private ViewController viewController;
	//private ResourceBundle items = ResourceBundle.getBundle("main.resources.Items");


	public TDPlayerEngine(String pathToBlueprintInit, ViewController myView) throws ClassNotFoundException, IOException, ZipException {
		// super();
		loadCanvasSize(pathToBlueprintInit);
		pathToBlueprint = pathToBlueprintInit;
		initEngineComponent(xtiles * TILE_WIDTH, ytiles * TILE_HEIGHT);
		observerList = new ArrayList<Observing>();
		isFullScreen = false;
		cursorState = CursorState.None;
		leapController = new LeapGameController();
		lastClickedObject = new JGPoint();
		viewController = myView;
		stop();
	}

	private void loadCanvasSize(String pathToBlueprint) throws ClassNotFoundException, IOException, ZipException {
		DataHandler dataHandler = new DataHandler();
		GameBlueprint blueprint = dataHandler.loadBlueprint(pathToBlueprint, true);
		CanvasSchema canvasSchema = (CanvasSchema) blueprint.getMyGameMapSchemas().get(0).getAttributesMap().
				get(GameMapSchema.MY_CANVAS_ATTRIBUTES);
		Map<String, Serializable> canvasSchemaAttributeMap = canvasSchema.getAttributesMap();
		xtiles = (Integer) canvasSchemaAttributeMap.get(CanvasSchema.X_TILES);
		ytiles = (Integer) canvasSchemaAttributeMap.get(CanvasSchema.Y_TILES);
		pathToMusic = (String) blueprint.getMyGameScenario().getAttributesMap().get(GameSchema.MUSIC);
	}
	
	public String getPathToMusic() {
		return pathToMusic;
	}

	@Override
	public void initCanvas() {
		setCanvasSettings(xtiles, ytiles, TILE_WIDTH, TILE_HEIGHT, null, JGColor.black, null);
	}

	@Override
	public void initGame() {
		setFrameRate(DEFAULT_FRAME_RATE, 1);
	}

	public void initModel(){
		model = new Model(this, pathToBlueprint);
		towerName = model.getPossibleTowers().get(0);
	}

	public void speedUp() {
		setFrameRate(getFrameRate() + FRAME_RATE_DELTA, 1);
	}

	/**
	 * 
	 * @return whether the game was slowed down or not
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
	}

	public void setCursorState(CursorState newCursorState) {
		cursorState = newCursorState;
	}

	public CursorState getCursorState() {
		return cursorState;
	}

	/**
	 * Draws a rectangle around the tile at the current mouse position
	 * according to certain rules
	 */
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

	public List<String> getCurrentDescription() {
		JGPoint mousePos = getMousePos();
		if (mousePos.x < pfWidth() && mousePos.x > 0 && mousePos.y < pfHeight() && mousePos.y > 0) {
			return model.getUnitInfo(lastClickedObject.x, lastClickedObject.y);
		}
		return new ArrayList<String>();
	}

	@Override
	public void doFrame() {
		super.doFrame();

		if (leapController != null) {
			leapController.doFrame();
		}

		if (model != null) {
			checkGameEnd();
			checkMouse();
			checkKeys();
			notifyObservers();
			updateModel();
			moveObjects();
			model.checkCollisions();
		}
	}

	/*private void setAllItems(){
		for(String s: items.keySet()){
			setItem(LEFT_CLICK, items.getString(s));
		}	
	}*/

	private void checkGameEnd() {
		if (model.isGameLost()) {
			endGameDialog("Game lost :(");
		}

		if (model.isGameWon()) {
			endGameDialog("Game won!");
		}
	}

	private void endGameDialog(String ending){
		int selected = JOptionPane.showConfirmDialog(null, ending, "Game End", JOptionPane.DEFAULT_OPTION);
		if(selected == 0){
			viewController.handleEndGame();
		}
		stop();
	}
	private void updateModel() {
		try {
			model.updateGame();
		} catch (MonsterCreationFailureException e) {
			JOptionPane.showMessageDialog(null, "Critical Monster creation exception. See stack trace. Exiting program");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void checkMouse() {
		if (cursorState == CursorState.AddTower) {
			if (getMouseButton(LEFT_CLICK)) {
				model.placeTower(getMouseX(), getMouseY(), towerName);
				setCursorState(CursorState.None);
				removeObjects("TowerGhost", 0);
				clearMouseButton(LEFT_CLICK);
			}
			else {
				drawTowerGhost(towerName);
			}
		}
		else if (cursorState == CursorState.None) {
			if (getMouseButton(LEFT_CLICK)) {
				lastClickedObject.x = getMousePos().x;
				lastClickedObject.y = getMousePos().y;
				if(getKey(Integer.parseInt(hotkeys.getString("UpgradeTower")))){
					try {
						model.upgradeTower(getMouseX(), getMouseY());
					} catch (TowerCreationFailureException e) {
						e.printStackTrace();
					}

					clearKey(Integer.parseInt(hotkeys.getString("UpgradeTower")));
				}
				clearMouseButton(LEFT_CLICK);
			}
			//setAllItems();
		}
		if (getMouseButton(RIGHT_CLICK)) {
			model.checkAndRemoveTower(getMouseX(), getMouseY());
			clearMouseButton(3);
		}
	}

	/*private void setItem(int clickName, String itemName){
		if (getMouseButton(clickName) && getKey(Integer.parseInt(hotkeys.getString(itemName)))) {
			try {
				model.placeItem(itemName, getMouseX(), getMouseY());
			} catch (Exception e) {
				e.printStackTrace();
			}
			clearKey(Integer.parseInt(hotkeys.getString(itemName)));
		}
	}*/

	public void setCurrentTowerType(String currentTowerName){
		towerName = currentTowerName;
	}

	public double getHighScore(){
		return model.getScore();
	}
	/**
	 * Toggle the cursor status from AddTower to None 
	 * or vice-versa
	 */
	public void toggleAddTower() {
		if (getCursorState() == CursorState.AddTower) {
			setCursorState(CursorState.None);
			removeObjects("TowerGhost", 0);
		}
		else {
			setCursorState(CursorState.AddTower);
		}
	}

	private void checkKeys() {
		if (getKey(Integer.parseInt(hotkeys.getString("AddTower")))){
			toggleAddTower();
			clearKey(Integer.parseInt(hotkeys.getString("AddTower")));
		}

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

	private void drawTowerGhost(String imageName) {
		JGPoint mousePos = getMousePos();
		new TowerGhost(mousePos.x/tileWidth() * tileWidth(), mousePos.y/tileHeight() * tileHeight(), imageName);
	}

	@Override
	public void register(Observing o) {
		if(!observerList.contains(o)) observerList.add(o);
	}

	@Override
	public void unregister(Observing o) {
		if(observerList.contains(o)) observerList.remove(o);
	}

	@Override
	public void notifyObservers() {
		for(Observing o: observerList){
			o.update();
		}
	}

	public List<String> getPossibleTowers(){
		return model.getPossibleTowers();
	}

	public List<String> getPossibleItems(){
		return model.getPossibleItems();
	}

	public void loadBlueprintFile(String fileName) throws ClassNotFoundException, IOException, ZipException {
		model.loadGameBlueprint(fileName);
	}

	public void saveGameState(String gameName){
		try {
			model.saveGame(gameName);
		} catch (InvalidSavedGameException e) {
			e.printStackTrace();
		}
	}

	public void loadGameState(String gameName){
		try {
			model.loadSavedGame(gameName);
		} catch (InvalidSavedGameException e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getGameAttributes() {
		Map<String, String> gameStats = new HashMap<String, String>();
		gameStats.put("Score", "Score: " + model.getScore());
		gameStats.put("Lives", "Lives left: " + model.getPlayerLives());
		gameStats.put("Money", "Money: " + model.getMoney());
		gameStats.put("Time", "Game clock: " + model.getGameClock());
		return gameStats;
	}

}
