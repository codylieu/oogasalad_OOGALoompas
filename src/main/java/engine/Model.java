package main.java.engine;

import java.awt.geom.Point2D;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jgame.platform.JGEngine;
import main.java.engine.factory.TDObjectFactory;
import main.java.engine.map.TDMap;
import main.java.engine.objects.CollisionManager;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.SimpleTower;
import main.java.engine.objects.tower.Tower;
import main.java.engine.spawnschema.MonsterSpawnSchema;
import main.java.engine.spawnschema.WaveSpawnSchema;
import main.java.exceptions.engine.MonsterCreationFailureException;
import main.java.exceptions.engine.TowerCreationFailureException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import main.java.schema.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Model {
    public static final String RESOURCE_PATH = "/main/resources/";

    private static final double DEFAULT_MONEY_MULTIPLIER = 0.5;

    private JGEngine engine;
    private TDObjectFactory factory;
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
        this.engine = engine;
        this.factory = new TDObjectFactory(engine);
        collisionManager = new CollisionManager(engine);
        this.gsonParser = new Gson();
        this.gameClock = 0;
        this.currentWave = 0;
        this.allWaves = new ArrayList<WaveSpawnSchema>();
        monsters = new ArrayList<Monster>();
        towers = new Tower[engine.viewTilesX()][engine.viewTilesY()];
        gameState = new GameState();
        setEntrance(0, engine.pfHeight()/2);
        setExit(engine.pfWidth(), engine.pfHeight()/2);
        loadGameBlueprint(null); // TODO: REPLACE
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
    public boolean placeTower(double x, double y) {
        try {   
              
    		Point2D location = new Point2D.Double(x, y);
    	        int[] currentTile = getTileCoordinates(location);
    		// if tower already exists in the tile clicked, do nothing
    		if(isTowerPresent(currentTile)) return false;
    		
        	Tower newTower = factory.placeTower(location, "test-tower-1");
        	
        	if(player.getMoney() >= newTower.getCost() ) {
        	        //FIXME: Decrease money?
        		player.addMoney(-SimpleTower.DEFAULT_COST);
        		towers[currentTile[0]][currentTile[1]]  = newTower;
        		return true;
        	} else {
        		destroyTower(newTower);
        		return false;
        	}
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
        
    }

	/**
	 * Force destroy a tower
	 * @param tower
	 */
	private void destroyTower(Tower tower) {
		tower.setImage(null);
		tower.remove();
	}

    /**
     * Return a two element int array with the tile coordinates that a given point is on, for use with Tower[][]
     * @param location
     * @return the row, col of the tile on which the location is situated
     */
    private int[] getTileCoordinates(Point2D location) {
        int curXTilePos = (int) (location.getX()/engine.tileWidth());
        int curYTilePos = (int) (location.getY()/engine.tileHeight());
        return new int[]{curXTilePos, curYTilePos};
    }
    
    /**
     * Check if there's a tower present at the specified coordinates
     * @param coordinates
     * @return true if there is a tower
     */
    private boolean isTowerPresent(int[] coordinates) {
    	return towers[coordinates[0]][coordinates[1]]!=null;
    }
    
    /**
     * Check if there's a tower present at the specified coordinates
     * This is mainly for the view to do a quick check
     * @param x
     * @param y
     * @return true if there is a tower
     */
    public boolean isTowerPresent(double x, double y) {
    	return isTowerPresent(getTileCoordinates(new Point2D.Double(x, y)));
    }
    
    /**
     * Check if the current location contains any tower. If yes, remove it. If no, do nothing 
     * @param x
     * @param y
     */
    public void checkAndRemoveTower(int x, int y) {
    	int[] coordinates = getTileCoordinates(new Point2D.Double(x, y));
    	if (isTowerPresent(coordinates)){
    		int xtile = coordinates[0];
    		int ytile = coordinates[1];
    		player.addMoney(DEFAULT_MONEY_MULTIPLIER * towers[xtile][ytile].getCost());
    		towers[xtile][ytile].remove();
    		towers[xtile][ytile] = null;
    	}
    }
    
    /**
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
     * Loads the game schemas from GameBlueprint and sets the appropriate state
     *
     * @param bp
     */
    public void loadGameBlueprint(GameBlueprint bp) {
//    	Map<String, String> gameAttributes = bp.getMyGameSchema().getAttributes();
//    	player = new Player(gameAttributes.get(GameSchema.MONEY), gameAttributes.get(GameSchema.LIVES));

//      GameSchema testGameSchema = new GameSchema();
//      Map<String, String> gameSchemaMap = testGameSchema.getAttributesMap();
//      player = new Player(gameSchemaMap.get(GameSchema.GOLD), gameSchemaMap.get(GameSchema.LIVES));
        player = new Player();

        List<TDObjectSchema> tdObjectSchemas = new ArrayList<>();

        SimpleTowerSchema testTowerSchema = new SimpleTowerSchema();
        testTowerSchema.addAttribute(Tower.NAME, "test-tower-1");
        testTowerSchema.addAttribute(Tower.COST, "10");
        testTowerSchema.addAttribute(Tower.IMAGE, "SimpleTower");
        tdObjectSchemas.add(testTowerSchema);

        SimpleMonsterSchema testMonsterSchema = new SimpleMonsterSchema();
        testMonsterSchema.addAttribute(Monster.NAME, "test-monster-1");
        testMonsterSchema.addAttribute(Monster.MONEY_VALUE, "200");
        tdObjectSchemas.add(testMonsterSchema);

        factory.loadSchemas(tdObjectSchemas);
        
        WaveSpawnSchema testWaveSchema = new WaveSpawnSchema();
        MonsterSpawnSchema testMonsterSpawnSchema1 = new MonsterSpawnSchema("", testMonsterSchema, 1);
        MonsterSpawnSchema testMonsterSpawnSchema2 = new MonsterSpawnSchema("", testMonsterSchema, 2);
        MonsterSpawnSchema testMonsterSpawnSchema3 = new MonsterSpawnSchema("", testMonsterSchema, 3);
        testWaveSchema.addMonsterSchema(testMonsterSpawnSchema1);
        testWaveSchema.addMonsterSchema(testMonsterSpawnSchema2);
        testWaveSchema.addMonsterSchema(testMonsterSpawnSchema3);

        allWaves.add(testWaveSchema);
    }
    
    /**
     * Creates a wave of simple monsters for sans-factory testing ...
     * @param m1
     * @param swarmSize
     * @return
     */
    private WaveSpawnSchema createTestWave (SimpleMonsterSchema m1, int swarmSize) {
        MonsterSpawnSchema mschema = new MonsterSpawnSchema("SimpleMonster", m1, swarmSize);
        WaveSpawnSchema wschema = new WaveSpawnSchema();
        wschema.addMonsterSchema(mschema);
        return wschema;
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
    	if (getPlayerLife() <= 0) return true;
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
    
    private boolean isGameWon() {
    	if(currentWave >= allWaves.size()){
    		return true;
    	}
    	return false;
    }
    
    /**
     * Spawns the next wave in the list of all waves.
     * Currently rotates through all waves indefinitely.
     * @throws MonsterCreationFailureException
     */
    private void spawnNextWave() throws MonsterCreationFailureException {
        for (MonsterSpawnSchema spawnSchema : allWaves.get(currentWave).getMonsterSpawnSchemas()) {
            for (int i = 0; i < spawnSchema.getSwarmSize(); i++) {
                Monster newlyAdded = factory.placeMonster(entrance, exit,
                        spawnSchema.getMonsterSchema().getAttributesMap().get(TDObject.NAME));
                monsters.add(newlyAdded);
            }
            if(++currentWave >= allWaves.size()) {
                currentWave = 0;
            }
        }
        
        // TODO: check if gameWon() ?
    }

    /**
     *  Spawns a new wave 
     * @throws MonsterCreationFailureException 
     */
    private void doSpawnActivity() throws MonsterCreationFailureException {
    	
     //at determined intervals:
     //   if (gameClock % 100 == 0)
     //or if previous wave defeated:
        if(monsters.isEmpty())
            spawnNextWave();
        
    }
    
	/**
	 * The model's "doFrame()" method that updates all state, spawn monsters,
	 * etc.
	 * @throws MonsterCreationFailureException 
	 */
	public void updateGame() throws MonsterCreationFailureException {
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

	/**
	 * Returns the coordinate of the monster nearest to the coordinate passed in
	 * @param towerCoor 
	 * @return coordinates of the nearest monster in the form of a Point2D object
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
    
	/**
	 * Check all collisions specified by the CollisionManager
	 */
    public void checkCollisions() {
    	collisionManager.checkAllCollisions();
    }
    
    /**
     * Upgrade the tower at the specified coordinates
     * @param x
     * @param y
     * @return
     * @throws TowerCreationFailureException
     */
    public boolean upgradeTower(double x, double y) throws TowerCreationFailureException {
    	int[] coordinates = getTileCoordinates(new Point2D.Double(x, y));
    	if (isTowerPresent(coordinates)){
    		int xtile = coordinates[0];
    		int ytile = coordinates[1];
    		towers[xtile][ytile].remove();
    		Tower newTower = factory.placeTower(new Point2D.Double(x, y), "test tower 2");
    		System.out.println(newTower.x);
    		towers[xtile][ytile] = newTower;
    		return true;
    	}
    	return false;
    }
}
