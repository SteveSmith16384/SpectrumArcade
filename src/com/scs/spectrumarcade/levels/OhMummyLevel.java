package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.models.GenericWalkingAvatar;

import mygame.util.Vector3Int;

public class OhMummyLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE_X = 30;
	private static final int MAP_SIZE_Z = 20;

	private boolean[][] mines = new boolean[MAP_SIZE_X][MAP_SIZE_Z];
	private VoxelTerrainEntity terrainUDG;
	private int levelNum;

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, new Vector3Int(MAP_SIZE_X, 1, MAP_SIZE_Z), 16, 1f, 1f);
		game.addEntity(terrainUDG);

		terrainUDG.addRectRange_Blocks(BlockCodes.MINED_OUT_FRESH, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE_X, 1, MAP_SIZE_Z));

		//todo - create map
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(MAP_SIZE_X/2, 1.1f, 2f);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new WalkingPlayer(game, getAvatarStartPos(), 3f, 0f, new GenericWalkingAvatar(game.getAssetManager(), "Textures/minedout/avatar_black.png"));
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Blue;
	}


	@Override
	public void process(float tpfSecs) {
	}



	@Override
	public String getHUDText() {
		return "";
	}


}
