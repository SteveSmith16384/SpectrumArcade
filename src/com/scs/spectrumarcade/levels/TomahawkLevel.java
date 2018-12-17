package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.water.WaterFilter;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.HeliAvatar;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.krakatoa.Cloud;
import com.scs.spectrumarcade.entities.krakatoa.House;
import com.scs.spectrumarcade.entities.krakatoa.LavaRock;
import com.scs.spectrumarcade.entities.krakatoa.Missile;
import com.scs.spectrumarcade.entities.krakatoa.Submarine;
import com.scs.spectrumarcade.entities.krakatoa.Tanker;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class TomahawkLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 500;
	private static final int MAP_SECTION_SIZE = MAP_SIZE/10;

	private VoxelTerrainEntity terrainUDG;
	private int levelNum;
	//private RealtimeInterval lavaInt = new RealtimeInterval(500);

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, -4, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/tomahawk/heli_green.png"); // make water a better colour?
		floor.geometry.setShadowMode(ShadowMode.Off);

		game.addEntity(floor);

		terrainUDG = new VoxelTerrainEntity(game, 0, 1f, 0, MAP_SIZE, 32, 1f, 1f);
		game.addEntity(terrainUDG);

		// Helipad
		terrainUDG.addRectRange_Blocks(BlockCodes.ROAD, new Vector3Int(MAP_SIZE, 0, MAP_SIZE), new Vector3Int(4, 1, 4));

		// Scenery
		int sections = MAP_SIZE/MAP_SECTION_SIZE;
		for (int z=1 ; z<sections-1 ; z++) {
			for (int x=1 ; x<sections-1 ; x++) {
				int sx = NumberFunctions.rnd(x*MAP_SECTION_SIZE, x*MAP_SECTION_SIZE+MAP_SECTION_SIZE);
				int sz = NumberFunctions.rnd(z*MAP_SECTION_SIZE, z*MAP_SECTION_SIZE+MAP_SECTION_SIZE);
				int h = NumberFunctions.rnd(4, 40);
				int rad = NumberFunctions.rnd(h, h*2);
				this.generateHill(new Vector3f(sx, h, sz), rad, -1);
			}
		}

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
		return new Vector3f(MAP_SIZE/2, 3f, MAP_SIZE/2);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new HeliAvatar(game, MAP_SIZE/2, 3f, MAP_SIZE/2, "Textures/tomahawk/heli_green.png");
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

		// Lava
		//if (lavaInt.hitInterval()) {
		//}

	}



	@Override
	public String getHUDText() {
		return "";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
	}


	public void setupCameraSystem(CameraSystem sys) {
		sys.setupCam(true, 6f, 0f, false, 3f);
	}



	private void generateHill(Vector3f peak, int rad, int plateau) {
		for (int z = (int)peak.z - rad ; z<=peak.z+rad ; z++) {
			for (int x = (int)peak.x - rad ; x<=peak.x+rad ; x++) {
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
						int y = height;
						//for (int y=0 ; y<= height ; y++) {
							int blockType = BlockCodes.GRASS_LONG;
							if (y <= 2) {
								blockType = BlockCodes.SAND;
							}
							try {
								this.terrainUDG.addRectRange_Blocks(blockType, new Vector3Int(x, 1, z), new Vector3Int(1, height, 1));
							} catch (java.lang.ArrayIndexOutOfBoundsException ex) {
								ex.printStackTrace();
							}
						//}
					}
				}
			}			
		}
	}


}
