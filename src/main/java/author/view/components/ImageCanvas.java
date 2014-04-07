package main.java.author.view.components;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class ImageCanvas extends Canvas {
	Image myImage;

	public ImageCanvas(Image img) {
		myImage = img;
	}

	public ImageCanvas() {
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics g) {
		if (myImage != null) {
			g.drawImage(myImage, getWidth() / 4, getHeight() / 4,
					getWidth() / 2, getHeight() / 2, this);
		}
	}

	public void setImage(Image img) {
		this.myImage = img;
	}
}
