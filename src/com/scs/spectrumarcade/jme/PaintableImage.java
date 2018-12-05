package com.scs.spectrumarcade.jme;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import com.jme3.texture.Image;

public abstract class PaintableImage extends Image {

	private BufferedImage backImg;
	private BufferedImage backImg2;
	private ByteBuffer scratch;

	public PaintableImage(int width, int height) {
		super();

		backImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		backImg2 = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		//setFormat(Format.ABGR8);
		setFormat(Format.RGBA8);
		setWidth(backImg.getWidth());
		setHeight(backImg.getHeight());
		scratch = ByteBuffer.allocateDirect(4 * backImg.getWidth() * backImg.getHeight());
	}


	public void refreshImage() {
		Graphics2D g = backImg.createGraphics();
		paint(g);
		g.dispose();

		// Rotate image
		for (int y=0 ; y<backImg.getHeight() ; y++) {
			for (int x=0 ; x<backImg.getWidth(); x++) {
				//int newX = backImg.getWidth() - x - 1;
				int newY = backImg.getHeight() - y - 1;
				backImg2.setRGB(x, newY, backImg.getRGB(x, y));
			}
		}

		// get the image data
		byte data[] = (byte[]) backImg2.getRaster().getDataElements(0, 0, backImg.getWidth(), backImg.getHeight(), null);

		scratch.clear();
		scratch.put(data, 0, data.length);
		scratch.rewind();
		setData(scratch);

	}

	public abstract void paint(Graphics2D graphicsContext);

}
