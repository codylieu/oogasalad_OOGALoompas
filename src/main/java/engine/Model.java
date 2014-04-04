package main.java.engine;

import java.awt.geom.Point2D;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jgame.platform.JGEngine;
import main.java.data.datahandler.DataBundle;
import main.java.engine.factories.TowerFactory;
import main.java.engine.map.TDMap;
import main.java.engine.objects.CollisionManager;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.SimpleTower;
import main.java.engine.objects.tower.Tower;
import main.java.engine.spawnschema.MonsterSpawnSchema;
import main.java.engine.spawnschema.WaveSpawnSchema;
import main.java.exceptions.engine.InvalidTowerCreationParametersException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import main.java.schema.GameBlueprint;
import main.java.schema.TowerSchema;

public class Model {
    public static final String RESOURCE_PATH = "/main/resources/";

    private JGEngine engine;
    private TowerFactory towerFactory;
//    private MonsterFactory monsterFactory;
    private Player player;
    private double gameClock;
    private Tower[][] towers;
    private List<Monster> monsters;
    private Gson gsonParser;
    private int currentWave;
    private List<WaveSpawnSchema> allWaves;
    private CollisionManager collisionManager;
    private Point2D entrance;
    private Point2D exit;
    private GameState gameState;

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
        towers = new Tower[engine.viewTilesX()][engine.viewTilesY()];
        gameState = new GameState();
        setEntrance(0, engine.pfHeight()/2);
        setExit(engine.pfWidth()/2, engine.pfHeight()/2);
    }
    
    /**
     * Add a new player to the engine
     */
    public void addNewPlayer() {
    	this.player = new Player();
    }

    /**
     * Add a tower at the specified location. If tower already exists in that cell, do nothing.
     * @param x	x coordinate of the tower
     * @param y	y coordinate of the tower
     */
    public void placeTower(double x, double y) {
        try {   
              
    		Point2D location = new Point2D.Double(x, y);
    	        int[] currentTile = getTileCoordinates(location);
    		// if tower already exists in the tile clicked, do nothing
    		if(towers[currentTile[0]][currentTile[1]] != null) return;
    		
        	Tower newTower = towerFactory.placeTower(location, "test tower 1");
        	
        	if(player.getMoney() >= newTower.getCost() ) {
        	        //FIXME: Decrease money?
        		player.addMoney(-SimpleTower.DEFAULT_COST);
        		towers[currentTile[0]][currentTile[1]]  = newTower;
    	
        	} else {
        		newTower.setImage(null);
        		newTower.remove();
        	}
        	
		} catch (InvalidTowerCreationParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    /**
     * Return a two element int array with the tile coordinates that a given point is on, for use with Tower[][]
     * @param location
     * @return the row, col of the tile on which the location is situated
     */
    public int[] getTileCoordinates(Point2D location) {
        int curXTilePos = (int) (location.getX()/engine.tileWidth());
        int curYTilePos = (int) (location.getY()/engine.tileHeight());
        return new int[]{curXTilePos, curYTilePos};
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
     * Loads the game schemas from json and passes them to the appropriate factories.
     *
     * @param fileName Name of the json file containing the schemas
     */
    public void loadSchemas(String fileName) {
    	
    	//load wavespawnschemas
    	MonsterSpawnSchema mschema = new MonsterSpawnSchema("SimpleMonster", 1, entrance, exit);
    	WaveSpawnSchema wschema = new WaveSpawnSchema();
    	wschema.addMonsterSchema(mschema);
    	addWaveToGame(wschema);
    	//
    	
        TowerSchema t1 = new TowerSchema();
        t1.setMyName("test tower 1");
        t1.setMyDamage(10);
        t1.setMyRange(200);
        t1.setMyCost(SimpleTower.DEFAULT_COST);
        t1.setMyImage("SimpleTower");

        TowerSchema t2 = new TowerSchema();
        t2.setMyName("test tower 2");
        t2.setMyDamage(20);
        t2.setMyRange(200);
        t2.setMyCost(SimpleTower.DEFAULT_COST);
        t2.setMyImage("SimpleTower");

        GameBlueprint gb = new GameBlueprint();
        List<TowerSchema> towerSchemas = new ArrayList<TowerSchema>();
        towerSchemas.add(t1);
        towerSchemas.add(t2);
        gb.setMyTowerSchemas(towerSchemas);
        DataBundle b = new DataBundle();
        b.setBlueprint(gb);

        try {
            DataBundle data = b;
            GameBlueprint blueprint = b.getBlueprint();
            List<TowerSchema> schemas = blueprint.getMyTowerSchemas();
            towerFactory.loadSchemas(schemas);
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
		gameState.updateGameStates(monsters, towers, entrance, exit, currentWave, allWaves, gameClock, 
				player.getMoney(), player.getLife(), player.getScore());
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
    private void doTowerFiring () {
        if (!monsters.isEmpty()) {
            for (Tower[] towerRow : towers) {
                for (Tower t : towerRow) {
                    if (t != null) {
                        Point2D monsterCoor =
                                getNearestMonsterCoordinate(new Point2D.Double(t.x, t.y));
                        t.checkAndfireProjectile(monsterCoor);
                    }
                }
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
	 * @param m object coordinate
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
    
    public void destroyTower(double x, double y) {
    	
    }
    
    
    /**
     * Test method
     */
    public void setTemporaryWaveSchema() {
    	MonsterSpawnSchema mschema = new MonsterSpawnSchema("SimpleMonster", 2, entrance, exit);
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
