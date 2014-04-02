package main.java.engine.objects;

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

    Collection<MonsterSpawnSchema> myMonsterSchema;

    /**
     * Add a new MonsterSpawnSchema to this wave.
     * 
     * @param schema
     */
    public void addMonsterSchema (MonsterSpawnSchema schema) {
        myMonsterSchema.add(schema);
    }

    /**
     * Clear collection of MonsterSpawnSchema
     */
    public void clearSchema () {
        myMonsterSchema.clear();
    }

    /**
     * Get all of the MonsterSpawnSchema represented in this wave schema
     * 
     * @return unmodifiable collection of MonsterSpawnSchema
     */
    public Collection<MonsterSpawnSchema> getMonsterSpawnSchema () {
        return Collections.unmodifiableCollection(myMonsterSchema);
    }
}
