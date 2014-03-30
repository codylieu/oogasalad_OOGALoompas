package main.java.engine;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import jgame.impl.JGEngineInterface;
import main.java.engine.map.TDMap;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Model {
    private JGEngineInterface engine;

    public void setEngine(JGEngineInterface engine) {
        this.engine = engine;
    }

    public void loadMap(String fileName) {
        try {
            JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Jordan Ly\\Desktop\\Duke\\4\\cs308\\workspace\\oogasalad_OOGALoompas\\src\\main\\java\\engine\\testmap.json"));
            Gson gson = new Gson();

            TDMap map = gson.fromJson(reader, TDMap.class);
            map.loadIntoGame(engine);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
