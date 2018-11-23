package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.collision.CollisionResults;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.abilities.BombGun_AA;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.antattack.Ant;
import com.scs.spectrumarcade.entities.manicminer.Key;

import mygame.util.Vector3Int;
import ssmith.lang.Functions;
import ssmith.lang.NumberFunctions;

public class AntAttackLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 128;

	public AntAttackLevel() {//SpectrumArcade _game) {
		super();//_game);
	}


	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/white.png");
		game.addEntity(floor);

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE+2, 1f);
		game.addEntity(terrainUDG);
		
		String text = Functions.readAllFileFromJar("maps/antattack_map.txt");
		String[] lines = text.split("\n");

		for (String line : lines) {
			//lineNum++;
			String[] parts = line.split(",");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			int z = Integer.parseInt(parts[2]);
			terrainUDG.addBlock_Block(new Vector3Int(x, y, z), BlockCodes.ANT_ATTACK);
		}
		
/*
		// Border
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, MAP_SIZE-1), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(MAP_SIZE, 0, 0), new Vector3Int(1, 1, MAP_SIZE));

		terrainUDG.addArrayRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(2, 0, 2), MapLoader.loadMap("maps/antattack_amphi.csv"));
		terrainUDG.addArrayRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(12, 0, 12), MapLoader.loadMap("maps/antattack_pyramid.csv"));
*/
		
		// Add ants
		for (int i=0 ; i<5 ; i++) {
			int x = NumberFunctions.rnd(40, MAP_SIZE-40);
			int z = NumberFunctions.rnd(10, 20);
			Ant ant = new Ant(game, x, 11, z); // Make height unique to stop collisions at start
			game.addEntity(ant);
		}

		// Add keys
		for (int i=0 ; i<5 ; i++) {
			int x = NumberFunctions.rnd(3, MAP_SIZE-4);
			int z = NumberFunctions.rnd(3, MAP_SIZE-4);
			Ray r = new Ray(new Vector3f(x, 100, z), new Vector3f(0, -1, 0));
			CollisionResults res = new CollisionResults();
			int c = game.getRootNode().collideWith(r, res);
			if (c == 0) {
				throw new RuntimeException("Something wrong with the map, there should always be a collision");
			}
			Vector3f pos = res.getCollision(0).getContactPoint();
			Key key = new Key(game, pos.x, pos.y + 1.3f, pos.z); // Raise key so ants don't hit it
			game.addEntity(key);
		}
		
		
		// Show all blocks for debugging
		/*terrainUDG.addBlock_Block(new Vector3Int(1, 0, 1), BlockCodes.BRICK);
		terrainUDG.addBlock_Block(new Vector3Int(2, 0, 2), BlockCodes.CONVEYOR);
		terrainUDG.addBlock_Block(new Vector3Int(3, 0, 3), BlockCodes.EATF_SOLID); // 26
		terrainUDG.addBlock_Block(new Vector3Int(4, 0, 4), BlockCodes.EATF_WEAK); // 17
		terrainUDG.addBlock_Block(new Vector3Int(5, 0, 5), BlockCodes.EXIT); // 24
		terrainUDG.addBlock_Block(new Vector3Int(6, 0, 6), BlockCodes.RED_FLOOR_PXL); // ant attack
		terrainUDG.addBlock_Block(new Vector3Int(7, 0, 7), BlockCodes.RED_FLOOR_UDG); // 41
		terrainUDG.addBlock_Block(new Vector3Int(8, 0, 8), BlockCodes.SPLAT); // OK
		/*terrainUDG.addBlock_Block(new Vector3Int(9, 0, 9), BlockCodes.);
		terrainUDG.addBlock_Block(new Vector3Int(10, 0, 10), BlockCodes.BRICK);
		terrainUDG.addBlock_Block(new Vector3Int(11, 0, 0), BlockCodes.BRICK);
		terrainUDG.addBlock_Block(new Vector3Int(0, 0, 0), BlockCodes.BRICK);
		terrainUDG.addBlock_Block(new Vector3Int(0, 0, 0), BlockCodes.BRICK);
		terrainUDG.addBlock_Block(new Vector3Int(0, 0, 0), BlockCodes.BRICK);*/
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(MAP_SIZE/2, 2f, 3f);
	}

	
	@Override
	public Avatar createAndPositionAvatar() {
		WalkingPlayer wp = new WalkingPlayer(game, MAP_SIZE/2, 2f, 3f, true);
		wp.setAbility(1, new BombGun_AA(game));
		return wp;
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.White;
	}


	@Override
	public void process(float tpfSecs) {
		// Do nothing
	}


	@Override
	public String getHUDText() {
		return "";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, 1)), Vector3f.UNIT_Y);
	}

}
