package main.java.engine.objects.monster;

import java.awt.geom.Point2D;
import main.java.schema.SimpleMonsterSchema;
import main.java.schema.MonsterSchema;

public class SimpleMonster extends Monster {
    
    public static final double DEFAULT_HEALTH = 100;
    public static final double DEFAULT_MOVE_SPEED = 10;
    public static final double DEFAULT_REWARD_AMOUNT = 10;
    public static final String DEFAULT_MONSTER_GFX = "monster";
    
    public SimpleMonster (Point2D entrance, Point2D exit) {
        super(entrance, exit, DEFAULT_HEALTH, DEFAULT_MOVE_SPEED, DEFAULT_REWARD_AMOUNT, DEFAULT_MONSTER_GFX);
//        myPathFinder = new StraightLinePath(this);
    }
    
    public SimpleMonster (Point2D entrance, Point2D exit, MonsterSchema schema) {
    	super(entrance, exit, ((SimpleMonsterSchema) schema).getMyHealth(),
    			((SimpleMonsterSchema) schema).getMyMoveSpeed(), ((SimpleMonsterSchema) schema).getMyRewardAmount(),
    			((SimpleMonsterSchema) schema).getMyImage());
    }

}
