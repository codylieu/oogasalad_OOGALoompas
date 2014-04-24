package test.java.engine;

import static org.junit.Assert.assertEquals;
import main.java.engine.Model;
import main.java.engine.TestEngine;

import org.junit.Test;

public class TestModel {

	@Test
	public void checkTowerIsPlaced() {
		Model model = new Model(new TestJGEngine(), null);
		
		final int testx = 200, testy = 200;
//		System.out.println("ha");
		System.out.println(model.placeTower(testx, testy,"name"));
		System.out.println("ha");
		model.isTowerPresent(testx, testy);
		System.out.println("ha");
		
		assert(model.isTowerPresent(testx, testy));
	}

	
	@Test
	public void checkModelGetters() {
		Model model = new Model(new TestEngine(), null);
		
		assertEquals(0, model.getGameClock(), .0001);
	}
	
	@Test
	public void checkPlayerGetters() {
		Model model = new Model(new TestEngine(), null);
		model.addNewPlayer();
		
		assert(model.getMoney() >= 0);
		assert(model.getPlayerLives() > 0);
		assertEquals(0, model.getScore(), .0001);
		
	}
}
