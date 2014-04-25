package main.java.engine.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of state and calculates statistics
 * @author Austin
 *
 */
public class StatsLogModel {

    private Map<String, Integer> countsMap;

    public StatsLogModel () {
        countsMap = new HashMap<String, Integer>();
    }

    public void log (String name) {
        if (!countsMap.containsKey(name)) {
            countsMap.put(name, 1);
        }
        countsMap.put(name, countsMap.get(name) + 1);
    }

    public void printStats () {
        System.out.println(countsMap);
    }

}
