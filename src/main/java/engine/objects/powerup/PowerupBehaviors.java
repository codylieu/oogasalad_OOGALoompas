package main.java.engine.objects.powerup;

import java.io.Serializable;



public enum PowerupBehaviors implements Serializable {
	AREA_BOMBING(AreaBomb.class),
	INSTANT_FREEZE(InstantFreeze.class),
	LIFE_SAVER(LifeSaver.class);
//	ROW_BOMB(RowBomb.class);

	private Class<? extends IPowerup> concreteClass;

	private PowerupBehaviors (Class<? extends IPowerup> concreteClass) {
		this.concreteClass = concreteClass;
	}

	public Class<? extends IPowerup> getConcreteClass () {
		return concreteClass;
	}

}
