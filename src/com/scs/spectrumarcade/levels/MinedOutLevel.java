package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.Player;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;

public class MinedOutLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;

	public MinedOutLevel() {
	}

	
	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE, 1f);
		game.addEntity(terrainUDG);
		
	}

	@Override
	public void moveAvatarToStartPosition(Player avatar) {
		avatar.playerControl.warp(new Vector3f(10, 3f, 10f));
		
	}

	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Cyan;
	}

}
