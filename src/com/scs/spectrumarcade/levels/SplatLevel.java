package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.MapLoader;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.Player;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.splat.PoisonousGrass;

import mygame.util.Vector3Int;

public class SplatLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;

	public SplatLevel() {
	}


	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, 50, 1, 50, "Textures/white.png");
		game.addEntity(floor);

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, 64, 1f);
		game.addEntity(terrainUDG);
		VoxelTerrainEntity terrainPixel = new VoxelTerrainEntity(game, 0f, 0f, 0f, 32*8, 1f/8f);
		game.addEntity(terrainPixel);

		// Border
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(0, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(0, 0, MAP_SIZE-1), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(MAP_SIZE, 0, 0), new Vector3Int(1, 1, MAP_SIZE));

		int map[][] = MapLoader.loadMap("maps/splat_map1.csv");
		// Create heightmap
		int heightMap[][] = new int[map.length][map[0].length];
		for (int z=0 ; z<map[0].length ; z++) {
			for (int x=0 ; z<map.length ; x++) {
				if (map[x][z] == 1) {
					heightMap[x][z] = 1;
				}
			}
		}
		terrainUDG.addArrayRange_Blocks(BlockCodes.SPLAT, new Vector3Int(2, 0, 2), heightMap);

		// Load plants
		for (int z=0 ; z<map[0].length ; z++) {
			for (int x=0 ; z<map.length ; x++) {
				if (map[x][z] == 2) {
					PoisonousGrass key = new PoisonousGrass(game, x, 0, z);
					game.addEntity(key);

				}
			}
		}

	}


	@Override
	public void moveAvatarToStartPosition(Player avatar) {
		avatar.playerControl.warp(new Vector3f(10, 3f, 10f));
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.White;
	}


	@Override
	public int getLevelCode() {
		return LevelCodes.LVL_SPLAT;
	}


}
