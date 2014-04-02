package main.java.engine;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import jgame.impl.JGEngineInterface;
import main.java.engine.factories.TowerFactory;
import main.java.engine.map.TDMap;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.Tower;
import main.java.engine.spawnschema.MonsterSpawnSchema;
import main.java.engine.spawnschema.WaveSpawnSchema;
import main.java.exceptions.engine.InvalidTowerCreationParameters;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Model {
    public static final String RESOURCE_PATH = "/main/resources/";

    private JGEngineInterface engine;
    private TowerFactory towerFactory;
//    private MonsterFactory monsterFactory;
    private Player player;
    private double gameClock;
    private Point entrance;
    private Point exit;
    private List<Tower> towers;
    private List<Monster> monsters;
    private Gson gsonParser;
    private int currentWave;
    private List<WaveSpawnSchema> allWaves;

    public Model() {
        this.towerFactory = new TowerFactory(engine);
//        this.monsterFactory = new MonsterFactory(engine);
        this.gsonParser = new Gson();
        this.gameClock = 0;
        this.currentWave = 0;
        this.allWaves = new ArrayList<WaveSpawnSchema>();
        monsters = new ArrayList<Monster>();
        towers = new ArrayList<Tower>();
    }
    
    public void addNewPlayer() {
    	this.player = new Player();
    }

    public void setEngine(JGEngineInterface engine) {
        this.engine = engine;
        this.towerFactory = new TowerFactory(engine);
    }

    public void placeTower(double x, double y) {
        try {
			towers.add(towerFactory.placeSimpleTower("PunyTower", x, y));
		} catch (InvalidTowerCreationParameters e) {
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
    
    public void resetGameClock() {
    	this.gameClock = 0;
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
    	
//        if (gameClock % 100 == 0)
    	if (gameClock == 100)
        	spawnNextWave();
        
    }
    
    /**
     *  The model's "doFrame()" method that updates all state, spawn monsters, etc.
     */
	public void updateGame() {
		updateGameClockByFrame();
		doSpawnActivity();
        doTowerFiring();
        System.out.println(towers.size());
	}
    
	private void doTowerFiring() {
		if (!monsters.isEmpty()) {
			for (Tower t : towers) {
				Point2D monsterCoor = getNearestMonsterCoordinate(new Point2D.Double(
						t.x, t.y));
//				System.out.println(t.checkAndfireProjectile(monsterCoor));
				t.checkAndfireProjectile(monsterCoor);
			}
		}

	}

	private Point2D getNearestMonsterCoordinate(Point2D towerCoor) {
		double minDistance = Double.MAX_VALUE;
		Point2D closestMonsterCoor = null;
		for(Monster m : monsters) {
			if(m.getCurrentCoor().distance(towerCoor) < minDistance) {
				minDistance = m.getCurrentCoor().distance(towerCoor);
				closestMonsterCoor = m.getCurrentCoor();
			}
 		}
//		System.out.println(minDistance);
		return closestMonsterCoor;
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
    	MonsterSpawnSchema mschema = new MonsterSpawnSchema("SimpleMonster", 1);
    	WaveSpawnSchema wschema = new WaveSpawnSchema();
    	wschema.addMonsterSchema(mschema);
    	addWaveToGame(wschema);
    }
}
