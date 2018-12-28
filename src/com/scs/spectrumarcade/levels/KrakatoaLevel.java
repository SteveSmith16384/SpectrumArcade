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
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.krakatoa.Cloud;
import com.scs.spectrumarcade.entities.krakatoa.Helipad;
import com.scs.spectrumarcade.entities.krakatoa.House;
import com.scs.spectrumarcade.entities.krakatoa.KrakatoaHeliAvatar;
import com.scs.spectrumarcade.entities.krakatoa.LavaRock;
import com.scs.spectrumarcade.entities.krakatoa.Missile;
import com.scs.spectrumarcade.entities.krakatoa.Submarine;
import com.scs.spectrumarcade.entities.krakatoa.Tanker;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class KrakatoaLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 500;
	private static final int MAP_BORDER = 50;

	private VoxelTerrainEntity terrainUDG;
	private int levelNum;
	private RealtimeInterval lavaInt = new RealtimeInterval(500);
	private float time = 0.0f;
	private float waterHeight = 0.0f;
	WaterFilter water;
	private float initialWaterHeight = 0.8f;
	private Tanker tanker;
	private FilterPostProcessor fpp;
	
	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, -4, 0, MAP_SIZE+(MAP_BORDER*2), 1, MAP_SIZE+(MAP_BORDER*2), "Textures/krakatoa/water2.png"); // make water a better colour?
		floor.geometry.setShadowMode(ShadowMode.Off);

		game.addEntity(floor);

		terrainUDG = new VoxelTerrainEntity(game, 0, 0f, 0, new Vector3Int(MAP_SIZE+(MAP_BORDER*2), 32, MAP_SIZE+(MAP_BORDER*2)), 16, 1f, 1f);
		game.addEntity(terrainUDG);

		// Start island
		this.generateHill(new Vector3f(100, 50, 100), 15, 12);
		House h = new House(game, 94, 13, 100, false);
		game.addEntity(h);
		// Helipad
		//terrainUDG.addRectRange_Blocks(BlockCodes.ROAD, new Vector3Int(98, 12, 98), new Vector3Int(4, 1, 4));
		Helipad pad = new Helipad(game, 98, 13, 98);
		game.addEntity(pad);
		
		this.generateHill(new Vector3f(105, 50, 110), 10, 9); // Hill 2
		this.generateHill(new Vector3f(97, 50, 117), 7, 6); // Hill 3

		tanker = new Tanker(game, 100, 1, 175);
		game.addEntity(tanker);

		Submarine sub = new Submarine(game, 70, -1, 165);
		game.addEntity(sub);

		// Volcano
		generateVolcano(new Vector3f(100, 30, 230), 20);
		this.generateHill(new Vector3f(100, 50, 240), 18, 12); // Hill 3
		this.generateHill(new Vector3f(100, 22, 250), 20, -1); // Hill 2
		House h1 = new House(game, 92, 13, 240, false);
		game.addEntity(h1);
		House h2 = new House(game, 105, 13, 242, false);
		game.addEntity(h2);
		House h3 = new House(game, 110, 13, 247, true);
		game.addEntity(h3);


		// Island 1
		this.generateHill(new Vector3f(110, 15, 300), 20, 12);
		this.generateHill(new Vector3f(95, 10, 310), 20, 8);


		// Water
		fpp = new FilterPostProcessor(game.getAssetManager());
		water = new WaterFilter(game.getRootNode(), game.sun.getDirection());
		water.setWaterHeight(1f);
		fpp.addFilter(water);
		game.getViewPort().addProcessor(fpp);

		// Clouds
		for (int i=0 ; i<10 ; i++) {
			float x = NumberFunctions.rndFloat(0,  MAP_SIZE);
			float y = NumberFunctions.rndFloat(50,  70);
			float z = NumberFunctions.rndFloat(0,  MAP_SIZE);
			float scale = NumberFunctions.rndFloat(4,  8);
			Cloud cloud = new Cloud(game, x, y, z, scale);
			game.addEntity(cloud);
		}
/*
		// Fog
        FilterPostProcessor fpp2 = new FilterPostProcessor(game.getAssetManager());
        FogFilter fog=new FogFilter();
        fog.setFogColor(new ColorRGBA(0.9f, 0.9f, 0.9f, 1.0f));
        fog.setFogDistance(150);
        fog.setFogDensity(1f);
        fpp.addFilter(fog);
        game.getViewPort().addProcessor(fpp2);
*/
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(100, 22f, 100);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new KrakatoaHeliAvatar(game, getAvatarStartPos(), "Textures/krakatoa/heli_red.png");
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
		if (lavaInt.hitInterval()) {
			if (NumberFunctions.rnd(1,  3) == 1) {
				LavaRock lava = new LavaRock(game, 100, 28, 230);
				game.addEntity(lava);
			}
			if (NumberFunctions.rnd(1,  3) == 1) {
				Missile m = new Missile(game, 100, 40, 300);
				game.addEntity(m);
			}
		}

		// Water level
		time += tpfSecs;
		waterHeight = (float) Math.cos(((time * 0.6f) % FastMath.TWO_PI)) * 1.5f;
		water.setWaterHeight(initialWaterHeight + waterHeight);
		tanker.getMainNode().setLocalTranslation(tanker.getMainNode().getLocalTranslation().x, water.getWaterHeight(), tanker.getMainNode().getLocalTranslation().z);
	}



	@Override
	public String getHUDText() {
		return "";
	}

/*
	@Override
	public void setInitialCameraDir(Camera cam) {
		//cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, 1)), Vector3f.UNIT_Y);
	}
*/

	public void setupCameraSystem(CameraSystem sys) {
		sys.setupCam(10f, 0f, true, 3f);
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


	private void generateVolcano(Vector3f peak, int rad) {
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
						height = height + (NumberFunctions.rnd(-2, 0));
						int blockType = BlockCodes.GRASS_LONG;
						if (dist < 4) {
							height = height - (int)(8f-dist*2);
							blockType = BlockCodes.LAVA;
						}
						for (int y=0 ; y<= height ; y++) {
							this.terrainUDG.addRectRange_Blocks(blockType, new Vector3Int(x, 1, z), new Vector3Int(1, height, 1));
						}
					}
				}
			}			
		}
	}
	
	
	@Override
	public void remove() {
		game.getViewPort().removeProcessor(fpp);
		
	}



}
