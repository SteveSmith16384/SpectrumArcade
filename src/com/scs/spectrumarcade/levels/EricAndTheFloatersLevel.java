package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.Player;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.ericandfloaters.Floater;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;

public class EricAndTheFloatersLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;
	public static final int SEGMENT_SIZE = 3;

	public EricAndTheFloatersLevel() {
	}

	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, 50, 1, 50, "Textures/splat.png");
		game.addEntity(floor);
		
		int gridSize = MAP_SIZE/SEGMENT_SIZE;

		// todo - do outer walls

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, gridSize, SEGMENT_SIZE);
		game.addEntity(terrainUDG);

		// Add solid walls
		for (int z=0 ; z<gridSize ; z++) {
			for (int x=0 ; x<gridSize ; x++) {
				if (x % 3 == 0 && z % 3 == 0) {
					terrainUDG.addBlock(new Vector3Int(x, 0, z), BlockCodes.EATF_SOLID);
				} else {
					if (NumberFunctions.rnd(1,  4) == 1) {
						terrainUDG.addBlock(new Vector3Int(x, 0, z), BlockCodes.EATF_WEAK);
					} else if (NumberFunctions.rnd(1,  4) == 1) {
						Floater f = new Floater(game, x * SEGMENT_SIZE, SEGMENT_SIZE*.55f, z*SEGMENT_SIZE);
						game.addEntity(f);
					}
				}
			}			
		}
		
	}
	
	
	@Override
	public void moveAvatarToStartPosition(Player avatar) {
		avatar.playerControl.warp(new Vector3f(10, 3f, 10f));
		
	}

	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}

}
