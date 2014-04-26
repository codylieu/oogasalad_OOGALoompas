package main.java.engine.objects.powerup;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import jgame.JGObject;
import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.powerup.decorations.Fire;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.powerups.AreaBombPowerupSchema;

/**
 * 
 * A special bomb that destroys a row of monsters
 * 
 * @author Lawrence
 *
 */
public class RowBomb extends PowerupBehaviorDecorator{

	public RowBomb(Point2D location, String image, double cost, double buildup_time, double damage, int flash_interval) {
		super("row_bomb", location.getX(), location.getY(), image, cost, buildup_time, damage, flash_interval);
	}
	
	public RowBomb(Map<String, Serializable> attributes) {
		this(
				baseItem,
				Double.parseDouble(String.valueOf(TDObject.getValueOrDefault(attributes, ItemSchema.LIVES_TO_GRANT, LIVES_DEFAULT))));
				);
	}

	@Override
	public void doAction(EnvironmentKnowledge environmentKnowledge) {
		timeCounter++;
		if (timeCounter >= buildupTime) {
			for (Monster m : environmentKnowledge.getAllMonsters()) {
				if (isInRange(m)) {
					setFireToTheRain(m);
					m.takeDamage(damage);
				}
			}
			terminateItem();
		} else flash(timeCounter, flash_interval, image);
	}

	private void setFireToTheRain(Monster m) {
		new Fire(m.x, m.y);
	}

	protected boolean isInRange(Monster m) {
		double lower = y - m.getImageBBoxConst().height/2;
		double upper = y + m.getImageBBoxConst().height/2;
		return ((m.y > lower) && (m.y < upper));
	}

	@Override
	void doDecoratedBehavior(EnvironmentKnowledge environ) {
		// TODO Auto-generated method stub
		
	}

}
