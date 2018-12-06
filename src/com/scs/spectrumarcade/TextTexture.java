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
	private String text;

	public TextTexture(String _text, int pixelsW, int pixelsH) {
		super(pixelsW, pixelsH);
		
		text = _text;

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("Assets/Fonts/zx_spectrum-7.ttf"));
			//font = font.deriveFont(14);
			font = font.deriveFont(Font.PLAIN, 72);
			if (font.getSize() == 1) {
				throw new RuntimeException("Unable to create font");
			}
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		refreshImage();
	}


	@Override
	public void paint(Graphics2D g) {
		g.setBackground(Color.GREEN);
		g.clearRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString(text, 5, 10);
	}

}
