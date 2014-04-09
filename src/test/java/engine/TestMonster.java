package test.java.engine;

import java.awt.geom.Point2D;

import main.java.engine.Model;
import main.java.engine.objects.Exit;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.monster.SimpleMonster;

import org.junit.Test;

public class TestMonster {

	@Test
	public void testIsDead() {
		//THIS TEST DOES NOT WORK
		//there are no graphics so a nullpointerexception is thrown
		Model model = new Model(new FakeJGEngine());
		Point2D entrance = new Point2D.Double(200,100);
		Exit exit = new Exit(500, 300, model);
		Monster monster = new SimpleMonster(entrance, exit);
		
		monster.takeDamage(Integer.MAX_VALUE);
		assert(monster.isDead());
	}

}
