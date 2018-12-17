package com.scs.spectrumarcade.levels;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.manicminer.Key;
import com.scs.spectrumarcade.entities.manicminer.MMRock;
import com.scs.spectrumarcade.entities.manicminer.PoisonousPlant;
import com.scs.spectrumarcade.entities.manicminer.Robot;
import com.scs.spectrumarcade.models.GenericWalkingAvatar;

import mygame.util.Vector3Int;

public class ManicMinerCentralCavern extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_DEPTH = 5;

	@Override
	public void generateLevel(SpectrumArcade game, int levelNum) {
		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, 64, 16, 1f, 1f);
		game.addEntity(terrainUDG);
		VoxelTerrainEntity terrainPixel = new VoxelTerrainEntity(game, 0f, 0f, 0f, 32*8, 16, 1f/8f, 1f);
		game.addEntity(terrainPixel);

		// For testing
		//terrainUDG.addBlock(new Vector3f(3, 3, 3), BlockCodes.RED_FLOOR);
		//terrainUDG.addBlock(new Vector3f(4, 4, 4), BlockCodes.BRICK);

		// Walls either side
		terrainUDG.addRectRange_Blocks(BlockCodes.BRICK, new Vector3Int(0, 0, 0), new Vector3Int(1, 22, MAP_DEPTH));
		terrainUDG.addRectRange_Blocks(BlockCodes.BRICK, new Vector3Int(32, 0, 0), new Vector3Int(1, 22, MAP_DEPTH));

		// Floor along bottom
		terrainUDG.addRectRange_Blocks(BlockCodes.RED_FLOOR_UDG, new Vector3Int(1, 0, 0), new Vector3Int(31, 1, MAP_DEPTH));

		// Exit
		terrainUDG.addRectRange_Blocks(BlockCodes.EXIT, new Vector3Int(30, 1, 2), new Vector3Int(2, 2, 2));

		// Level 1
		terrainPixel.addRectRange_Fibonacci_Actual(BlockCodes.RED_FLOOR_PXL, new Vector3f(6, 2, 2), new Vector3f(15, 1, MAP_DEPTH-3));		
		PoisonousPlant flower1 = new PoisonousPlant(game, 15, 3, 3);
		game.addEntity(flower1);
		terrainUDG.addRectRange_Blocks(BlockCodes.BRICK,     new Vector3Int(21, 3, 2), new Vector3Int(3, 1, MAP_DEPTH-3));
		terrainPixel.addRectRange_Fibonacci_Actual(BlockCodes.RED_FLOOR_PXL, new Vector3f(26, 3, 2), new Vector3f(6, 1, MAP_DEPTH-3));

		// Level 2
		terrainPixel.addRectRange_Fibonacci_Actual(BlockCodes.RED_FLOOR_PXL, new Vector3f(1, 5, 2), new Vector3f(4, 1, MAP_DEPTH-3));
		terrainUDG.addRectRange_Blocks(BlockCodes.CONVEYOR,  new Vector3Int(9, 6, 2), new Vector3Int(18, 1, MAP_DEPTH-3));
		terrainUDG.addRectRange_Blocks(BlockCodes.BRICK,     new Vector3Int(18, 7, 2), new Vector3Int(3, 1, MAP_DEPTH-3));
		terrainPixel.addRectRange_Fibonacci_Actual(BlockCodes.RED_FLOOR_PXL, new Vector3f(29, 6, 2), new Vector3f(2, 1, MAP_DEPTH-3));


		// Top level
		terrainPixel.addRectRange_Fibonacci_Actual(BlockCodes.RED_FLOOR_PXL, new Vector3f(1, 11, 2), new Vector3f(30, 1, MAP_DEPTH-3));
		//terrainUDG.addRectRange_Blocks(BlockCodes.COLLAPSING_RED_FLOOR, new Vector3Int(1, 10, 2), new Vector3Int(10, 1, MAP_DEPTH-3));


		// Ceiling
		terrainPixel.addRectRange_Fibonacci_Actual(BlockCodes.RED_FLOOR_PXL, new Vector3f(1, 15, 0), new Vector3f(30, 1, MAP_DEPTH));

		// Plants
		//PoisonousPlant flower = new PoisonousPlant(game, game.getNextEntityID(), 2, 3, 4);
		//game.addEntity(flower);
		PoisonousPlant flower2 = new PoisonousPlant(game, 23, 9, 3);
		game.addEntity(flower2);

		// Rocks
		MMRock rock = new MMRock(game, 3, 14, 2);
		game.addEntity(rock);

		// Keys
		Key key = new Key(game, 2, 3, 2);
		game.addEntity(key);

		// Robot
		Robot robot = new Robot(game, 14, 10, 3, 9, 17);
		game.addEntity(robot);
	}

/*
	@Override
	public void moveAvatarToStartPosition(Avatar avatar) {
		avatar.warp(new Vector3f(15, 5, 20f));
	}
*/

	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(3, 2, 3);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new WalkingPlayer(game, 3, 2, 3f, 7f, new GenericWalkingAvatar(game.getAssetManager(), "Textures/manicminer/avatar_white.png"));
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}


	@Override
	public void process(float tpfSecs) {
		//camSys.process(game.getCamera(), game.player);
	}


	@Override
	public String getHUDText() {
		return "CENTRAL CAVERN";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		cam.lookAt(cam.getLocation().add(new Vector3f(1, 0, 0)), Vector3f.UNIT_Y);
	}

}
