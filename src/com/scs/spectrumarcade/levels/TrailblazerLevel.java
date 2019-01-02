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
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.trailblazer.TrailblazerAvatar;

import mygame.util.Vector3Int;
import ssmith.lang.Functions;
import ssmith.util.RealtimeInterval;

public class TrailblazerLevel extends AbstractLevel implements ILevelGenerator {

	public static final int MAP_HOLE = 1;
	public static final int MAP_WALL = 2;
	public static final int MAP_SPEED_UP = 3;
	public static final int MAP_SLOW_DOWN = 4;
	public static final int MAP_JUMP = 5;
	public static final int MAP_NUDGE_LEFT = 6;
	public static final int MAP_NUDGE_RIGHT = 7;

	private VoxelTerrainEntity terrainUDG;
	private RealtimeInterval checkWinInt = new RealtimeInterval(100);
	private int levelNum;
	public int[][] map;

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		if (Settings.TEST_BALL_ROLLING) {
			FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, 100, 2, 100, "Textures/blocks/antattack.png");
			game.addEntity(floor);

		} else {
			String text = Functions.readAllTextFileFromJar("data/maps/trailblazer_map1.csv");
			String[] lines = text.split("\n");

			terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, new Vector3Int(5, 2, lines.length), 100, 1f, 1f);
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
				for (int x = 0 ; x<tokens.length ; x++) {
					nextCol++;
					if (nextCol > 2) {
						nextCol = 0;
					}
					int id = Integer.parseInt(tokens[x]);
					map[x][z] = id;
					switch (id) {
					case 0:
						switch (nextCol) {
						case 0:
							terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.TRAILBLAZER_NORMAL1);
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
						break;
					case MAP_HOLE:
						terrainUDG.removeBlock(new Vector3Int(x, 0, z));
						break;
					case MAP_WALL:
						terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.TRAILBLAZER_NORMAL1);
						terrainUDG.addBlock_Block(new Vector3Int(x, 1, z), BlockCodes.TRAILBLAZER_NORMAL1);
						//Barrier_Trailblazer barrier = new Barrier_Trailblazer(game, x, z); Don't work
						//game.addEntity(barrier);
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
		}

	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(map.length/2, 2f, 1f);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		if (Settings.TEST_BALL_ROLLING) {
			return new TrailblazerAvatar(game, this, 10f, 2f, 1f);
		} else {
			return new TrailblazerAvatar(game, this, map.length/2, 2f, 1f);
		}
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Cyan;
	}


	@Override
	public void process(float tpfSecs) {
		if (checkWinInt.hitInterval()) {
			Vector3f pos = game.player.getMainNode().getWorldTranslation();
			// Check if player completed level
			if (!Settings.TEST_BALL_ROLLING) {
				if (pos.z > map[0].length) {
					game.setNextLevel(this.getClass(), levelNum++);
				}
			}
		}
	}

	
	@Override
	public String getHUDText() {
		return "LEVEL: " + this.levelNum;
	}


	@Override
	public void setupCameraSystem(CameraSystem sys) {
		sys.setupCam(3f, 0, true, 1f);
	}
	

}
