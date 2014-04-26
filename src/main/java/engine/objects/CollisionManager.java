package main.java.engine.objects;

import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.projectile.DamageProjectile;

import jgame.platform.JGEngine;


/**
 * A class that handles all the collisions in the game. 
 *
 */
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
        myEngine.checkCollision(Monster.MONSTER_CID, DamageProjectile.TOWER_PROJECTILE_CID);
        
        myEngine.checkCollision(Monster.MONSTER_CID, Exit.EXIT_CID);
        // myEngine.checkBGCollision(tilecid, Monster.MONSTER_CID);
    }
}
