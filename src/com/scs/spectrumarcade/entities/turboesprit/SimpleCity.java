package com.scs.spectrumarcade.entities.turboesprit;

import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.FloorOrCeiling;

import ssmith.lang.NumberFunctions;

public class SimpleCity {

	private static final int SKYSCRAPER_WIDTH = 7;

	private static final float WALL_THICKNESS = 3;
	public static final float FLOOR_THICKNESS = 3f;
	private static final float PATH_THICKNESS = .1f;

	private SpectrumArcade game;
	private int numSectors;

	public SimpleCity(SpectrumArcade _game) {
		game = _game;

		numSectors = 4;//Settings.NUM_SECTORS;
	}


	public void setup() {
		for (int y=0 ; y<numSectors ; y++) {
			for (int x=0 ; x<numSectors ; x++) {
				createSector(x*(SKYSCRAPER_WIDTH+6), y*(SKYSCRAPER_WIDTH+6));
			}			
		}

		// Add outer walls
		for (int j=0 ; j<numSectors ; j++) {
			// Back
			float height = NumberFunctions.rndFloat(10, 20);
			SkyScraper skyscraperBack = new SkyScraper(game, j*(SKYSCRAPER_WIDTH+6), -WALL_THICKNESS, SKYSCRAPER_WIDTH+6, height, WALL_THICKNESS);
			game.addEntity(skyscraperBack);

			// Left
			height = NumberFunctions.rndFloat(10, 20);
			SkyScraper skyscraperLeft = new SkyScraper(game, -WALL_THICKNESS, j*(SKYSCRAPER_WIDTH+6), WALL_THICKNESS, height, SKYSCRAPER_WIDTH+6);
			game.addEntity(skyscraperLeft);

			// Front
			height = NumberFunctions.rndFloat(10, 20);
			SkyScraper skyscraperFront = new SkyScraper(game, j*(SKYSCRAPER_WIDTH+6), (numSectors*(SKYSCRAPER_WIDTH+6)), SKYSCRAPER_WIDTH+6, height, WALL_THICKNESS);
			game.addEntity(skyscraperFront);

			// Right
			height = NumberFunctions.rndFloat(10, 20);
			SkyScraper skyscraperRight = new SkyScraper(game, numSectors*(SKYSCRAPER_WIDTH+6), j*(SKYSCRAPER_WIDTH+6), WALL_THICKNESS, height, SKYSCRAPER_WIDTH+6);
			game.addEntity(skyscraperRight);
		}

	}


	private void createSector(float x, float z) {
		/* 123456789012
		 * XRRRRRRRRRRR
		 * RRRRRRRRRRRR
		 * XRSSSSSSSSXR
		 * RRSxxxxxxSRR
		 * RRSxxxxxxSRR
		 * RRSxxxxxxSRR
		 * RRSxxxxxxSRR
		 * RRSxxxxxxSRR
		 * RRSxxxxxxSRR
		 * RRSSSSSSSSRR
		 * RRXRRRRRRRRR
		 * RRRRRRRRRRRR
		 * 
		 */

		// Road
		String roadtex = "Textures/road2.png";
		CreateFloor(x, 0, z, SKYSCRAPER_WIDTH+6, FLOOR_THICKNESS, 2, roadtex); // top x
		CreateFloor(x+SKYSCRAPER_WIDTH+4, 0, z+2, 2, FLOOR_THICKNESS, SKYSCRAPER_WIDTH+4, roadtex); // right x
		CreateFloor(x+2, 0, z+SKYSCRAPER_WIDTH+4, SKYSCRAPER_WIDTH+2, FLOOR_THICKNESS, 2, roadtex); // bottom x
		CreateFloor(x, 0, z+2, 2, FLOOR_THICKNESS, SKYSCRAPER_WIDTH+4, roadtex); // Left

		// Sidewalk
		String sidewalktex = "Textures/floor015.png";
		CreateFloor(x+2, PATH_THICKNESS, z+2, SKYSCRAPER_WIDTH+2, FLOOR_THICKNESS, 1, sidewalktex); // top x
		CreateFloor(x+SKYSCRAPER_WIDTH+3, PATH_THICKNESS, z+3, 1, FLOOR_THICKNESS, SKYSCRAPER_WIDTH+1, sidewalktex); // right x
		CreateFloor(x+2, PATH_THICKNESS, z+SKYSCRAPER_WIDTH+3, SKYSCRAPER_WIDTH+1, FLOOR_THICKNESS, 1, sidewalktex); // bottom x
		CreateFloor(x+2, PATH_THICKNESS, z+3, 1, FLOOR_THICKNESS, SKYSCRAPER_WIDTH, sidewalktex); // Left x

		int i = NumberFunctions.rnd(1, 1); // todo - was 4
		if (i == 1) {
			// Grass area
			String grasstex = "Textures/grass.jpg";
			CreateFloor(x+3, 0f, z+3, SKYSCRAPER_WIDTH, 0.1f, SKYSCRAPER_WIDTH, grasstex);
		} else {
			// Add skyscraper
			float height = NumberFunctions.rndFloat(3, 10);
			SkyScraper skyscraper = new SkyScraper(game, x+3, z+3, SKYSCRAPER_WIDTH, height, SKYSCRAPER_WIDTH);
			game.addEntity(skyscraper);

		}
	}


	private FloorOrCeiling CreateFloor(float x, float y, float z, float w, float h, float d, String tex) {
		FloorOrCeiling floor = new FloorOrCeiling(game, x, y, z, w, h, d, tex);
		game.addEntity(floor);
		return floor;
	}

}