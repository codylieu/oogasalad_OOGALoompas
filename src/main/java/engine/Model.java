package main.java.engine;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import jgame.impl.JGEngineInterface;
import main.java.engine.factories.TowerFactory;
import main.java.engine.map.TDMap;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Model {
    public static final String RESOURCE_PATH = "/main/resources/";

    private JGEngineInterface engine;
    private TowerFactory towerFactory;

    public Model() {
        this.towerFactory = new TowerFactory(engine);
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
}
