package main.java.engine;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import jgame.impl.JGEngineInterface;
import main.java.engine.factories.MonsterFactory;
import main.java.engine.factories.TowerFactory;
import main.java.engine.map.TDMap;
import main.java.engine.objects.Tower;
import main.java.engine.objects.monster.Monster;

import java.awt.Point;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Model {
    public static final String RESOURCE_PATH = "/main/resources/";

    private JGEngineInterface engine;
    private TowerFactory towerFactory;
    private MonsterFactory monsterFactory;
    private Player player;
    private double gameClock;
    private Point entrance;
    private Point exit;
    private List<Tower> towers;
    private List<Monster> monsters;

    public Model() {
        this.towerFactory = new TowerFactory(engine);
        this.monsterFactory = new MonsterFactory(engine);
        this.gameClock = 0;
    }
    
    public void addNewPlayer() {
    	this.player = new Player();
    }

    public void setEngine(JGEngineInterface engine) {
        this.engine = engine;
        this.towerFactory = new TowerFactory(engine);
    }

    public void placeTower(double x, double y) {
        towerFactory.placeTower(x, y);
    }

    /**
     * Loads a map/terrain into the engine.
     *
     * @param fileName The name of the file which contains the map information
     */
    public void loadMap(String fileName) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream(RESOURCE_PATH + fileName)));
            Gson gson = new Gson();

            TDMap map = gson.fromJson(reader, TDMap.class);
            map.loadIntoGame(engine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void resetGameClock() {
    	this.gameClock = 0;
    }
    
    public void spawnMonster(double x, double y) {
    	monsterFactory.placeMoster(x, y);
    }
    
    public void setEntrance(double x, double y) {
    	this.entrance.setLocation(x, y);
    }
    
    public void setExit(double x, double y) {
    	this.exit.setLocation(x,  y);
    }
    
    public void addScore(double score) {
    	player.addScore(score);
    }
    
    public double getScore() {
    	return player.getScore();
    }
    
    public boolean isGameLost() {
    	if (player.getLife() <= 0) return true;
    	return false;
    }
    
    public void updateGameClockByFrame() {
    	this.gameClock++;
    }
    
    public double getGameClock() {
    	return this.gameClock;
    }
    
    public int getPlayerLife() {
    	return player.getLife();
    }
    
    public int getMoney() {
    	return player.getMoney();
    }
}
