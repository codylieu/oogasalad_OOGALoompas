package main.java.author.view.tabs.terrain;

import java.awt.Color;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum TerrainAttribute {

	Walkable {
		@Override
		Color getColor() {
			return Color.GREEN;
		}

		@Override
		public int getIndex() {
			return 10;
		}
	},

	Flyable {
		@Override
		Color getColor() {
			return Color.BLUE;
		}

		@Override
		public int getIndex() {
			return 11;
		}
	},

	Untraversable {
		@Override
		Color getColor() {
			return Color.RED;
		}

		@Override
		public int getIndex() {
			return 12;
		}
	},

	Entry {
		@Override
		Color getColor() {
			return Color.MAGENTA;
		}

		@Override
		public int getIndex() {
			return 13;
		}
	},

	Exit {
		@Override
		Color getColor() {
			return Color.ORANGE;
		}

		@Override
		public int getIndex() {
			return 14;
		}
	};

	private static Map<Integer, TerrainAttribute> enumMap = new HashMap<Integer, TerrainAttribute>();

	static {
		for (TerrainAttribute attr : values()) {
			enumMap.put(attr.getIndex(), attr);
		}
	}

	@Override
	public String toString() {
		return this.name();
	}

	public static TerrainAttribute getAttribute(int i) {
		return enumMap.get(i);
	}

	/**
	 * Specifies the color that will be displayed in the Terrain View when the
	 * specific attribute is added to a Tile
	 */
	abstract Color getColor();

	/**
	 * Specifies the collision ID of a TerrainAttribute
	 */
	public abstract int getIndex();

}
