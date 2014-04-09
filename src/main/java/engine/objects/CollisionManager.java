package main.java.engine.objects;

<<<<<<< HEAD
import main.java.schema.MonsterSchema;
=======
import java.io.Serializable;
import java.util.Map;
import main.java.engine.objects.monster.Monster;
>>>>>>> FETCH_HEAD
import jgame.platform.JGEngine;


public class CollisionManager {

    private JGEngine myEngine;

    public CollisionManager (JGEngine engine) {
        myEngine = engine;
    }

    /**
     * 
     * Handle all JGObject and tile/terrain collisions.
     */
    public void checkAllCollisions () {

        // let projectile handle causing of damage and splash animation
        myEngine.checkCollision(MonsterSchema.MONSTER_CID, Projectile.TOWER_PROJECTILE_CID);
        
        myEngine.checkCollision(Monster.MONSTER_CID, Exit.EXIT_CID);
        // myEngine.checkBGCollision(tilecid, Monster.MONSTER_CID);
    }
}
