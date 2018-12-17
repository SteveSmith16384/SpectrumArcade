package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.MapLoader;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.splat.KillingWall;
import com.scs.spectrumarcade.entities.splat.PoisonousGrass;
import com.scs.spectrumarcade.models.GenericWalkingAvatar;

import mygame.util.Vector3Int;

public class SplatLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;
	private static final int WALL_HEIGHT = 5;

	private KillingWall killingWall;
	

	@Override
	public void generateLevel(SpectrumArcade game, int levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, 50, 1, 50, "Textures/white.png");
		game.addEntity(floor);

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, new Vector3Int(MAP_SIZE, WALL_HEIGHT, MAP_SIZE), 16, 1f, 1f);
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
		
		this.killingWall = new KillingWall(game, 2, 2);
		//game.addEntity(killingWall);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new WalkingPlayer(game, 5, 3, 5f, 0f, new GenericWalkingAvatar(game.getAssetManager(), "Textures/splat/black.png"));
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(5, 3f, 5f);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.White;
	}


	@Override
	public void process(float tpfSecs) {
	}


	@Override
	public String getHUDText() {
		return "SPLAT!";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, 1)), Vector3f.UNIT_Y);
	}

}
