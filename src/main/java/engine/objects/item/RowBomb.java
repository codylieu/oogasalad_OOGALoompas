package main.java.engine.objects.item;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.monster.Monster;

public class RowBomb extends TDItem{
	
	private static final String IMAGE = "row_bomb";
	private static final double COST = 50;
	private static final double BUILDUP_Time = 100;
	
	private String image;

	public RowBomb(double x, double y) {
		super("row_bomb", x, y, IMAGE, COST, BUILDUP_Time);
	}

	@Override
	public void doAction(EnvironmentKnowledge environmentKnowledge) {
		timeCounter++;
		if (timeCounter >= buildupTime) {
			for (Monster m : environmentKnowledge.getAllMonsters()) {
				if (isInRange(m, x, y)) {
					m.setDead();
				}
			}
			setDead();
		} else flash(timeCounter, 5, IMAGE);
	}

	private boolean isInRange(Monster m, double x, double y) {
		double lower = y - m.getImageBBoxConst().height/2;
		double upper = y + m.getImageBBoxConst().height/2;
		return ((m.y > lower) && (m.y < upper));
	}

}
