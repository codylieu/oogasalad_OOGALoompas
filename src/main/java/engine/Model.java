package main.java.engine;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jgame.platform.JGEngine;
import main.java.engine.factories.TowerFactory;
import main.java.engine.map.TDMap;
import main.java.engine.objects.CollisionManager;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.Tower;
import main.java.engine.spawnschema.MonsterSpawnSchema;
import main.java.engine.spawnschema.WaveSpawnSchema;
import main.java.exceptions.engine.InvalidTowerCreationParametersException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Model {
    public static final String RESOURCE_PATH = "/main/resources/";

    private JGEngine engine;
    private TowerFactory towerFactory;
//    private MonsterFactory monsterFactory;
    private Player player;
    private double gameClock;
    private List<Tower> towers;
    private List<Monster> monsters;
    private Gson gsonParser;
    private int currentWave;
    private List<WaveSpawnSchema> allWaves;
    private CollisionManager collisionManager;
    private Point2D entrance;
    private Point2D exit;

    public Model(JGEngine engine) {
//        this.monsterFactory = new MonsterFactory(engine);
        this.engine = engine;
        this.towerFactory = new TowerFactory(engine);
        collisionManager = new CollisionManager(engine);
        this.gsonParser = new Gson();
        this.gameClock = 0;
        this.currentWave = 0;
        this.allWaves = new ArrayList<WaveSpawnSchema>();
        monsters = new ArrayList<Monster>();
        towers = new ArrayList<Tower>();

    }
    
    /**
     * Add a new player to the engine
     */
    public void addNewPlayer() {
    	this.player = new Player();
    }

    /**
     * Add a tower at the specified location
     * @param x	x coordinate of the tower
     * @param y	y coordinate of the tower
     */
    public void placeTower(double x, double y) {
        try {
        	Point2D location = new Point2D.Double(x, y);
			towers.add(towerFactory.placeTower("PunyTower", location));
			
		} catch (InvalidTowerCreationParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Loads a map/terrain into the engine.
     *
     * @param fileName The name of the file which contains the map information
     */
    public void loadMap(String fileName) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream(RESOURCE_PATH + fileName)));

            TDMap map = gsonParser.fromJson(reader, TDMap.class);
            map.loadIntoGame(engine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Reset the game clock
     */
    public void resetGameClock() {
    	this.gameClock = 0;
    }
    
    /**
     * Set the monsters' entrance
     * @param x
     * @param y
     */
    public void setEntrance(double x, double y) {
    	this.entrance = new Point2D.Double(x, y);
    }
    
    /**
     * Set the monsters' exit(destination)
     * @param x
     * @param y
     */
    public void setExit(double x, double y) {
    	this.exit = new Point2D.Double(x, y);
    }
    
    public void addScore(double score) {
    	player.addScore(score);
    }
    
    /**
     * Get the score of the player
     * @return current score
     */
    public double getScore() {
    	return player.getScore();
    }
    
    /**
     * Check whether the game is lost
     * @return true if game is lost
     */
    public boolean isGameLost() {
    	if (player.getLife() <= 0) return true;
    	return false;
    }
    
    private void updateGameClockByFrame() {
    	this.gameClock++;
    }
    
    /**
     * Get the game clock
     * @return current game clock
     */
    public double getGameClock() {
    	return this.gameClock;
    }
    
    /**
     * Get the number of remaining lives of the player
     * @return number of lives left
     */
    public int getPlayerLife() {
    	return player.getLife();
    }
    
    /**
     * Get the amount of money obtained by the player
     * @return current amount of money
     */
    public int getMoney() {
    	return player.getMoney();
    }
    /**
     * Loads a wave spawn schema into the model
     *
     * @param fileName The name of the JSON file containing wave spawn schema info
     */
    public void loadWaveSpawnSchema(String fileName) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream(RESOURCE_PATH + fileName)));
            WaveSpawnSchema newWave = gsonParser.fromJson(reader, WaveSpawnSchema.class);
            
            addWaveToGame(newWave); 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private boolean gameWon() {
    	if(currentWave >= allWaves.size()){
    		return true;
    	}
    	return false;
    }
    

    private void spawnNextWave() {
    	monsters.addAll(allWaves.get(currentWave).spawn());
    	//currentWave++;
    	//TODO: check if gameWon() ?
    }
    
    /**
     *  Spawns a new wave at determined intervals
     */
    private void doSpawnActivity() {
    	
        if (gameClock % 100 == 0)
        	spawnNextWave();
        
    }
    
	/**
	 * The model's "doFrame()" method that updates all state, spawn monsters,
	 * etc.
	 */
	public void updateGame() {
		updateGameClockByFrame();
		doSpawnActivity();
		doTowerFiring();
		removeDeadMonsters();
	}

	/**
	 * Clean up dead monsters from monsters list and JGEngine display.
	 */
	private void removeDeadMonsters() {
		Iterator<Monster> monsterIter = monsters.iterator();
		while(monsterIter.hasNext()) {
			Monster currentMonster = monsterIter.next();
			if (currentMonster.isDead()) {
				monsterIter.remove();
				addMoney(currentMonster.getMoneyValue());
				currentMonster.remove();
			}
		}
	}

	private void addMoney(double moneyValue) {
		player.addMoney(moneyValue);
	}

	/**
	 * Call this to make each of the Towers execute firing logic
	 */
	private void doTowerFiring() {
		if (!monsters.isEmpty()) {
			for (Tower t : towers) {
				Point2D monsterCoor = getNearestMonsterCoordinate(new Point2D.Double(
						t.x, t.y));
				t.checkAndfireProjectile(monsterCoor);
			}
		}

	}

	/**
	 * Returns the coordinate of the monster nearest to the coordinate passed in
	 * @param towerCoor 
	 * @return
	 */
	private Point2D getNearestMonsterCoordinate(Point2D towerCoor) {
		double minDistance = Double.MAX_VALUE;
		Point2D closestMonsterCoor = null;
		for(Monster m : monsters) {
			if(m.getCurrentCoor().distance(towerCoor) < minDistance) {
				minDistance = m.getCurrentCoor().distance(towerCoor);
				closestMonsterCoor = centerCoordinate(m);
			}
 		}
		return closestMonsterCoor;
	}

	/**
	 * Returns the center of the object for targeting 
	 * @param current object coordinate
	 * @return the center of the objects image according to the imageBBox
	 */
	private Point2D centerCoordinate(Monster m) {
		return new Point2D.Double(m.getCurrentCoor().getX()+m.getImageBBoxConst().width/2,
				m.getCurrentCoor().getY()+m.getImageBBoxConst().height/2);
	}

	/**
	 * Add a wave to the game plan logic
	 * 
	 * @param waveSchema
	 */
    public void addWaveToGame(WaveSpawnSchema waveSchema) {
    	allWaves.add(waveSchema);
    }
    
    
    /**
     * Test method
     */
    public void setTemporaryWaveSchema() {
    	MonsterSpawnSchema mschema = new MonsterSpawnSchema("SimpleMonster", 1, entrance, exit);
    	WaveSpawnSchema wschema = new WaveSpawnSchema();
    	wschema.addMonsterSchema(mschema);
    	addWaveToGame(wschema);
    }

	/**
	 * Check all collisions specified by the CollisionManager
	 */
	public void checkCollisions() {
		collisionManager.checkAllCollisions();
	}
}
