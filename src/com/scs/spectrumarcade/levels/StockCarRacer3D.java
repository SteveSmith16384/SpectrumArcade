package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.minedout.Fence;
import com.scs.spectrumarcade.entities.stockcarracer.StockCarAvatar;

import mygame.util.Vector3Int;

public class StockCarRacer3D extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;

	private VoxelTerrainEntity terrainUDG;


	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE, 1f);
		game.addEntity(terrainUDG);

		terrainUDG.addRectRange_Blocks(BlockCodes.MINED_OUT_FRESH, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, MAP_SIZE));

		// add fence
		for (int z=0; z<MAP_SIZE ; z++) {
			for (int x=0; x<MAP_SIZE ; x++) {
				if (x == 0 || z == 0 || x == MAP_SIZE-1 || z == MAP_SIZE-1) {
					Fence f = new Fence(game, x, z, (z == 0 || z == MAP_SIZE-1) ? 0 : 90);
					game.addEntity(f);
				}
			}
		}

	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(MAP_SIZE/2, 3f, 2f);
	}


	@Override
	public Avatar createAndPositionAvatar() {
		return new StockCarAvatar(game, MAP_SIZE/2, 3f, 2f);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Blue;
	}


	@Override
	public void process(float tpfSecs) {
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
