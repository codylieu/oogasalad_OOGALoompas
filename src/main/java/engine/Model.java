package main.java.engine;

import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;
import jgame.platform.JGEngine;
import main.java.data.DataHandler;
import main.java.engine.factory.TDObjectFactory;
import main.java.engine.map.TDMap;
import main.java.engine.objects.CollisionManager;
import main.java.engine.objects.Exit;
import main.java.engine.objects.item.RowBomb;
import main.java.engine.objects.item.TDItem;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.ITower;
import main.java.engine.objects.tower.SimpleTower;
import main.java.engine.objects.tower.TowerBehaviors;
import main.java.exceptions.engine.InvalidSavedGameException;
import main.java.exceptions.engine.MonsterCreationFailureException;
import main.java.exceptions.engine.TowerCreationFailureException;
import main.java.schema.GameBlueprint;
import main.java.schema.GameSchema;
import main.java.schema.map.GameMapSchema;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.MonsterSpawnSchema;
import main.java.schema.tdobjects.items.AnnihilatorItemSchema;
import main.java.schema.tdobjects.items.AreaBombItemSchema;
import main.java.schema.tdobjects.items.InstantFreezeItemSchema;
import main.java.schema.tdobjects.items.LifeSaverItemSchema;
import main.java.schema.tdobjects.items.RowBombItemSchema;
import main.java.schema.tdobjects.monsters.SimpleMonsterSchema;
import main.java.schema.tdobjects.ItemSchema;
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
    private List<TDItem> items;
    
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
        items = new ArrayList<TDItem>();

        try {
            loadGameBlueprint(null);// TODO: REPLACE
        }
        catch (Exception e) {
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
        engine.defineImage("blue_bullet", "-", 1, RESOURCE_PATH + "blue_bullet.png", "-");
        engine.defineImage("row_bomb", "-", TDItem.ITEM_CID, RESOURCE_PATH + "Big_Ben.png", "-");
        engine.defineImage("fire", "-", 0, RESOURCE_PATH + "fire.png", "-");
        engine.defineImage("ice", "-", 0, RESOURCE_PATH + "ice.png", "-");
    }

    /**
     * Add a new player to the engine
     */
    public void addNewPlayer () {
        this.player = new Player();
        levelManager.registerPlayer(player);

        environ = new EnvironmentKnowledge(monsters, player, towers, levelManager.getExit());
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
                player.changeMoney(-newTower.getCost());
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
     * Get the information of the TDObject, if any, 
     * at the specified coordinates
     * 
     * @param x
     * @param y
     * @return The information that we want to display to the player
     */
    public List<String> getUnitInfo(double x, double y) {
    	List<String> info = new ArrayList<String>();
    	// TODO: make tower info happen
    	if (isTowerPresent(x, y)) {
    		int[] currentTile = getTileCoordinates(new Point2D.Double(x, y));
    		ITower currTower = towers[currentTile[0]][currentTile[1]];
//    		info.add(((SimpleTower) currTower).);
    	}
    	
    	// TODO: add more monster info as appropriate
    	Monster m;
    	if ((m=monsterPresent(x, y)) != null) {
    		info.add("Monster");
    		info.add(""+m.x);
    		info.add(""+m.y);
    	}
    	return info;
    }
    
    /**
     * Return the monster at the specified coordinates. 
     * If there's no monster at that location, null will be returned.
     * 
     * @param x
     * @param y
     * @return the monster present
     */
    private Monster monsterPresent(double x, double y) {
    	Monster monster = null;
    	for (Monster m : monsters) {
    		double xUpper = m.x + m.getImageBBoxConst().width;
    		double yUpper = m.y + m.getImageBBoxConst().height;
    		if (m.x <= x && x <= xUpper && m.y <= y && y <= yUpper) {
    			monster = m;
    		}
    	}
    	return monster;
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
            player.changeMoney(DEFAULT_MONEY_MULTIPLIER * towers[xtile][ytile].getCost());
            towers[xtile][ytile].remove();
            towers[xtile][ytile] = null;
        }
    }

    // TODO: use this instead of other one, will change -jordan
    public void loadMapTest (String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            GameMapSchema mapToLoad = (GameMapSchema) is.readObject();
            is.close();

            TDMap tdMap = new TDMap(engine, mapToLoad);
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

    public void loadGameBlueprint (String filePath) throws ClassNotFoundException, IOException {
        GameBlueprint blueprint = null;
        if (filePath == null) {
            blueprint = createTestBlueprint();
        }
        else {
            try {
                blueprint = dataHandler.loadBlueprint(filePath, true);
            }
            catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        // Initialize from game settings from game schema
        GameSchema gameSchema = blueprint.getMyGameScenario();
        Map<String, Serializable> gameSchemaAttributeMap = gameSchema.getAttributesMap();
        this.player = new Player((Integer) gameSchemaAttributeMap.get(GameSchema.MONEY),
                                 (Integer) gameSchemaAttributeMap.get(GameSchema.LIVES));

        // Initialize factory objects
        if (blueprint.getMyTowerSchemas() != null) {
            factory.loadTowerSchemas(blueprint.getMyTowerSchemas());
        }
        if (blueprint.getMyMonsterSchemas() != null) {
            factory.loadMonsterSchemas(blueprint.getMyMonsterSchemas());
        }
        if (blueprint.getMyItemSchemas() != null) {
            factory.loadItemSchemas(blueprint.getMyItemSchemas());
        }

        // Initialize waves
        if (blueprint.getMyWaveSchemas() != null) {
            levelManager.cleanLoadWaveSchemas(blueprint.getMyWaveSchemas(), 0);
        }

        // Initialize map if necessary
        if (blueprint.getMyGameMapSchemas() != null) {
            TDMap map = new TDMap(engine, blueprint.getMyGameMapSchemas().get(0)); // TODO: load
                                                                                   // each map
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

	/**
	 * Returns whether or not the player has complete all waves and thus has won
	 * the game. This will always return false on survival mode.
	 * 
	 * @return boolean of whether game is won (all waves completed)
	 */
	public boolean isGameWon() {
		return levelManager.isGameWon();
	}
	
	/**
	 * Set whether or not the game is played on survival mode.
	 * @param survivalMode
	 * @return
	 */
	public void setSurvivalMode(boolean survivalMode){
		levelManager.setSurvivalMode(survivalMode);
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
        doItemActions();
        removeDeadMonsters();
    }

    private void doItemActions () {
        Iterator<TDItem> itemIter = items.iterator();
        while (itemIter.hasNext()) {
            TDItem currentItem = itemIter.next();
            if (currentItem.isDead()) {
                itemIter.remove();
                currentItem.remove();
                return;
            }
            currentItem.doAction(environ);
        }
    }

    /**
     * Place an item at the specified location.
     * If it costs more than the player has, do nothing.
     * 
     * @param name
     * @param x
     * @param y
     */
    public boolean placeItem (String name, double x, double y) {
        try {
            TDItem newItem = factory.placeItem(new Point2D.Double(x, y), name);
            if (newItem.getCost() <= player.getMoney()) {
                items.add(newItem);
                player.changeMoney(-newItem.getCost());
                return true;
            }
            else {
                newItem.setImage(null);
                newItem.remove();
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Clean up dead monsters from monsters list and JGEngine display.
     */
    private void removeDeadMonsters () {
        Iterator<Monster> monsterIter = monsters.iterator();
        List<Monster> newlyAdded = new ArrayList<Monster>();
        while (monsterIter.hasNext()) {
            Monster currentMonster = monsterIter.next();
            if (currentMonster.isDead()) {
                MonsterSpawnSchema resurrectSchema =
                        currentMonster.getResurrrectMonsterSpawnSchema();
                if (resurrectSchema != null) {
                    try {
                        newlyAdded =
                                levelManager.spawnMonsterSpawnSchema(resurrectSchema,
                                                                     currentMonster
                                                                             .getCurrentCoor());
                    }
                    catch (MonsterCreationFailureException e) {
                        // resurrection schema could not be spawned, so ignore it.
                        e.printStackTrace();
                    }
                }
                monsterIter.remove();
                addMoney(currentMonster.getMoneyValue());
                currentMonster.remove();
            }
        }
        monsters.addAll(newlyAdded);
    }

    private void addMoney (double moneyValue) {
        player.changeMoney(moneyValue);
    }

    /**
     * Call this to do the individual behavior of each Tower
     */
    private void doTowerBehaviors () {

        for (ITower[] towerRow : towers) {
            for (ITower t : towerRow) {
                if (t != null) {
                    t.callTowerActions(environ);
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
     * Upgrade the tower at the specified coordinates and return true if upgraded successfully.
     * If not possible, does nothing, and this method returns false.
     * 
     * @param x x-coordinate of tower to be upgraded
     * @param y y-coordinate of tower to be upgraded
     * @return boolean whether or not the tower was successfully upgraded
     * @throws TowerCreationFailureException
     */
    public boolean upgradeTower (double x, double y) throws TowerCreationFailureException {
        int[] coordinates = getTileCoordinates(new Point2D.Double(x, y));

        if (!isTowerPresent(coordinates)) { return false; }

        int xtile = coordinates[0];
        int ytile = coordinates[1];
        ITower existingTower = towers[xtile][ytile];
        String newTowerName = existingTower.getUpgradeTowerName();

        if (!isValidUpgradeTower(newTowerName)) { return false; }

        ITower newTower = factory.placeTower(new Point2D.Double(x, y), newTowerName);
        player.changeMoney(-newTower.getCost());
        // TODO: Specify cost of upgrade, calculate difference between old and new tower, or give
        // some discount?
        existingTower.remove();
        towers[xtile][ytile] = newTower;
        return true;
    }

    /**
     * Checks if a string is a valid tower name, i.e. non-empty and in the list of possible towers
     * defined by loaded schemas
     * 
     * @param newTowerName
     * @return boolean
     */
    private boolean isValidUpgradeTower (String newTowerName) {
        return (!newTowerName.equals("") && getPossibleTowers().contains(newTowerName));
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
        List<TowerSchema> testTowerSchema = new ArrayList<>();
        List<MonsterSchema> testMonsterSchema = new ArrayList<>();
        List<ItemSchema> testItemSchema = new ArrayList<>();

        // Create test items
        AnnihilatorItemSchema testAnnihilatorItem = new AnnihilatorItemSchema();
        testAnnihilatorItem.addAttribute(ItemSchema.NAME, "Annihilator");
        testAnnihilatorItem.addAttribute(ItemSchema.IMAGE_NAME, "fire.png");
        testAnnihilatorItem.addAttribute(ItemSchema.COST, (double) 1);
        testAnnihilatorItem.addAttribute(ItemSchema.DAMAGE, (double) 999);
        testItemSchema.add(testAnnihilatorItem);

        AreaBombItemSchema testAreaBombItem = new AreaBombItemSchema();
        testAreaBombItem.addAttribute(ItemSchema.NAME, "AreaBomb");
        testAreaBombItem.addAttribute(ItemSchema.IMAGE_NAME, "fire.png");
        testAreaBombItem.addAttribute(AreaBombItemSchema.RANGE, (double) 100);
        testItemSchema.add(testAreaBombItem);

        RowBombItemSchema testRowBombItem = new RowBombItemSchema();
        testRowBombItem.addAttribute(ItemSchema.NAME, "RowBomb");
        testRowBombItem.addAttribute(ItemSchema.IMAGE_NAME, "fire.png");
        testRowBombItem.addAttribute(ItemSchema.COST, (double) 1);
        testItemSchema.add(testRowBombItem);

        InstantFreezeItemSchema testInstantFreezeItem = new InstantFreezeItemSchema();
        testInstantFreezeItem.addAttribute(ItemSchema.NAME, "InstantFreeze");
        testInstantFreezeItem.addAttribute(ItemSchema.IMAGE_NAME, "fire.png");
        testInstantFreezeItem.addAttribute(InstantFreezeItemSchema.FREEZE_DURATION, (double) 999999);
        testItemSchema.add(testInstantFreezeItem);

        LifeSaverItemSchema testLifeSaverItem = new LifeSaverItemSchema();
        testLifeSaverItem.addAttribute(ItemSchema.NAME, "LifeSaver");
        testLifeSaverItem.addAttribute(ItemSchema.IMAGE_NAME, "fire.png");
        testItemSchema.add(testLifeSaverItem);

        // Create test towers
        TowerSchema testTowerOne = new TowerSchema();
        testTowerOne.addAttribute(TowerSchema.NAME, "MoneyTower");
        testTowerOne.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
        testTowerOne.addAttribute(TowerSchema.BULLET_IMAGE_NAME, "red_bullet");
        Collection<TowerBehaviors> towerBehaviors = new ArrayList<TowerBehaviors>();
        towerBehaviors.add(TowerBehaviors.MONEY_FARMING);
        testTowerOne.addAttribute(TowerSchema.UPGRADE_PATH, "BombingTower");
        testTowerOne.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors);
        testTowerOne.addAttribute(TowerSchema.COST, (double) 10);
        testTowerSchema.add(testTowerOne);

        TowerSchema testTowerTwo = new TowerSchema();
        testTowerTwo.addAttribute(TowerSchema.NAME, "ShootingTower");
        testTowerTwo.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
        testTowerTwo.addAttribute(TowerSchema.BULLET_IMAGE_NAME, "red_bullet");
        Collection<TowerBehaviors> towerBehaviors2 = new ArrayList<TowerBehaviors>();
        towerBehaviors2.add(TowerBehaviors.SHOOTING);
        testTowerTwo.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors2);
        testTowerTwo.addAttribute(TowerSchema.COST, (double) 10);
        testTowerSchema.add(testTowerTwo);

        TowerSchema testTowerThree = new TowerSchema();
        testTowerThree.addAttribute(TowerSchema.NAME, "BombingTower");
        testTowerThree.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
        testTowerThree.addAttribute(TowerSchema.BULLET_IMAGE_NAME, "blue_bullet");
        testTowerThree.addAttribute(TowerSchema.SHRAPNEL_IMAGE_NAME, "red_bullet");
        Collection<TowerBehaviors> towerBehaviors3 = new ArrayList<TowerBehaviors>();
        towerBehaviors3.add(TowerBehaviors.BOMBING);
        testTowerThree.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors3);
        testTowerThree.addAttribute(TowerSchema.COST, (double) 10);
        testTowerSchema.add(testTowerThree);

        TowerSchema testTowerFour = new TowerSchema();
        testTowerFour.addAttribute(TowerSchema.NAME, "FreezingTower");
        testTowerFour.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
        testTowerFour.addAttribute(TowerSchema.BULLET_IMAGE_NAME, "red_bullet");
        testTowerFour.addAttribute(TowerSchema.FREEZE_SLOWDOWN_PROPORTION, (double) 0.8);
        Collection<TowerBehaviors> towerBehaviors4 = new ArrayList<TowerBehaviors>();
        towerBehaviors4.add(TowerBehaviors.FREEZING);
        testTowerFour.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors4);
        testTowerFour.addAttribute(TowerSchema.COST, (double) 10);
        testTowerSchema.add(testTowerFour);

        TowerSchema testTowerFive = new TowerSchema();
        testTowerFive.addAttribute(TowerSchema.NAME, "SplashingTower");
        testTowerFive.addAttribute(TowerSchema.IMAGE_NAME, "tower.gif");
        testTowerFive.addAttribute(TowerSchema.BULLET_IMAGE_NAME, "red_bullet");
        Collection<TowerBehaviors> towerBehaviors5 = new ArrayList<TowerBehaviors>();
        towerBehaviors5.add(TowerBehaviors.SPLASHING);
        testTowerFive.addAttribute(TowerSchema.TOWER_BEHAVIORS, (Serializable) towerBehaviors5);
        testTowerFive.addAttribute(TowerSchema.COST, (double) 10);
        testTowerSchema.add(testTowerFive);

        // Create test monsters
        SimpleMonsterSchema testMonsterOne = new SimpleMonsterSchema();
        testMonsterOne.addAttribute(MonsterSchema.NAME, "test-monster-1");
        testMonsterOne.addAttribute(TDObjectSchema.IMAGE_NAME, "monster.png");
        testMonsterOne.addAttribute(MonsterSchema.SPEED, (double) 1);
        testMonsterOne.addAttribute(MonsterSchema.REWARD, (double) 200);
        testMonsterSchema.add(testMonsterOne);

        SimpleMonsterSchema testMonsterResurrects = new SimpleMonsterSchema();
        testMonsterResurrects.addAttribute(MonsterSchema.NAME, "test-monster-2");
        testMonsterResurrects.addAttribute(TDObjectSchema.IMAGE_NAME, "monster.png");
        // resurrect spawn schema is 2 testMonsterOnes
        MonsterSpawnSchema resurrect = new MonsterSpawnSchema(testMonsterOne, 2);
        testMonsterResurrects.addAttribute(MonsterSchema.RESURRECT_MONSTERSPAWNSCHEMA, resurrect);
        testMonsterResurrects.addAttribute(MonsterSchema.SPEED, (double) 1);
        testMonsterResurrects.addAttribute(MonsterSchema.REWARD, (double) 200);
        testMonsterSchema.add(testMonsterResurrects);

        testBlueprint.setMyTowerSchemas(testTowerSchema);
        testBlueprint.setMyMonsterSchemas(testMonsterSchema);
        testBlueprint.setMyItemSchemas(testItemSchema);

        // Create test game schemas
        GameSchema testGameSchema = new GameSchema();
        testGameSchema.addAttribute(GameSchema.LIVES, 3);
        testGameSchema.addAttribute(GameSchema.MONEY, 500);

        testBlueprint.setMyGameScenario(testGameSchema);

        // Create wave schemas
        List<WaveSpawnSchema> testWaves = new ArrayList<WaveSpawnSchema>();
        MonsterSpawnSchema testMonsterSpawnSchemaOne =
                new MonsterSpawnSchema(testMonsterResurrects, 1);
        WaveSpawnSchema testWaveSpawnSchemaOne = new WaveSpawnSchema();
        testWaveSpawnSchemaOne.addMonsterSchema(testMonsterSpawnSchemaOne);
        testWaves.add(testWaveSpawnSchemaOne);

        MonsterSpawnSchema testMonsterSpawnSchemaTwo = new MonsterSpawnSchema(testMonsterOne, 3);
        WaveSpawnSchema testWaveSpawnSchemaTwo = new WaveSpawnSchema();
        testWaveSpawnSchemaTwo.addMonsterSchema(testMonsterSpawnSchemaTwo);
        testWaves.add(testWaveSpawnSchemaTwo);

        MonsterSpawnSchema testMonsterSpawnSchemaThree = new MonsterSpawnSchema(testMonsterOne, 10);
        WaveSpawnSchema testWaveSpawnSchemaThree = new WaveSpawnSchema();
        testWaveSpawnSchemaThree.addMonsterSchema(testMonsterSpawnSchemaThree);
        testWaves.add(testWaveSpawnSchemaThree);

        testBlueprint.setMyWaveSchemas(testWaves);

        return testBlueprint;
    }

    /**
     * A list of names of possible towers to create
     * 
     * @return
     */
    public List<String> getPossibleTowers () {
        return Collections.unmodifiableList(factory.getPossibleTowersNames());
    }
    
    /**
     * A list of names of possible items to create
     * 
     * @return
     */
    public List<String> getPossibleItems () {
        return Collections.unmodifiableList(factory.getPossibleItemNames());
    }

    /**
     * Save the present game state to a loadable file.
     * Note: all saved game files saved to under resources folder.
     * 
     * @param gameName the file name to save the current game under.
     * @throws InvalidSavedGameException Problem saving the game
     */
    public void saveGame (String gameName) throws InvalidSavedGameException {
        GameState currentGame = new GameState();
        currentGame.updateGameStates(towers,
                                     levelManager.getCurrentWave(),
                                     levelManager.getAllWaves(),
                                     gameClock,
                                     player);
        try {
            dataHandler.saveState(currentGame, RESOURCE_PATH + gameName);
        }
        catch (IOException ioe) {
            throw new InvalidSavedGameException(ioe);
        }
    }

    /**
     * Clears current game and restarts a new game based on loaded saved game.
     * Only valid saved game files in the resources folder can be loaded.
     * Pass in the file's name only (e.g. which can be chosen through JFileChooser)
     * 
     * @param filename The full filename only.
     * @throws InvalidSavedGameException issue loading the game,
     *         (please pause and notify the player, then continue the present game).
     */
    public void loadSavedGame (String filename) throws InvalidSavedGameException {
        try {
            // TODO: check for proper game blueprint loaded prior?

            GameState newGameState = dataHandler.loadState(RESOURCE_PATH + filename);

            // replace towers, player, clock with new state
            clearAllTowers();
            towers = newGameState.getTowers();
            player = newGameState.getPlayer();
            gameClock = newGameState.getGameClock();

            // cleanly reload waves in the level manager, and reset wave # to start at.
            levelManager.cleanLoadWaveSchemas(newGameState.getAllWaveSchemas(),
                                              newGameState.getCurrentWaveNumber());

        }
        catch (ClassNotFoundException | IOException e) {
            throw new InvalidSavedGameException(e);
        }

    }

    /**
     * Clear all of the current towers.
     * Used internally to replace current tower state with with a new loaded saved game state.
     */
    private void clearAllTowers () {
        for (ITower[] row : towers) {
            for (ITower t : row) {
                if (t != null) {
                    t.remove();
                }
            }
            // null out tower matrix row by row after jgobject removal called.
            Arrays.fill(row, null);
        }
    }

}
