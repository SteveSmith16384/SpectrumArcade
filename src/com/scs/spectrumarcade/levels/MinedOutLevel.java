package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.manicminer.Key;
import com.scs.spectrumarcade.entities.minedout.Fence;
import com.scs.spectrumarcade.entities.minedout.SquareIndicator;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class MinedOutLevel extends AbstractLevel implements ILevelGenerator {

	private static final boolean SHOW_MINES = false;
	private static final int MAP_SIZE = 30;
	private static final int NUM_MINES = 0;
	private static final int NUM_KEYS = 0;
	
	private boolean[][] mines = new boolean[MAP_SIZE][MAP_SIZE];
	private VoxelTerrainEntity terrainUDG;
	private RealtimeInterval checkMinesInt = new RealtimeInterval(100);
	

	@Override
	public void generateLevel(SpectrumArcade game, int levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE, 1f);
		game.addEntity(terrainUDG);

		terrainUDG.addRectRange_Blocks(BlockCodes.MINED_OUT_FRESH, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, MAP_SIZE));

		// choose mines
		for (int i=0 ; i<NUM_MINES ; i++) {
			int x = NumberFunctions.rnd(1, MAP_SIZE-2);
			int z = NumberFunctions.rnd(3, MAP_SIZE-2);
			mines[x][z] = true;
			if (SHOW_MINES) {
				this.terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.BRICK);

			}
		}

		// add fence
		for (int z=0; z<MAP_SIZE ; z++) {
			for (int x=0; x<MAP_SIZE ; x++) {
				if (x == 0 || z == 0 || x == MAP_SIZE-1 || z == MAP_SIZE-1) {
					Fence f = new Fence(game, x, z, (z == 0 || z == MAP_SIZE-1) ? 0 : 90);
					game.addEntity(f);
				}
			}
		}

		// Add keys
		for (int i=0 ; i<NUM_KEYS ; i++) {
			int x = NumberFunctions.rnd(3, MAP_SIZE-4);
			int z = NumberFunctions.rnd(3, MAP_SIZE-4);
			Key key = new Key(game, x, 1.3f, z);
			game.addEntity(key);
		}

		SquareIndicator si = new SquareIndicator(game);
		game.addEntity(si);
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(MAP_SIZE/2, 3f, 2f);
	}

	
	@Override
	public IAvatar createAndPositionAvatar() {
		return new WalkingPlayer(game, MAP_SIZE/2, 3f, 2f, false);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Blue;
	}


	@Override
	public void process(float tpfSecs) {
		if (checkMinesInt.hitInterval()) {
		Vector3f pos = game.player.getMainNode().getWorldTranslation();
		int x = (int)pos.x;
		int z = (int)pos.z;
		if (mines[x][z]) {
			Globals.p("You have stood on a mine!");
		} else {
			this.terrainUDG.addBlock_Block(new Vector3Int((int)pos.x, 0, (int)pos.z), BlockCodes.MINED_OUT_WALKED_ON);
		}
		}
	}


	@Override
	public String getHUDText() {
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
		return "There are " + count + " mines next to you";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, 1)), Vector3f.UNIT_Y);
	}

}
