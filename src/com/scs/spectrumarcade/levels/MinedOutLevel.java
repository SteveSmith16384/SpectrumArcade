package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;

public class MinedOutLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;
	
	private boolean[][] mines = new boolean[MAP_SIZE][MAP_SIZE];
	private VoxelTerrainEntity terrainUDG;
	private SpectrumArcade game;

	public MinedOutLevel(SpectrumArcade _game) {
		super(_game);
	}

	
	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE, 1f);
		game.addEntity(terrainUDG);
		
		terrainUDG.addRectRange_Blocks(BlockCodes.MINED_OUT_FRESH, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, MAP_SIZE));
		
		// todo - choose mines
		for (int i=0 ; i>30 ; i++) {
			int x = NumberFunctions.rnd(1, MAP_SIZE-2);
			int z = NumberFunctions.rnd(3, MAP_SIZE-2);
			mines[x][z] = true;
		}
		// todo - add fence
		
		
	}
/*
	@Override
	public void moveAvatarToStartPosition(Avatar avatar) {
		avatar.warp(new Vector3f(MAP_SIZE/2, 3f, 3f));
		
	}
*/
	
	@Override
	public Avatar createAndPositionAvatar() {
		return new WalkingPlayer(game, MAP_SIZE/2, 3f, 2f);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Cyan;
	}


	@Override
	public void process(float tpfSecs) {
		// todo - not every iteration?
		Vector3f pos = game.player.getMainNode().getWorldTranslation();
		int x = (int)pos.x;
		int z = (int)pos.z;
		if (mines[x][z]) {
			Globals.p("You have stood on a mine!");
		} else {
			this.terrainUDG.addBlock_Block(new Vector3Int((int)pos.x, 0, (int)pos.z), BlockCodes.MINED_OUT_WALKED_ON);
		}
		
	}


	@Override
	public String getHUDText() {
		Vector3f pos = game.player.getMainNode().getWorldTranslation();
		int sx = (int)pos.x;
		int sz = (int)pos.z;
		int count = 0;
		for (int z=sz-1; z<=sz+1 ; z++) {
			for (int x=sx-1; x<=sx+1 ; x++) {
				try {
					if (mines[x][z]) {
						count++;
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					// Do nothing
				}
			}
		}
		return "There are " + count + " mines next to you";
	}

}
