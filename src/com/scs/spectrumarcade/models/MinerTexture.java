package com.scs.spectrumarcade.models;

import java.awt.Color;
import java.awt.Graphics2D;

import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.scs.spectrumarcade.jme.PaintableImage;

import ssmith.lang.NumberFunctions;

public class MinerTexture {

	private static final int SIZE = 32;

	public MinerTexture() {
		super();
	}


	public static Texture getTexture(final int levelCode) {
		PaintableImage pi = new PaintableImage(SIZE, SIZE) {

			@Override
			public void paint(Graphics2D g) {
				Color col = null;
				/*switch (levelCode) {
				case LevelCodes.LVL_CENTRAL_CAVERN:
					col = Color.WHITE;
					break;
				case LevelCodes.LVL_ANT_ATTACK:*/
					col = Color.DARK_GRAY;
/*					break;
				default:
					Globals.pe("Warning: no avatar colour set up for level " + levelCode);
				}*/

				for (int row=0 ; row<5 ; row++) {
					switch (row) {
					case 0: // trousers
						g.setColor(col.darker());
						break;

					case 1: // Shirt
						g.setColor(col);
						break;

					case 2: // Hair
						g.setColor(getRandomHairColour());
						break;

					case 3: // Eyes
						g.setColor(Color.LIGHT_GRAY);
						break;

					case 4: // Skin
						g.setColor(getRandomSkinColour());
						break;
					}

					int sy = getRowStart(row);
					int ey = getRowStart(row+1)-1;
					g.fillRect(0, sy, SIZE, ey);
				}
			}

		};

		pi.refreshImage();
		return new Texture2D(pi);
	}


	private static int getRowStart(int row) {
		switch (row) {
		case 0:
			return 0;
		case 1:
			return 10;
		case 2:
			return 15;
		case 3:
			return 21;
		case 4:
			return 26;
		case 5:
			return 31;
		default:
			throw new IllegalArgumentException("Invalid row:" + row);
		}
	}


	private static Color getRandomHairColour() {
		return Color.LIGHT_GRAY;
	}


	private static Color getRandomSkinColour() {
		int i = NumberFunctions.rnd(1, 2);
		switch (i) {
		case 1:
			return Color.pink;
		case 2:
			return Color.pink;
		default:
			throw new IllegalArgumentException(""+i);
		}
	}

}

