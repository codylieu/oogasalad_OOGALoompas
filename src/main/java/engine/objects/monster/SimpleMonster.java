package main.java.engine.objects.monster;

public class SimpleMonster extends Monster {
    
    public static String MONSTER_GFX = "monster";
    
    public SimpleMonster () {
        super("Simple", Math.random()*100, Math.random()*100, 100, 10, 10, 10, MONSTER_GFX);
        myPathFinder = new StraightLinePath(this);
    }

}
