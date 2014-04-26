package main.java.engine.objects.powerup;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.powerup.decorations.Fire;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.powerups.AnnihilatorPowerupSchema;
import main.java.schema.tdobjects.powerups.AreaBombPowerupSchema;

/**
 * 
 * A special bomb that destroys an area of monsters
 * 
 * @author Lawrence
 *
 */
public class AreaBomb extends PowerupBehaviorDecorator{

	private static final double RANGE_DEFAULT = 100;
	private static final double DAMAGE_DEFAULT = 50;
	private double myRange;
	private double myDamage;

	public AreaBomb (IPowerup baseItem, double range, double damage) {
		super(baseItem);
		myRange = range;
		myDamage = damage;
	}

	public AreaBomb (IPowerup baseItem, Map<String, Serializable> attributes) {
		this(
				baseItem,
				Double.parseDouble(String.valueOf(TDObject.getValueOrDefault(attributes, ItemSchema.RANGE, RANGE_DEFAULT))),
				Double.parseDouble(String.valueOf(TDObject.getValueOrDefault(attributes, ItemSchema.DAMAGE, DAMAGE_DEFAULT)))
				);
	}

	protected boolean isInRange(Monster m) {
		Point2D monster = m.centerCoordinate();
		Point2D bomb = new Point2D.Double(getXCoordinate(), getYCoordinate());
		return (monster.distance(bomb) <= myRange);
	}
	
	private void setFire(Monster m) {
		new Fire(m.x, m.y);
	}

	@Override
	void doDecoratedBehavior(EnvironmentKnowledge environ) {
		for (Monster m : environ.getAllMonsters()) {
			if (isInRange(m)) {
				setFire(m);
				m.takeDamage(myDamage);
			}
		}
		remove();

	}

}
