package com.scs.spectrumarcade;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.IOException;

import com.scs.spectrumarcade.jme.PaintableImage;

public class TextTexture extends PaintableImage {

	private Font font;

	public TextTexture(int pixelsW, int pixelsH) {
		super(pixelsW, pixelsH);

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("Assets/Fonts/zx_spectrum-7.ttf"));
			//font = font.deriveFont(14);
			font = font.deriveFont(Font.PLAIN, 72);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		refreshImage();
	}


	@Override
	public void paint(Graphics2D g) {
		g.setBackground(Color.green);
		g.clearRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString("R TAPE LOADING ERROR", 20, 20);
	}

}
