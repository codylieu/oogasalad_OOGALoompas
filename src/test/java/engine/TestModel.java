package test.java.engine;

import static org.junit.Assert.assertEquals;
import main.java.engine.Model;
import main.java.engine.TestEngine;

import org.junit.Test;

public class TestModel {

	@Test
	public void checkTowerIsPlaced() {
		Model model = new Model(new TestEngine());
		
		final int testx = 2, testy = 2;
		
		model.placeTower(testx, testy);
		
		assert(model.isTowerPresent(testx, testy));
	}

	
	@Test
	public void checkModelGetters() {
		Model model = new Model(new TestEngine());
		
		assertEquals(0, model.getGameClock(), .0001);
	}
	
	@Test
	public void checkPlayerGetters() {
		Model model = new Model(new TestEngine());
		model.addNewPlayer();
		
		assert(model.getMoney() >= 0);
		assert(model.getPlayerLife() > 0);
		assertEquals(0, model.getScore(), .0001);
		
	}
}
