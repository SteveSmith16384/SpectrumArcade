package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.collision.CollisionResults;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.manicminer.Key;
import com.scs.spectrumarcade.entities.minedout.Fence;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class MinedOutLevel extends AbstractLevel implements ILevelGenerator {

	private static final boolean SHOW_MINES = true;
	private static final int MAP_SIZE = 30;
	
	private boolean[][] mines = new boolean[MAP_SIZE][MAP_SIZE];
	private VoxelTerrainEntity terrainUDG;
	private RealtimeInterval checkMinesInt = new RealtimeInterval(1000);
	
	public MinedOutLevel(SpectrumArcade _game) {
		super(_game);
	}


	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE, 1f);
		game.addEntity(terrainUDG);

		terrainUDG.addRectRange_Blocks(BlockCodes.MINED_OUT_FRESH, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, MAP_SIZE));

		// choose mines
		for (int i=0 ; i<30 ; i++) {
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
					Fence f = new Fence(game, x, z, (z == 0 || z == MAP_SIZE-1) ? 0 : 9);
					game.addEntity(f);
				}
			}
		}

		// Add keys
		for (int i=0 ; i<5 ; i++) {
			int x = NumberFunctions.rnd(3, MAP_SIZE-4);
			int z = NumberFunctions.rnd(3, MAP_SIZE-4);
			Key key = new Key(game, x, 1.3f, z);
			game.addEntity(key);
		}

	}
	/*
	@Override
	public void moveAvatarToStartPosition(Avatar avatar) {
		avatar.warp(new Vector3f(MAP_SIZE/2, 3f, 3f));

	}
	 */


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(MAP_SIZE/2, 3f, 2f);
	}

	
	@Override
	public Avatar createAndPositionAvatar() {
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

}
