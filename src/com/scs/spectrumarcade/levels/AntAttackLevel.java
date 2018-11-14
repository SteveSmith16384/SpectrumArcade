package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.collision.CollisionResults;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.MapLoader;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.Player;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.antattack.Ant;
import com.scs.spectrumarcade.entities.manicminer.Key;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;

public class AntAttackLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;

	public AntAttackLevel() {
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
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, MAP_SIZE-1), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(MAP_SIZE, 0, 0), new Vector3Int(1, 1, MAP_SIZE));

		terrainUDG.addArrayRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(2, 0, 2), MapLoader.loadMap("maps/antattack_amphi.csv"));
		terrainUDG.addArrayRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(12, 0, 12), MapLoader.loadMap("maps/antattack_pyramid.csv"));

		for (int i=0 ; i<1 ; i++) {
			int x = NumberFunctions.rnd(2, MAP_SIZE-4);
			int z = NumberFunctions.rnd(2, MAP_SIZE-4);
			Ant ant = new Ant(game, x, 15+i, z, 0); // Make jeight unique to stop collisions at start
			game.addEntity(ant);
		}

		for (int i=0 ; i<5 ; i++) {
			int x = NumberFunctions.rnd(2, MAP_SIZE-4);
			int z = NumberFunctions.rnd(2, MAP_SIZE-4);
			Ray r = new Ray(new Vector3f(x, 100, z), new Vector3f(0, -1, 0));
			CollisionResults res = new CollisionResults();
			int c = game.getRootNode().collideWith(r, res);
			Vector3f pos = res.getCollision(0).getContactPoint();
			Key key = new Key(game, pos.x, pos.y, pos.z);
			game.addEntity(key);
		}
	}


	@Override
	public void moveAvatarToStartPosition(Player avatar) {
		avatar.playerControl.warp(new Vector3f(10, 20f, 10f));
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.White;
	}


	@Override
	public int getLevelCode() {
		return LevelCodes.LVL_ANT_ATTACK;
	}


}
