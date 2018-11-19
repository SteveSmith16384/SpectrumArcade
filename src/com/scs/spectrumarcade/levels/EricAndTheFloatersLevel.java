package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.ericandfloaters.Floater;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;

public class EricAndTheFloatersLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;
	public static final int SEGMENT_SIZE = 3;

	public EricAndTheFloatersLevel(SpectrumArcade game) {
		super(game);
	}

	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/black.png");
		game.addEntity(floor);
		FloorOrCeiling ceiling = new FloorOrCeiling(game, 0, SEGMENT_SIZE+1, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/black.png");
		game.addEntity(ceiling); // ceiling.getMainNode().getWorldBound() 

		int gridSize = MAP_SIZE/SEGMENT_SIZE;


		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, gridSize, SEGMENT_SIZE);
		game.addEntity(terrainUDG);

		//  outer walls
		terrainUDG.addRectRange_Blocks(BlockCodes.EATF_OUTER_WALL, new Vector3Int(0, 0, 0), new Vector3Int(gridSize, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.EATF_OUTER_WALL, new Vector3Int(0, 0, 0), new Vector3Int(1, 1, gridSize));
		terrainUDG.addRectRange_Blocks(BlockCodes.EATF_OUTER_WALL, new Vector3Int(0, 0, gridSize), new Vector3Int(gridSize, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.EATF_OUTER_WALL, new Vector3Int(gridSize, 0, 0), new Vector3Int(1, 1, gridSize));

		//terrainUDG.addBlock_Block(new Vector3Int(2, 0, 2), BlockCodes.EATF_SOLID);
		
		// Add solid walls
		for (int zGrid=1 ; zGrid<gridSize-1 ; zGrid++) {
			for (int xGrid=1 ; xGrid<gridSize-1 ; xGrid++) {
				if (xGrid >= 2 || zGrid >= 2) {
					if ((xGrid) % 2 == 0 && (zGrid) % 2 == 0) {
						terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.EATF_SOLID);
					} else {
						if (NumberFunctions.rnd(1,  4) == 1) {
							terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.EATF_WEAK);
						} else if (NumberFunctions.rnd(1,  8) == 1) {
							//Floater f = new Floater(game, xGrid * SEGMENT_SIZE, 2f, zGrid*SEGMENT_SIZE);
							//game.addEntity(f);
						}
					}
				}
			}			
		}


		// Floaters
		for (int i=0 ; i<1 ; i++) {
			int x = SEGMENT_SIZE+5;//NumberFunctions.rnd(2, MAP_SIZE-4);
			int z = SEGMENT_SIZE+5;//NumberFunctions.rnd(2, MAP_SIZE-4);
			Floater floater = new Floater(game, x, 2f, z);
			game.addEntity(floater);
		}

	}

/*
	@Override
	public void moveAvatarToStartPosition(Avatar avatar) {
		avatar.warp(new Vector3f(SEGMENT_SIZE+1f, .5f, SEGMENT_SIZE+1f));

	}
	*/
	
	@Override
	public Avatar createAndPositionAvatar() {
		return new WalkingPlayer(game, SEGMENT_SIZE+1f, .5f, SEGMENT_SIZE+1f);
	}




	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}


	@Override
	public void process(float tpfSecs) {
		// Do nothing
	}

}
