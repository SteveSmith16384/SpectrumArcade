package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.krakatoa.Cloud;
import com.scs.spectrumarcade.entities.tomahawk.Hill;
import com.scs.spectrumarcade.entities.tomahawk.TomahawkHeliAvatar;

import ssmith.lang.NumberFunctions;

public class TomahawkLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 500;
	private static final int MAP_SECTION_SIZE = MAP_SIZE/10;

	private VoxelTerrainEntity terrainUDG;
	private int levelNum;
	//private RealtimeInterval lavaInt = new RealtimeInterval(500);

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

//		terrainUDG = new VoxelTerrainEntity(game, 0, 1f, 0, new Vector3Int(MAP_SIZE, 32, MAP_SIZE), 16, 1f, 1f);
//		game.addEntity(terrainUDG);

		Hill hill = new Hill(game, 250, 0, 260, 10, 5, "Textures/blocks/grass.jpg");
		game.addEntity(hill);
		
		// Scenery
		int sections = MAP_SIZE/MAP_SECTION_SIZE;
		for (int z=0 ; z<sections ; z++) {
			for (int x=0 ; x<sections ; x++) {
				FloorOrCeiling floor = new FloorOrCeiling(game, x*MAP_SECTION_SIZE, 0, z*MAP_SECTION_SIZE, MAP_SECTION_SIZE, 1, MAP_SECTION_SIZE, "Textures/blocks/grass.jpg");
				floor.geometry.setShadowMode(ShadowMode.Off);
				game.addEntity(floor);

				int sx = NumberFunctions.rnd(x*MAP_SECTION_SIZE, x*MAP_SECTION_SIZE+MAP_SECTION_SIZE);
				int sz = NumberFunctions.rnd(z*MAP_SECTION_SIZE, z*MAP_SECTION_SIZE+MAP_SECTION_SIZE);
				int h = NumberFunctions.rnd(4, 20);
				int rad = NumberFunctions.rnd(h, h*2);
				//this.generateHill(new Vector3f(sx, h, sz), rad, -1);
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
		return new TomahawkHeliAvatar(game, MAP_SIZE/2, 3f, MAP_SIZE/2, "Textures/tomahawk/heli_green.png");
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
		sys.setupCam(6f, 0f, false, 3f);
	}


}
