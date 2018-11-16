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
	private static final int WALL_HEIGHT = 5;

	public SplatLevel() {
	}


	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, 50, 1, 50, "Textures/white.png");
		game.addEntity(floor);

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, 64, 1f);
		game.addEntity(terrainUDG);

		// Border
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(0, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(0, 0, MAP_SIZE-1), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(MAP_SIZE, 0, 0), new Vector3Int(1, 1, MAP_SIZE));

		int map[][] = MapLoader.loadMap("maps/splat_map1.csv");
		
		// Create heightmap
		int heightMap[][] = new int[map.length][map[0].length];
		for (int z=0 ; z<map[0].length ; z++) {
			for (int x=0 ; x<map.length ; x++) {
				try {
					if (map[x][z] == 1) {
						heightMap[x][z] = WALL_HEIGHT;
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					ex.printStackTrace();
				}
			}
		}
		terrainUDG.addArrayRange_Blocks(BlockCodes.SPLAT, new Vector3Int(2, 0, 2), heightMap);

		// Load plants
		for (int z=0 ; z<map[0].length ; z++) {
			for (int x=0 ; x<map.length ; x++) {
				if (map[x][z] == 2) {
					PoisonousGrass key = new PoisonousGrass(game, x, 0, z);
					game.addEntity(key);

				}
			}
		}
	}


	@Override
	public void moveAvatarToStartPosition(Player avatar) {
		avatar.playerControl.warp(new Vector3f(2, 3f, 2f));
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.White;
	}

}
