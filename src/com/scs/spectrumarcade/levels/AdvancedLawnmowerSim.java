package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.ericandfloaters.ExplosionShard;
import com.scs.spectrumarcade.entities.minedout.Fence;
import com.scs.spectrumarcade.entities.minedout.SquareIndicator;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class AdvancedLawnmowerSim extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE_X = 30;
	private static final int MAP_SIZE_Z = 20;

	private VoxelTerrainEntity terrainUDG;
	private RealtimeInterval checkMinesInt = new RealtimeInterval(100);
	private int levelNum;

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE_X, 1f);
		game.addEntity(terrainUDG);

		terrainUDG.addRectRange_Blocks(BlockCodes.GRASS_LONG, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE_X, 1, MAP_SIZE_Z));

		// add fence
		for (int z=0; z<MAP_SIZE_Z ; z++) {
			for (int x=0; x<MAP_SIZE_X ; x++) {
				if (x == 0 || z == 0 || x == MAP_SIZE_X-1 || z == MAP_SIZE_Z-1) {
					if (z != MAP_SIZE_Z-1 || x != MAP_SIZE_X/2) { // No fence at exit
						Fence f = new Fence(game, x, z, (z == 0 || z == MAP_SIZE_Z-1) ? 0 : 90);
						game.addEntity(f);
					}
				}
			}
		}

	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(MAP_SIZE_X/2, 3f, 2f);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new WalkingPlayer(game, MAP_SIZE_X/2, 3f, 2f, false, false);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Cyan;
	}


	@Override
	public void process(float tpfSecs) {
		if (checkMinesInt.hitInterval()) {
			Vector3f pos = game.player.getMainNode().getWorldTranslation();
			int x = (int)pos.x;
			int z = (int)pos.z;
				this.terrainUDG.addBlock_Block(new Vector3Int((int)pos.x, 0, (int)pos.z), BlockCodes.MINED_OUT_WALKED_ON);

			// Check if player completed level
			if (pos.z > MAP_SIZE_Z) { // todo
				//this.levelNum++;
				//game.setLevel(this.getClass(), levelNum);
				game.setNextLevel(this.getClass(), levelNum++);
			}
		}
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
