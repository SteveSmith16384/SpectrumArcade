package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.motos.MotosAvatar;
import com.scs.spectrumarcade.entities.motos.MotosSimpleEnemy;

import mygame.util.Vector3Int;

public class MotosLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE_BLOCKS = 22;
	public static final int SEGMENT_SIZE = 2;

	private VoxelTerrainEntity terrainUDG; // todo - delete

	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		int gridSize = MAP_SIZE_BLOCKS;//MAP_SIZE/SEGMENT_SIZE;

		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, gridSize, SEGMENT_SIZE);
		game.addEntity(terrainUDG);

		//terrainUDG.addRectRange_Blocks(BlockCodes.MOTOS_MAGENTA, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE_BLOCKS, 1, MAP_SIZE_BLOCKS));

		// Add solid walls
		int id = 0;
		for (int zGrid=0 ; zGrid<gridSize ; zGrid++) {
			for (int xGrid=0 ; xGrid<gridSize ; xGrid++) {
				switch (id) {
				case 0:
					terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.MOTOS_MAGENTA);
					break;
				case 1:
					terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.MOTOS_CYAN);
					break;
				case 2:
					terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.MOTOS_YELLOW);
					break;
				case 3:
					terrainUDG.addBlock_Block(new Vector3Int(xGrid, 0, zGrid), BlockCodes.MOTOS_WHITE);
					id = -1;
					break;
				default:
					throw new RuntimeException("Todo");
				}
				id++;
			}
		}

		// Baddies
		//MotosSimpleEnemy mse = new MotosSimpleEnemy(game, 2, 2);
		//game.addEntity(mse);

	}


	@Override
	public Vector3f getAvatarStartPos() {
		float pos = MAP_SIZE_BLOCKS / SEGMENT_SIZE / 2;
		return new Vector3f(pos, MotosLevel.SEGMENT_SIZE+2f, pos);
	}


	@Override
	public Avatar createAndPositionAvatar() {
		float pos = MAP_SIZE_BLOCKS / SEGMENT_SIZE / 2;
		MotosAvatar wp = new MotosAvatar(game, pos, MotosLevel.SEGMENT_SIZE+2f, pos);
		return wp;
	}



	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}


	@Override
	public void process(float tpfSecs) {
		// Do nothing
	}


	@Override
	public String getHUDText() {
		return "";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
	}

}
