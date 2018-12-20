package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.abilities.BombGun_EATF;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.components.IEntity;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.ericandfloaters.DestroyableWall;
import com.scs.spectrumarcade.entities.ericandfloaters.Floater;
import com.scs.spectrumarcade.models.GenericWalkingAvatar;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class EricAndTheFloatersLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;
	public static final int SEGMENT_SIZE = 3;

	private int levelNum;
	private RealtimeInterval checkEndfLevelInt = new RealtimeInterval(4000);

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/blocks/black.png");
		game.addEntity(floor);

		// No ceiling so we can view from above
		//FloorOrCeiling ceiling = new FloorOrCeiling(game, 0, SEGMENT_SIZE+1, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/black.png");
		//game.addEntity(ceiling); // ceiling.getMainNode().getWorldBound() 

		int gridSize = MAP_SIZE/SEGMENT_SIZE;

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, new Vector3Int(gridSize, 1, gridSize), 16, SEGMENT_SIZE, 1f);
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
				//if (xGrid >= 2 || zGrid >= 2) {
				if ((xGrid) % 2 == 0 && (zGrid) % 2 == 0) { // Solid walls
					terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.EATF_SOLID);
				} else {
					if (NumberFunctions.rnd(1,  6) == 1) { // Destroyable wall
						DestroyableWall dw = new DestroyableWall(game, xGrid, zGrid);
						game.addEntity(dw);
					} else if (NumberFunctions.rnd(1,  8) <= levelNum) {
						if (xGrid >= 2 || zGrid >= 2) {
							Floater f = new Floater(game, xGrid * SEGMENT_SIZE, 2f, zGrid*SEGMENT_SIZE);
							game.addEntity(f);
						}
					}
				}
				//}
			}			
		}

		/*
		// Walls for testing
		DestroyableWall dw = new DestroyableWall(game, 1, 3);
		game.addEntity(dw);

		DestroyableWall dw2 = new DestroyableWall(game, 5, 1);
		game.addEntity(dw2);
		 */

		/*
		// Floaters for testing
		for (int i=0 ; i<1 ; i++) {
			int x = SEGMENT_SIZE+3;//NumberFunctions.rnd(2, MAP_SIZE-4);
			int z = SEGMENT_SIZE+3;//NumberFunctions.rnd(2, MAP_SIZE-4);
			Floater floater = new Floater(game, x, 2f, z);
			game.addEntity(floater);
		}
		 */
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(SEGMENT_SIZE+1f, .5f, SEGMENT_SIZE+1f);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		WalkingPlayer wp = new WalkingPlayer(game, getAvatarStartPos(), 4f, 0f, new GenericWalkingAvatar(game.getAssetManager(), "Textures/ericandthefloaters/eric_avatar.png"));
		game.setAbility(1, new BombGun_EATF(game));
		return wp;
	}



	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}


	@Override
	public void process(float tpfSecs) {
		if (checkEndfLevelInt.hitInterval()) {
			this.checkIfAllBaddiesDead();
		}
	}


	@Override
	public String getHUDText() {
		return "Level " + this.levelNum;
	}

	/*
	@Override
	public void setInitialCameraDir(Camera cam) {
		cam.lookAt(cam.getLocation().add(new Vector3f(1, 0, 1)), Vector3f.UNIT_Y);
	}
	 */

	public void checkIfAllBaddiesDead() {
		boolean any = false;
		for (IEntity e : game.entities) {
			if (e instanceof Floater) {
				Floater enemy = (Floater)e;
				if (!enemy.isMarkedForRemoval()) {
					any = true;
					break;
				}
			}
		}
		if (!any) {
			game.setNextLevel(this.getClass(), levelNum++);
		}
	}

}
