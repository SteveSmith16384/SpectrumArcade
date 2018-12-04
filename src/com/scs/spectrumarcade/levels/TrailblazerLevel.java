package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.trailblazer.Barrier_Trailblazer;
import com.scs.spectrumarcade.entities.trailblazer.TrailblazerAvatar;

import mygame.util.Vector3Int;
import ssmith.lang.Functions;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class TrailblazerLevel extends AbstractLevel implements ILevelGenerator {

	public static final boolean FOLLOW_CAM = true;

	public static final int MAP_HOLE = 1;
	public static final int MAP_WALL = 2;
	public static final int MAP_SPEED_UP = 3;
	public static final int MAP_SLOW_DOWN = 4;
	public static final int MAP_JUMP = 5;
	public static final int MAP_NUDGE_LEFT = 6;
	public static final int MAP_NUDGE_RIGHT = 7;

	//private static final int MAP_SIZE_X = 8;
	//private static final int MAP_SIZE_Z = 400;

	private VoxelTerrainEntity terrainUDG;
	private RealtimeInterval checkWinInt = new RealtimeInterval(100);
	private int levelNum;
	public int[][] map;
	private CameraSystem camSys;

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		camSys = new CameraSystem(game, FOLLOW_CAM, 2f);
		if (FOLLOW_CAM) {
			camSys.setupFollowCam(3, 0, true);
		}

		String text = Functions.readAllFileFromJar("maps/trailblazer_map1.csv");
		String[] lines = text.split("\n");

		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, lines.length, 100, 1f);
		game.addEntity(terrainUDG);

		int width = Integer.parseInt(lines[0].split(",")[0]);
		map = new int[width][lines.length-1];

		int lineNum = 0;
		int z=0;
		int nextCol = 0;
		for (String line : lines) {
			lineNum++;
			if (lineNum == 1) {
				continue; // Skip first line
			}
			String[] tokens = line.split(",");
			for (int x = 0 ; x<tokens.length-1 ; x++) {
				int id = Integer.parseInt(tokens[x]);
				map[x][z] = id;
				switch (id) {
				case 0:
					switch (nextCol) {
					case 0:
						terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.TRAILBLAZER_NORMAL1); // todo - use TB blocks!
						break;
					case 1:
						terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.TRAILBLAZER_NORMAL2);
						break;
					case 2:
						terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.TRAILBLAZER_NORMAL3);
						break;
					default:
						Globals.pe("Unhandled map type: " + nextCol);
						break;
					}
					nextCol++;
					if (nextCol > 2) {
						nextCol = 0;
					}
					break;
				case MAP_HOLE:
					terrainUDG.removeBlock(new Vector3Int(x, 0, z));
					break;
				case MAP_WALL:
					//terrainUDG.addBlock_Block(new Vector3Int(xGrid, 1, zGrid), BlockCodes.ANT_ATTACK); // todo - use TB blocks!
					Barrier_Trailblazer barrier = new Barrier_Trailblazer(game, x, z);
					game.addEntity(barrier);
					break;
				case MAP_SPEED_UP:
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.TRAILBLAZER_SPEED_UP);
					break;
				case MAP_SLOW_DOWN:
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.TRAILBLAZER_SLOW_DOWN);
					break;
				case MAP_JUMP:
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.TRAILBLAZER_JUMP);
					break;
				case MAP_NUDGE_LEFT:
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.TRAILBLAZER_NUDGE_LEFT);
					break;
				case MAP_NUDGE_RIGHT:
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.TRAILBLAZER_NUDGE_RIGHT);
					break;
				default:
					Globals.pe("Unhandled map type: " + id);
					break;
				}

			}
			z++;
		}



		/*
		map = new int[MAP_SIZE_X][MAP_SIZE_Z];
		// Add floor
		int id = 0;
		for (int zGrid=0 ; zGrid<MAP_SIZE_Z ; zGrid++) {
			for (int xGrid=0 ; xGrid<MAP_SIZE_X ; xGrid++) {
				switch (id) {
				case 0:
					terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.MOTOS_MAGENTA); // todo - use TB blocks!
					break;
				case 1:
					terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.MOTOS_CYAN);
					break;
				default:
					terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.MOTOS_YELLOW);
					id = -1;
					break;
				}
				id++;
			}
		}*/
		/*
		// Add problems
		for (int zGrid=3 ; zGrid<MAP_SIZE_Z ; zGrid++) {
			int xGrid = NumberFunctions.rnd(0, MAP_SIZE_X-1);
			int rnd = NumberFunctions.rnd(1, 4);
			map[xGrid][zGrid] = rnd;
			switch (rnd) {
			case 3:
				//terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.MOTOS_CYAN);
				break;
			case 4:
				//terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.MOTOS_YELLOW);
				break;
			default:
				//throw new RuntimeException("Todo");
			}
		}
		 */
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(map.length/2, 2f, 1f);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new TrailblazerAvatar(game, this, map.length/2, 2f, 1f, FOLLOW_CAM);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Blue;
	}


	@Override
	public void process(float tpfSecs) {
		camSys.process(game.getCamera(), game.player);

		if (checkWinInt.hitInterval()) {
			Vector3f pos = game.player.getMainNode().getWorldTranslation();
			// Check if player completed level
			if (pos.z > map[0].length) {
				game.setNextLevel(this.getClass(), levelNum++);
			}
		}
	}



	@Override
	public String getHUDText() {
		return "LEVEL: " + this.levelNum;
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, 1)), Vector3f.UNIT_Y);
	}


}
