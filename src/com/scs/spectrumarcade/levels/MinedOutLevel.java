package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.Player;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;

import mygame.util.Vector3Int;

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
		
		// todo - add fence
		
		// todo - choose mines
		
	}
/*
	@Override
	public void moveAvatarToStartPosition(Avatar avatar) {
		avatar.warp(new Vector3f(MAP_SIZE/2, 3f, 3f));
		
	}
*/
	
	@Override
	public Avatar createAndPositionAvatar() {
		return new Player(game, MAP_SIZE/2, 3f, 3f);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Cyan;
	}


	@Override
	public void process(float tpfSecs) {
		Vector3f pos = ((AbstractPhysicalEntity)game.player).getMainNode().getWorldTranslation();
		this.terrainUDG.addBlock_Block(new Vector3Int((int)pos.x, 0, (int)pos.z), BlockCodes.MINED_OUT_WALKED_ON);
		
	}

}
