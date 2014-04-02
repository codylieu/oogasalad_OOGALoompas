package main.java.engine;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import jgame.impl.JGEngineInterface;
import main.java.engine.factories.TowerFactory;
import main.java.engine.map.TDMap;
import main.java.engine.spawnschema.WaveSpawnSchema;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Model {
    public static final String RESOURCE_PATH = "/main/resources/";

    private JGEngineInterface engine;
    private TowerFactory towerFactory;
    private Gson gsonParser;
    private WaveSpawnSchema currentWave;

    public Model() {
        this.towerFactory = new TowerFactory(engine);
        this.gsonParser = new Gson();
    }

    public void setEngine(JGEngineInterface engine) {
        this.engine = engine;
        this.towerFactory = new TowerFactory(engine);
    }

    public void placeTower(double x, double y) {
        towerFactory.placeSimpleTower("SimpleTower",x, y);
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
     * Loads a wave spawn schema into the model
     *
     * @param fileName The name of the JSON file containing wave spawn schema info
     */
    public void loadWaveSpawnSchema(String fileName) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream(RESOURCE_PATH + fileName)));
            WaveSpawnSchema newWave = gsonParser.fromJson(reader, WaveSpawnSchema.class);
            
          //TODO: set up wave # -> wave schema map object
            currentWave = newWave; 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     *  Spawns a new wave.
     */
    public void spawnNextWave() {
    	currentWave.spawn();
    	//TODO: keep track of wave# state .. 
    }
}
