package main.java.engine;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jgame.platform.JGEngine;
import main.java.data.datahandler.DataHandler;
import main.java.engine.factory.TDObjectFactory;
import main.java.engine.map.TDMap;
import main.java.engine.objects.CollisionManager;
import main.java.engine.objects.Exit;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.ITower;
import main.java.engine.objects.tower.TowerBehaviors;
import main.java.exceptions.engine.MonsterCreationFailureException;
import main.java.exceptions.engine.TowerCreationFailureException;
import main.java.schema.GameBlueprint;
import main.java.schema.GameSchema;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.MonsterSpawnSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.WaveSpawnSchema;


public class Model {
    private static final double DEFAULT_MONEY_MULTIPLIER = 0.5;
    public static final String RESOURCE_PATH = "/main/resources/";

    private JGEngine engine;
    private TDObjectFactory factory;
    private Player player;
    private double gameClock;
    private ITower[][] towers;
    private List<Monster> monsters;
    private CollisionManager collisionManager;
    private GameState gameState;
    private DataHandler dataHandler;
    private LevelManager levelManager;
    private EnvironmentKnowledge environ;

    public Model (JGEngine engine) {
        this.engine = engine;
        defineAllStaticImages();
        this.factory = new TDObjectFactory(engine);
        collisionManager = new CollisionManager(engine);

        levelManager = new LevelManager(factory);
        // TODO: Code entrance/exit logic into wave or monster spawn schema
        levelManager.setEntrance(0, engine.pfHeight() / 2);
        levelManager.setExit(engine.pfWidth() / 2, engine.pfHeight() / 2);

        this.gameClock = 0;
        monsters = new ArrayList<Monster>();
        towers = new ITower[engine.viewTilesX()][engine.viewTilesY()];
        gameState = new GameState();

        try {
            loadGameBlueprint(null);// TODO: REPLACE
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataHandler = new DataHandler();

        addNewPlayer();

    }

    private void defineAllStaticImages () {
        // TODO: remove this method, make this a part of schemas
        engine.defineImage(Exit.NAME, "-", 1, RESOURCE_PATH + Exit.IMAGE_NAME, "-");
        // make bullet image dynamic
        engine.defineImage("red_bullet", "-", 1, RESOURCE_PATH + "red_bullet.png", "-");
    }

    /**
     * Add a new player to the engine
     */
    public void addNewPlayer () {
        this.player = new Player();
        levelManager.registerPlayer(player);

        environ = new EnvironmentKnowledge(monsters, player, towers);
    }

    /**
     * Add a tower at the specified location. If tower already exists in that cell, do nothing.
     * 
     * @param x x coordinate of the tower
     * @param y y coordinate of the tower
     * @param towerName Type tower to be placed
     */
    public boolean placeTower (double x, double y, String towerName) {
        try {
            Point2D location = new Point2D.Double(x, y);
            int[] currentTile = getTileCoordinates(location);

            // if tower already exists in the tile clicked, do nothing
            if (isTowerPresent(currentTile)) { return false; }

            ITower newTower = factory.placeTower(location, towerName);

            if (player.getMoney() >= newTower.getCost()) {
                // FIXME: Decrease money?
                player.addMoney(-newTower.getCost());
                towers[currentTile[0]][currentTile[1]] = newTower;
                return true;
            }
            else {
                newTower.remove();
                return false;
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Return a two element int array with the tile coordinates that a given point is on, for use
     * with Tower[][]
     * 
     * @param location
     * @return the row, col of the tile on which the location is situated
     */
    private int[] getTileCoordinates (Point2D location) {
        int curXTilePos = (int) (location.getX() / engine.tileWidth());
        int curYTilePos = (int) (location.getY() / engine.tileHeight());

        return new int[] { curXTilePos, curYTilePos };
    }

    /**
     * Check if there's a tower present at the specified coordinates
     * 
     * @param coordinates
     * @return true if there is a tower
     */
    private boolean isTowerPresent (int[] coordinates) {
        return towers[coordinates[0]][coordinates[1]] != null;
    }

    /**
     * Check if there's a tower present at the specified coordinates
     * This is mainly for the view to do a quick check
     * 
     * @param x
     * @param y
     * @return true if there is a tower
     */
    public boolean isTowerPresent (double x, double y) {
        return isTowerPresent(getTileCoordinates(new Point2D.Double(x, y)));
    }

    /**
     * Check if the current location contains any tower. If yes, remove it. If no, do nothing
     * 
     * @param x
     * @param y
     */
    public void checkAndRemoveTower (double x, double y) {
        int[] coordinates = getTileCoordinates(new Point2D.Double(x, y));
        if (isTowerPresent(coordinates)) {
            int xtile = coordinates[0];
            int ytile = coordinates[1];
            player.addMoney(DEFAULT_MONEY_MULTIPLIER * towers[xtile][ytile].getCost());
            towers[xtile][ytile].remove();
            towers[xtile][ytile] = null;
        }
    }

    // TODO: use this instead of other one, will change -jordan
    public void loadMapTest (String fileName) {
        try {
            TDMap tdMap = new TDMap();
            tdMap.loadMapIntoGame(engine, fileName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialize and load into the engine the GameBlueprint obtained from the file path
     * 
     * @param filePath File path of the blueprint to be loaded
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public void loadGameBlueprint(String filePath) throws ClassNotFoundException, IOException {
        GameBlueprint bp = null;
        // TODO: load from datahandler
//        try {
//            bp = dataHandler.loadBlueprint(filePath);
//        } catch (ZipException e) {
//            e.printStackTrace();
//        }

        // TODO: use the actual game blueprint (aka bp)
        GameBlueprint testBP = createTestBlueprint();

        // init player
        GameSchema gameSchema = testBP.getMyGameScenario();
        Map<String, Serializable> gameSchemaAttributeMap = gameSchema.getAttributesMap();
        this.player = new Player((Integer) gameSchemaAttributeMap.get(GameSchema.MONEY),

                                 (Integer) gameSchemaAttributeMap.get(GameSchema.LIVES));

        // init factory objects
        List<TDObjectSchema> tdObjectSchemas = testBP.getMyTDObjectSchemas();
        factory.loadTDObjectSchemas(tdObjectSchemas);

        // init levels
        for (WaveSpawnSchema wave : testBP.getMyLevelSchemas()) {
            levelManager.addNewWave(wave);
        }
    }

    /**
     * Reset the game clock
     */
    public void resetGameClock () {
        this.gameClock = 0;
    }

    public void addScore (double score) {
        player.addScore(score);
    }

    /**
     * Get the score of the player
     * 
     * @return player's current score
     */
    public double getScore () {
        return player.getScore();
    }

    /**
     * Check whether the game is lost
     * 
     * @return true if game is lost
     */
    public boolean isGameLost () {
        return getPlayerLives() <= 0;
    }

    private void updateGameClockByFrame () {
        this.gameClock++;
    }

    /**
     * Get the game clock
     * 
     * @return current game clock
     */
    public double getGameClock () {
        return this.gameClock;
    }

    /**
     * Get the number of remaining lives of the player
     * 
     * @return number of lives left
     */
    public int getPlayerLives () {
        return player.getLivesRemaining();
    }

    /**
     * Get the amount of money obtained by the player
     * 
     * @return current amount of money
     */
    public int getMoney () {
        return player.getMoney();
    }

    private boolean isGameWon () {
        return levelManager.checkAllWavesFinished();
    }

    /**
     * Spawns a new wave
     * 
     * @throws MonsterCreationFailureException
     */
    public void doSpawnActivity () throws MonsterCreationFailureException {
        // at determined intervals:
        // if (gameClock % 100 == 0)
        // or if previous wave defeated:
        if (monsters.isEmpty())
            monsters.addAll(levelManager.spawnNextWave());

    }

    /**
     * The model's "doFrame()" method that updates all state, spawn monsters,
     * etc.
     * 
     * @throws MonsterCreationFailureException
     */
    public void updateGame () throws MonsterCreationFailureException {
        updateGameClockByFrame();
        doSpawnActivity();
        doTowerBehaviors();
        removeDeadMonsters();
        gameState
                .updateGameStates(monsters, towers, levelManager.getCurrentWave(),
                                  levelManager.getAllWaves(), gameClock,
                                  player.getMoney(), player.getLivesRemaining(), player.getScore());
    }

    /**
     * Clean up dead monsters from monsters list and JGEngine display.
     */
    private void removeDeadMonsters () {
        Iterator<Monster> monsterIter = monsters.iterator();
        while (monsterIter.hasNext()) {
            Monster currentMonster = monsterIter.next();
            if (currentMonster.isDead()) {
                monsterIter.remove();
                addMoney(currentMonster.getMoneyValue());
                currentMonster.remove();
            }
        }
    }

    private void addMoney (double moneyValue) {
        player.addMoney(moneyValue);
    }

    /**
     * Call this to do the individual behavior of each Tower
     */
    private void doTowerBehaviors () {

        for (ITower[] towerRow : towers) {
            for (ITower t : towerRow) {
                if (t != null) {
                    t.callTowerActions(environ, t.getLauncher());
                }
            }
        }
    }

    /**
     * Check all collisions specified by the CollisionManager
     */
    public void checkCollisions () {
        collisionManager.checkAllCollisions();
    }

    /**
     * Upgrade the tower at the specified coordinates
     * 
     * @param x
     * @param y
     * @return
     * @throws TowerCreationFailureException
     */
    public boolean upgradeTower (double x, double y) throws TowerCreationFailureException {
        int[] coordinates = getTileCoordinates(new Point2D.Double(x, y));
        if (isTowerPresent(coordinates)) {
            int xtile = coordinates[0];
            int ytile = coordinates[1];
            towers[xtile][ytile].remove();
            ITower newTower = factory.placeTower(new Point2D.Double(x, y), "test tower 2");
            // System.out.println(newTower.x);
            towers[xtile][ytile] = newTower;
            return true;
        }
        return false;
    }

    /**
     * Decrease player's lives by one.
     */
    public void decrementLives () {
        player.decrementLives();
    }

    /**
     * TEST METHOD - Create a test blueprint for testing purposes
     * TODO: remove when we no longer need this
     * 
     * @return test blueprint
     */
    private GameBlueprint createTestBlueprint () {
        GameBlueprint testBlueprint = new GameBlueprint();

        // Populate TDObjects
        List<TDObjectSchema> testTDObjectSchema = new ArrayList<>();

        // Create test towers
        TowerSchema testTowerOne = new TowerSchema();
        testTowerOne.addAttribute(TowerSchema.NAME, "test-tower-1");
        testTowerOne.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
        Collection<TowerBehaviors> towerBehaviors = new ArrayList<TowerBehaviors>();
        towerBehaviors.add(TowerBehaviors.MONEY_FARMING);
        testTowerOne.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors);
        testTowerOne.addAttribute(TowerSchema.COST, (double) 10);
        testTDObjectSchema.add(testTowerOne);
        
        TowerSchema testTowerTwo = new TowerSchema();
        testTowerTwo.addAttribute(TowerSchema.NAME, "test-tower-2");
        testTowerTwo.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
        Collection<TowerBehaviors> towerBehaviors2 = new ArrayList<TowerBehaviors>();
        towerBehaviors2.add(TowerBehaviors.SHOOTING);
        testTowerTwo.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors2);
        testTowerTwo.addAttribute(TowerSchema.COST, (double) 10);
        testTDObjectSchema.add(testTowerTwo);

        // Create test money tower

        // Create test monsters
        SimpleMonsterSchema testMonsterOne = new SimpleMonsterSchema();
        testMonsterOne.addAttribute(MonsterSchema.NAME, "test-monster-1");
        testMonsterOne.addAttribute(TDObjectSchema.IMAGE_NAME, "monster.png");
        testMonsterOne.addAttribute(MonsterSchema.REWARD, (double) 200);
        testTDObjectSchema.add(testMonsterOne);

        testBlueprint.setMyTDObjectSchemas(testTDObjectSchema);

        // Create test game schemas
        GameSchema testGameSchema = new GameSchema();
        testGameSchema.addAttribute(GameSchema.LIVES, 3);
        testGameSchema.addAttribute(GameSchema.MONEY, 500);

        testBlueprint.setMyGameScenario(testGameSchema);

        // Create wave schemas
        List<WaveSpawnSchema> testWaves = new ArrayList<WaveSpawnSchema>();
        MonsterSpawnSchema testMonsterSpawnSchemaOne = new MonsterSpawnSchema(testMonsterOne, 1);
        WaveSpawnSchema testWaveSpawnSchemaOne = new WaveSpawnSchema();
        testWaveSpawnSchemaOne.addMonsterSchema(testMonsterSpawnSchemaOne);
        testWaves.add(testWaveSpawnSchemaOne);

        MonsterSpawnSchema testMonsterSpawnSchemaTwo = new MonsterSpawnSchema(testMonsterOne, 2);
        WaveSpawnSchema testWaveSpawnSchemaTwo = new WaveSpawnSchema();
        testWaveSpawnSchemaTwo.addMonsterSchema(testMonsterSpawnSchemaTwo);
        testWaves.add(testWaveSpawnSchemaTwo);

        MonsterSpawnSchema testMonsterSpawnSchemaThree = new MonsterSpawnSchema(testMonsterOne, 10);
        WaveSpawnSchema testWaveSpawnSchemaThree = new WaveSpawnSchema();
        testWaveSpawnSchemaThree.addMonsterSchema(testMonsterSpawnSchemaThree);
        testWaves.add(testWaveSpawnSchemaThree);

        testBlueprint.setMyLevelSchemas(testWaves);

        return testBlueprint;
    }

    public List<String> getPossibleTowers () {
        return factory.getPossibleTowersNames();
    }
}
