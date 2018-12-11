package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.HeliAvatar;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;

import mygame.util.Vector3Int;

public class EscapeFromKrakatoa extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 300;

	private VoxelTerrainEntity terrainUDG;
	private int levelNum;

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE, 16, 1f, 1f);
		game.addEntity(terrainUDG);

		terrainUDG.addRectRange_Blocks(BlockCodes.GRASS_LONG, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, MAP_SIZE));

	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(MAP_SIZE/2, 3f, MAP_SIZE/2);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		return new HeliAvatar(game, MAP_SIZE/2, 3f, MAP_SIZE/2);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Cyan;
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
