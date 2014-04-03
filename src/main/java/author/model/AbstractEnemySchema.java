package main.java.author.model;

import java.awt.Dimension;

public abstract class AbstractEnemySchema {

	private String myName;
	private String myImage;
	private boolean isFlyingType;
	private double myMoveSpeed;
	private Dimension myTileSize;
	private double myRewardAmount;

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getMyImage() {
		return myImage;
	}

	public void setMyImage(String myImage) {
		this.myImage = myImage;
	}

	public boolean isFlyingType() {
		return isFlyingType;
	}

	public void setFlyingType(boolean isFlyingType) {
		this.isFlyingType = isFlyingType;
	}

	public double getMyMoveSpeed() {
		return myMoveSpeed;
	}

	public void setMyMoveSpeed(double myMoveSpeed) {
		this.myMoveSpeed = myMoveSpeed;
	}

	public Dimension getMyTileSize() {
		return myTileSize;
	}

	public void setMyTileSize(Dimension myTileSize) {
		this.myTileSize = myTileSize;
	}

	public double getMyRewardAmount() {
		return myRewardAmount;
	}

	public void setMyRewardAmount(double myRewardAmount) {
		this.myRewardAmount = myRewardAmount;
	}
	
}
