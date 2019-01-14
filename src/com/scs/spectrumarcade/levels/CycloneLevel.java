package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.krakatoa.Cloud;
import com.scs.spectrumarcade.entities.krakatoa.KrakatoaHeliAvatar;

import mygame.util.Vector3Int;
import ssmith.lang.Functions;
import ssmith.lang.NumberFunctions;

public class CycloneLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 200;
	private static final int MAP_BORDER = 50;

	private VoxelTerrainEntity terrainUDG;
	private int levelNum;
	//private RealtimeInterval lavaInt = new RealtimeInterval(500);

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE+(MAP_BORDER*2), 1, MAP_SIZE+(MAP_BORDER*2), "Textures/krakatoa/water2.png"); // make water a better colour?
		game.addEntity(floor);

		terrainUDG = new VoxelTerrainEntity(game, 0, 0f, 0, new Vector3Int(MAP_SIZE+(MAP_BORDER*2), 20, MAP_SIZE+(MAP_BORDER*2)), 16, 1f, 1f);
		game.addEntity(terrainUDG);

		int mx=0;
		int mz = 0;
		byte b[] = Functions.readAllBinaryFileFromJar("maps/cyclone/Base Island.raw");
		for (int i=0 ; i<b.length ; i++) {
			mx++;
			if (i % 150 == 0) {
				System.out.println("");
				mx = 0;
				mz++;
			}
			int c = b[i]+48;
			char ch = (char)c;
			System.out.print(b[i] + ",");

			try {
				while (b[i] > 20) {
					b[i] = (byte)(b[i]/2);
				}
				if (b[i] > 0 && b[i] < 20) {
					this.terrainUDG.addRectRange_Blocks(BlockCodes.GRASS_LONG, new Vector3Int(mx, 1, mz), new Vector3Int(1, b[i], 1));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			/*
			if (b[i] == 0) {
				System.out.print("W");
			} else if (b[i] == 1) {
				System.out.print("1");
			} else if (b[i] == 2) {
				System.out.print("2");
			} else {
			}*/
		}
		/*
		// Start island
		this.generateHill(new Vector3f(100, 50, 100), 20, 15);
		House h = new House(game, 94, 21, 100, false);
		game.addEntity(h);
		// Helipad
		terrainUDG.addRectRange_Blocks(BlockCodes.ROAD, new Vector3Int(98, 20, 98), new Vector3Int(4, 1, 4));
		 */
/*
		// Water
		FilterPostProcessor fpp = new FilterPostProcessor(game.getAssetManager());
		WaterFilter water = new WaterFilter(game.getRootNode(), game.sun.getDirection());
		water.setWaterHeight(1f);
		//water.setWaveScale(waveScale);
		fpp.addFilter(water);
		game.getViewPort().addProcessor(fpp);
*/
		// Clouds
		for (int i=0 ; i<10 ; i++) {
			float x = NumberFunctions.rndFloat(0,  MAP_SIZE);
			float y = NumberFunctions.rndFloat(50,  70);
			float z = NumberFunctions.rndFloat(0,  MAP_SIZE);
			float scale = NumberFunctions.rndFloat(4,  8);
			Cloud cloud = new Cloud(game, x, y, z, scale);
			game.addEntity(cloud);
		}

	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(100, 22f, 100);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new KrakatoaHeliAvatar(game, getAvatarStartPos(), "Textures/cyclone/heli_black.png");
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Cyan;
	}


	@Override
	public void process(float tpfSecs) {
		// World wrap
		Vector3f pos = game.player.getMainNode().getWorldTranslation();
		Vector3f newPos = pos.clone(); // todo - dcet
		boolean move = false;
		if (pos.x < 0) {
			move = true;
			newPos.x += MAP_SIZE;
		} else if (pos.x > MAP_SIZE) {
			move = true;
			newPos.x -= MAP_SIZE;
		}
		if (pos.z < 0) {
			move = true;
			newPos.z += MAP_SIZE;
		} else if (pos.z > MAP_SIZE) {
			move = true;
			newPos.z -= MAP_SIZE;
		}
		if (pos.y > 70) {
			move = true;
			newPos.y = 70;
		}
		if (move) {
			Globals.p("Warping player");
			IAvatar p = (IAvatar)game.player;
			p.warp(newPos);
		}

	}



	@Override
	public String getHUDText() {
		return "";
	}

	/*
	@Override
	public void setInitialCameraDir(Camera cam) {
	}
	 */

	public void setupCameraSystem(CameraSystem sys) {
		sys.setupCam(6f, 0f, false, 3f);
	}



	private void generateHill(Vector3f peak, int rad, int plateau) {
		for (int z = (int)peak.z - rad ; z<=peak.z+rad ; z++) {
			for (int x = (int)peak.x - rad ; x<=peak.x+rad ; x++) {
				/*if (x == peak.x && z == peak.z) {
					int dfgdg = 56;
				}*/
				float dist = peak.distance(new Vector3f(x, peak.y, z));
				if (dist <= rad) {
					float frac = (dist+1) / rad;
					int height = (int)(peak.y - (peak.y * frac));
					if (height > 0) {
						if (plateau > 0 && height > plateau) {
							height = plateau;
						} else {
							height = height + (NumberFunctions.rnd(-1, 0));
						}
						for (int y=0 ; y<= height ; y++) {
							int blockType = BlockCodes.GRASS_LONG;
							if (y <= 2) {
								blockType = BlockCodes.SAND;
							}
							this.terrainUDG.addRectRange_Blocks(blockType, new Vector3Int(x, 1, z), new Vector3Int(1, height, 1));
						}
					}
				}
			}			
		}
	}


}
