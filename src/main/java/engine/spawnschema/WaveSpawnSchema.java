package main.java.engine.spawnschema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * 
 * A container for multiple MonsterSpawnSchema, representing all of the swarms of monsters to be spawned in a
 * particular wave
 * 
 * @author Austin
 * 
 */
public class WaveSpawnSchema {

    Collection<MonsterSpawnSchema> myMonsterSchemas = new ArrayList<MonsterSpawnSchema>();

    /**
     * Add a new MonsterSpawnSchema to this wave.
     * 
     * @param schema
     */
    public void addMonsterSchema (MonsterSpawnSchema schema) {
        myMonsterSchemas.add(schema);
    }

    /**
     * Clear collection of MonsterSpawnSchema
     */
    public void clearSchema () {
        myMonsterSchemas.clear();
    }

    /**
     * Get all of the MonsterSpawnSchema represented in this wave schema
     * 
     * @return unmodifiable collection of MonsterSpawnSchema
     */
    public Collection<MonsterSpawnSchema> getMonsterSpawnSchema () {
        return Collections.unmodifiableCollection(myMonsterSchemas);
    }
    
    

    /**
     * Create the wave of monsters specified by spawning all contained monster spawn schemas.
     *
     */
    public void spawn () {
    	for(MonsterSpawnSchema monsterSchema : myMonsterSchemas) {
    		monsterSchema.spawn();
    	}
    }
}
