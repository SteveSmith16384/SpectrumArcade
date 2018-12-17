package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.ericandfloaters.ExplosionShard;
import com.scs.spectrumarcade.entities.minedout.Fence;
import com.scs.spectrumarcade.entities.minedout.SquareIndicator;
import com.scs.spectrumarcade.models.GenericWalkingAvatar;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;

public class MinedOutLevel extends AbstractLevel implements ILevelGenerator {

	private static final boolean SHOW_MINES = false;
	private static final int MAP_SIZE_X = 30;
	private static final int MAP_SIZE_Z = 20;

	private boolean[][] mines = new boolean[MAP_SIZE_X][MAP_SIZE_Z];
	private VoxelTerrainEntity terrainUDG;
	//private RealtimeInterval checkMinesInt = new RealtimeInterval(100);
	private int levelNum;
	private int lastCheckX, lastCheckZ;

	//private CameraSystem camSys;

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		/*camSys = new CameraSystem(game, FOLLOW_CAM, 2f);
		if (FOLLOW_CAM) {
			camSys.setupFollowCam(3, 0, true);
		}*/

		levelNum = _levelNum;

		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, new Vector3Int(MAP_SIZE_X, 1, MAP_SIZE_Z), 16, 1f, 1f);
		game.addEntity(terrainUDG);

		terrainUDG.addRectRange_Blocks(BlockCodes.MINED_OUT_FRESH, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE_X, 1, MAP_SIZE_Z));

		// choose mines
		int NUM_MINES = 30 + (levelNum*10);
		for (int i=0 ; i<NUM_MINES ; i++) {
			int x = NumberFunctions.rnd(1, MAP_SIZE_X-2);
			int z = NumberFunctions.rnd(3, MAP_SIZE_Z-3);
			mines[x][z] = true;
			if (SHOW_MINES) {
				this.terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.BRICK);

			}
		}

		// add fence
		for (int z=0; z<MAP_SIZE_Z ; z++) {
			for (int x=0; x<MAP_SIZE_X ; x++) {
				if (x == 0 || z == 0 || x == MAP_SIZE_X-1 || z == MAP_SIZE_Z-1) {
					if (z != MAP_SIZE_Z-1 || x != MAP_SIZE_X/2) { // No fence at exit
						Fence f = new Fence(game, x, z, (z == 0 || z == MAP_SIZE_Z-1) ? 0 : 90);
						game.addEntity(f);
					}
				}
			}
		}

		SquareIndicator si = new SquareIndicator(game);
		game.addEntity(si);
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(MAP_SIZE_X/2, 1.1f, 2f);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new WalkingPlayer(game, MAP_SIZE_X/2, 1.1f, 2f, 0f, new GenericWalkingAvatar(game.getAssetManager(), "Textures/minedout/avatar_black.png"));
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Blue;
	}


	@Override
	public void process(float tpfSecs) {
		//camSys.process(game.getCamera(), game.player);
		
		Vector3f pos = game.player.getMainNode().getWorldTranslation();
		int x = (int)pos.x;
		int z = (int)pos.z;
		if (x != lastCheckX || z != lastCheckZ) {
			lastCheckX = (int)pos.x;
			lastCheckZ = (int)pos.z;

			if (mines[x][z]) {
				Globals.p("You have stood on a mine!");
				this.explosion(pos);
				game.playerKilled();
			} else {
				this.terrainUDG.addBlock_Block(new Vector3Int((int)pos.x, 0, (int)pos.z), BlockCodes.MINED_OUT_WALKED_ON);
			}

			// Check if player completed level
			if (pos.z >= MAP_SIZE_Z-1) {
				//this.levelNum++;
				//game.setLevel(this.getClass(), levelNum);
				game.setNextLevel(this.getClass(), levelNum++);
			}
		}
	}



	@Override
	public String getHUDText() {
		// todo - cache this
		Vector3f pos = game.player.getMainNode().getWorldTranslation();
		int x = (int)pos.x;
		int z = (int)pos.z;
		int count = 0;
		try {
			if (mines[x-1][z]) {
				count++;
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			// Do nothing
		}
		try {
			if (mines[x+1][z]) {
				count++;
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			// Do nothing
		}
		try {
			if (mines[x][z-1]) {
				count++;
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			// Do nothing
		}
		try {
			if (mines[x][z+1]) {
				count++;
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			// Do nothing
		}
		return "Level: " + this.levelNum + "\nThere are " + count + " mines next to you";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, 1)), Vector3f.UNIT_Y);
	}


	private void explosion(Vector3f pos) {
		// Shards
		for (int i=0 ; i<15 ; i++) {
			float x = pos.x + NumberFunctions.rndFloat(-.2f,  .2f);
			float y = pos.y + NumberFunctions.rndFloat(-.2f,  .2f);
			float z = pos.z + NumberFunctions.rndFloat(-.2f,  .2f);
			ExplosionShard shard = new ExplosionShard(game, x, y, z, .2f, "Textures/blocks/minedout_cyan.png");
			game.addEntity(shard);
		}

	}

}
