package test.java.engine;

import static org.junit.Assert.assertEquals;
import main.java.engine.Model;
import main.java.engine.TestEngine;

import org.junit.Test;

public class TestModel {

	@Test
	public void checkTowerIsPlaced() {
		Model model = new Model(new TestJGEngine(), null); // TODO: Replace with actual path
		
		final int testx = 200, testy = 200;
		System.out.println(model.placeTower(testx, testy,"name"));
		model.isTowerPresent(testx, testy);
		
		assert(model.isTowerPresent(testx, testy));
	}

	
	@Test
	public void checkModelGetters() {
		Model model = new Model(new TestEngine(), null); // TODO: Replace with actual blueprint
		
		assertEquals(0, model.getGameClock(), .0001);
	}
	
	@Test
	public void checkPlayerGetters() {
		Model model = new Model(new TestEngine(), null); // TOOD: Replace with actual blueprint
		model.addNewPlayer();
		
		assert(model.getMoney() >= 0);
		assert(model.getPlayerLives() > 0);
		assertEquals(0, model.getScore(), .0001);
		
	}
}
