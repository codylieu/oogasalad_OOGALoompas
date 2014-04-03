package main.java.author.model;

public class EnemySwarm {
	private String myName;
	private int mySwarmSize;

	/**
	 * @param name
	 *            the name that exactly corresponds to a name of a specific
	 *            enemySchema
	 * @param swarmSize
	 *            the number of fellow enemies, including itself, it will spawn
	 *            with
	 */
	public EnemySwarm(String name, int swarmSize) {
		myName = name;
		mySwarmSize = swarmSize;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public int getMySwarmSize() {
		return mySwarmSize;
	}

	public void setMySwarmSize(int mySwarmSize) {
		this.mySwarmSize = mySwarmSize;
	}
}
