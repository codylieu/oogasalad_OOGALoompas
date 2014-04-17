package main.java.author.view.components;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class ImageCanvas extends Canvas {
	private Image myImage;
	private boolean fillsUpCanvas;
	private String imagePath;

	public String getImagePath() {
		return imagePath;
	}
	public ImageCanvas(boolean fillsUpCanvas, String name) {
		this.fillsUpCanvas = fillsUpCanvas;
		setName(name);
		this.imagePath = "";
	}
	
	public void setImagePath(String newImagePath) {
		imagePath = newImagePath;
	}

	public ImageCanvas() {
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics g) {
		if (myImage != null) {
			if (fillsUpCanvas) {
				int minimumDimension = Math.min(getHeight(), getWidth());
				int maximumDimension = Math.max(getHeight(), getWidth());
				if (maximumDimension == getHeight()) {
					g.drawImage(myImage, 0, (getHeight() - getWidth())/2, minimumDimension, minimumDimension, this);
				} else {
					g.drawImage(myImage, (getWidth() - getHeight())/2, 0, minimumDimension, minimumDimension, this);
				}
				
			} else {
				g.drawImage(myImage, getWidth() / 3, getHeight() / 2
						- getWidth() / 6, getWidth() / 3, getWidth() / 3, this);
			}
		}
	}

	public void setImage(Image img) {
		this.myImage = img;
	}
}
