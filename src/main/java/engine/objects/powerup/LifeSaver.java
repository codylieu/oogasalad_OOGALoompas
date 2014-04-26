package main.java.engine.objects.powerup;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.tower.ITower;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.powerups.AreaBombPowerupSchema;

/**
 * 
 * Grants lives to player
 *
 */
public class LifeSaver extends PowerupBehaviorDecorator{
	private static final int LIVES_DEFAULT = 1;
	private double livesToGrant;
	
	public LifeSaver(IPowerup baseItem, double livesToGrant) {
		super(baseItem);
		this.livesToGrant = livesToGrant;
	}
	
    /**
     * Constructor used by the factory in decorating a final tower.
     * 
     * @param baseItem
     * @param attributes
     */
    public LifeSaver (IPowerup baseItem, Map<String, Serializable> attributes) {
        this(
        	baseItem,
             Double.parseDouble(String.valueOf(TDObject.getValueOrDefault(attributes, ItemSchema.LIVES_TO_GRANT, LIVES_DEFAULT))));
    }

	@Override
	void doDecoratedBehavior(EnvironmentKnowledge environ) {
		environ.grantPlayerLife(livesToGrant);
		remove();
	}

}
