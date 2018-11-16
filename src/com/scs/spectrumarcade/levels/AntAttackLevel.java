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
		//VoxelTerrainEntity terrainPixel = new VoxelTerrainEntity(game, 0f, 0f, 0f, 32*8, 1f/8f);
		//game.addEntity(terrainPixel);

		// Border
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, MAP_SIZE-1), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(MAP_SIZE, 0, 0), new Vector3Int(1, 1, MAP_SIZE));

		//terrainUDG.addArrayRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(2, 0, 2), MapLoader.loadMap("maps/antattack_amphi.csv"));
		//terrainUDG.addArrayRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(12, 0, 12), MapLoader.loadMap("maps/antattack_pyramid.csv"));

		for (int i=0 ; i<1 ; i++) {
			int x = NumberFunctions.rnd(2, MAP_SIZE-4);
			int z = NumberFunctions.rnd(2, MAP_SIZE-4);
			Ant ant = new Ant(game, x, 6+i, z); // Make jeight unique to stop collisions at start
			game.addEntity(ant);
		}

		for (int i=0 ; i<5 ; i++) {
			int x = NumberFunctions.rnd(2, MAP_SIZE-4);
			int z = NumberFunctions.rnd(2, MAP_SIZE-4);
			Ray r = new Ray(new Vector3f(x, 100, z), new Vector3f(0, -1, 0));
			CollisionResults res = new CollisionResults();
			int c = game.getRootNode().collideWith(r, res);
			Vector3f pos = res.getCollision(0).getContactPoint();
			Key key = new Key(game, pos.x, pos.y + 1.3f, pos.z); // Raise key so ants don't hit it
			game.addEntity(key);
		}
		
		
		// Show all blocks for debugging
		terrainUDG.addBlock_Block(new Vector3Int(1, 0, 1), BlockCodes.BRICK);
		terrainUDG.addBlock_Block(new Vector3Int(2, 0, 2), BlockCodes.CONVEYOR);
		terrainUDG.addBlock_Block(new Vector3Int(3, 0, 3), BlockCodes.EATF_SOLID); // 26
		terrainUDG.addBlock_Block(new Vector3Int(4, 0, 4), BlockCodes.EATF_WEAK); // 17
		terrainUDG.addBlock_Block(new Vector3Int(5, 0, 5), BlockCodes.EXIT);
		terrainUDG.addBlock_Block(new Vector3Int(6, 0, 6), BlockCodes.RED_FLOOR_PXL);
		terrainUDG.addBlock_Block(new Vector3Int(7, 0, 7), BlockCodes.RED_FLOOR_UDG);
		terrainUDG.addBlock_Block(new Vector3Int(8, 0, 8), BlockCodes.SPLAT);
		/*terrainUDG.addBlock_Block(new Vector3Int(9, 0, 9), BlockCodes.);
		terrainUDG.addBlock_Block(new Vector3Int(10, 0, 10), BlockCodes.BRICK);
		terrainUDG.addBlock_Block(new Vector3Int(11, 0, 0), BlockCodes.BRICK);
		terrainUDG.addBlock_Block(new Vector3Int(0, 0, 0), BlockCodes.BRICK);
		terrainUDG.addBlock_Block(new Vector3Int(0, 0, 0), BlockCodes.BRICK);
		terrainUDG.addBlock_Block(new Vector3Int(0, 0, 0), BlockCodes.BRICK);*/
	}


	@Override
	public void moveAvatarToStartPosition(Player avatar) {
		avatar.playerControl.warp(new Vector3f(10, 20f, 10f));
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.White;
	}

}
