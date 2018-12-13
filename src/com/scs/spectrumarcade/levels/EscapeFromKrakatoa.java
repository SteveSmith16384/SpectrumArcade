package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.water.WaterFilter;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.HeliAvatar;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.krakatoa.House;
import com.scs.spectrumarcade.entities.krakatoa.LavaRock;
import com.scs.spectrumarcade.entities.krakatoa.Tanker;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class EscapeFromKrakatoa extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 300;
	private static final int MAP_BORDER = 50;

	private VoxelTerrainEntity terrainUDG;
	private int levelNum;
	private RealtimeInterval lavaInt = new RealtimeInterval(1000);

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		terrainUDG = new VoxelTerrainEntity(game, -MAP_BORDER, 0f, -MAP_BORDER, MAP_SIZE+(MAP_BORDER*2), 16, 1f, 1f);
		game.addEntity(terrainUDG);

		// Start island
		this.generateHill(new Vector3f(150, 10, 150), 20, 5);
		House h = new House(game, 144, 6, 150);
		game.addEntity(h);
		// Helipad
		terrainUDG.addRectRange_Blocks(BlockCodes.ROAD, new Vector3Int(148, 5, 148), new Vector3Int(4, 1, 4));

		Tanker tanker = new Tanker(game, 170, 1, 150);
		game.addEntity(tanker);

		// Island 1
		this.generateHill(new Vector3f(110, 10, 110), 20, -1);

		// Island 2
		this.generateHill(new Vector3f(170, 10, 110), 20, 5);
		this.generateHill(new Vector3f(175, 10, 115), 20, 7);

		// Volcano
		this.generateHill(new Vector3f(50, 20, 50), 20, -1);
		this.generateHill(new Vector3f(55, 20, 50), 20, -1);
		this.generateHill(new Vector3f(50, 20, 55), 20, -1);
		this.generateHill(new Vector3f(55, 20, 55), 20, -1);



		//terrainUDG.addRectRange_Blocks(BlockCodes.GRASS_LONG, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, MAP_SIZE));

		// Water
		FilterPostProcessor fpp = new FilterPostProcessor(game.getAssetManager());
		WaterFilter water = new WaterFilter(game.getRootNode(), game.sun.getDirection());
		water.setWaterHeight(1);
		fpp.addFilter(water);
		game.getViewPort().addProcessor(fpp);

	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(MAP_SIZE/2, 8f, MAP_SIZE/2);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new HeliAvatar(game, MAP_SIZE/2, 8f, MAP_SIZE/2, "Textures/krakatoa/heli_yellow.png");
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
		if (move) {
			Globals.p("Warping player");
			IAvatar p = (IAvatar)game.player;
			p.warp(newPos);
		}
		
		if (lavaInt.hitInterval()) {
			LavaRock lava = new LavaRock(game, 52, 20, 52);
			game.addEntity(lava);
		}
	}



	@Override
	public String getHUDText() {
		return "";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		//cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, 1)), Vector3f.UNIT_Y);
	}


	public void setupCameraSystem(CameraSystem sys) {
		sys.setupCam(true, 6f, 0f, false, 3f);
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
							//try {
							this.terrainUDG.addRectRange_Blocks(blockType, new Vector3Int(x, 1, z), new Vector3Int(1, height, 1));
							/*} catch (java.lang.ArrayIndexOutOfBoundsException ex) {
							ex.printStackTrace();
						}*/
						}
					}
				}
			}			
		}
	}
}
