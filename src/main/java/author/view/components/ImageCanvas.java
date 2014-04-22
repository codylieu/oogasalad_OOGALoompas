package main.java.author.view.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author garysheng Custom subclass of AWT Canvas that holds a reference to the
 *         path of the image it should paint on its canvas.
 */
public class ImageCanvas extends Canvas {
	private Image myImage;
	private boolean fillsUpCanvas;
	private String imagePath;

	/**
	 * @return the path of the image that should be painted
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param fillsUpCanvas
	 *            boolean that sets whether the image edges should touch the
	 *            shortest edge of the canvas
	 * @param name
	 *            the name of the image canvas
	 */
	public ImageCanvas(boolean fillsUpCanvas, String name) {
		this.fillsUpCanvas = fillsUpCanvas;
		setName(name);
		this.imagePath = "";
	}

	/**
	 * @param newImagePath
	 *            sets a new image path to reference
	 * @throws IOException
	 */
	public void setImageFromPath(String newImagePath) throws IOException {

		imagePath = newImagePath;
		Image image = new ImageIcon(imagePath).getImage();
		setImage(image);
	}

	/*
	 * (non-Javadoc) draws a blank rectangle or the image needed to be painted
	 */
	public void paint(Graphics g) {
		if (!imagePath.equals("")) {
			if (fillsUpCanvas) {
				int minimumDimension = Math.min(getHeight(), getWidth());
				int maximumDimension = Math.max(getHeight(), getWidth());
				if (maximumDimension == getHeight()) {
					g.drawImage(myImage, 0, (getHeight() - getWidth()) / 2,
							minimumDimension, minimumDimension, this);
				} else {
					g.drawImage(myImage, (getWidth() - getHeight()) / 2, 0,
							minimumDimension, minimumDimension, this);
				}

			} else {
				g.drawImage(myImage, getWidth() / 3, getHeight() / 2
						- getWidth() / 6, getWidth() / 3, getWidth() / 3, this);
			}
		} else {

			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.BLACK);
		}
	}

	/**
	 * @param img
	 *            the image to set
	 */
	private void setImage(Image img) {
		this.myImage = img;
	}

	/**
	 * resets the image paths
	 */
	public void clearImagePath() {
		imagePath = "";
	}
}
