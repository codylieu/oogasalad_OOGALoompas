package main.java.engine.objects.item.decorations;

import jgame.JGObject;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.powerup.TDPowerupPowerup;

/**
 * A fire object that shows up when a monster dies.
 * The fire object does nothing but shows up and disappears after some time. 
 *
 */
public class Fire extends JGObject {
	
	private static final int DEFAULT_EXPIRATION_TIME = 10;
	private double timeCounter;
	private double expirationTime;
	
	public Fire(double x, double y) {
		super("fire", true, x, y, 0, "fire");
		timeCounter = 0;
		expirationTime = DEFAULT_EXPIRATION_TIME;
	}
	
	@Override
	public void move() {
		if (++timeCounter > expirationTime) this.remove();
	}
}