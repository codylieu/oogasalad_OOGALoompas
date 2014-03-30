package main.java.engine;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import jgame.impl.JGEngineInterface;
import main.java.engine.map.TDMap;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Model {
    private JGEngineInterface engine;

    public void setEngine(JGEngineInterface engine) {
        this.engine = engine;
    }

    public void loadMap(String fileName) {
        try {
            InputStream is = getClass().getResourceAsStream("/main/resources/" + fileName);
            InputStreamReader isr = new InputStreamReader(is);
            JsonReader reader = new JsonReader(isr);
            Gson gson = new Gson();

            TDMap map = gson.fromJson(reader, TDMap.class);
            map.loadIntoGame(engine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
