package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.motos.AbstractMotosEnemyBall;
import com.scs.spectrumarcade.entities.motos.MotosAvatar;
import com.scs.spectrumarcade.entities.motos.MotosHeavyEnemy;
import com.scs.spectrumarcade.entities.motos.MotosSimpleEnemy;

import mygame.util.Vector3Int;

public class MotosLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE_BLOCKS = 22;
	public static final int SEGMENT_SIZE = 2;

	private int levelNum = 2;
	private VoxelTerrainEntity terrainUDG; // todo - delete
	private int boardsSizeActual;

	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		boardsSizeActual = MAP_SIZE_BLOCKS * SEGMENT_SIZE;
		int gridSize = MAP_SIZE_BLOCKS;

		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, gridSize, SEGMENT_SIZE);
		game.addEntity(terrainUDG);

		// Border - todo - remove
		/*terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(0, 1, 0), new Vector3Int(MAP_SIZE_BLOCKS, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(0, 1, 0), new Vector3Int(1, 1, MAP_SIZE_BLOCKS));
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(0, 1, MAP_SIZE_BLOCKS-1), new Vector3Int(MAP_SIZE_BLOCKS, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.SPLAT, new Vector3Int(MAP_SIZE_BLOCKS, 1, 0), new Vector3Int(1, 1, MAP_SIZE_BLOCKS));
		 */

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

		this.addBaddies();

	}


	private void addBaddies() {
		switch (levelNum) {
		case 1:
			// Baddies
			MotosSimpleEnemy mse = new MotosSimpleEnemy(game, this, boardsSizeActual/4, boardsSizeActual/4);
			game.addEntity(mse);
			MotosSimpleEnemy mse2 = new MotosSimpleEnemy(game, this, boardsSizeActual * .75f, boardsSizeActual * .75f);
			game.addEntity(mse2);
			break;

		case 2:
			MotosHeavyEnemy mse3 = new MotosHeavyEnemy(game, this, boardsSizeActual/4, boardsSizeActual/4);
			game.addEntity(mse3);
			break;


		}

	}


	@Override
	public Vector3f getAvatarStartPos() {
		float pos = boardsSizeActual / 2;
		return new Vector3f(pos, 4, pos);
	}


	@Override
	public Avatar createAndPositionAvatar() {
		float pos = boardsSizeActual / 2;
		MotosAvatar wp = new MotosAvatar(game, pos, 4f, pos);
		return wp;
	}



	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}


	@Override
	public void process(float tpfSecs) {
	}


	@Override
	public String getHUDText() {
		return "Level " + this.levelNum;
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
	}


	public void checkIfAllBaddiesDead() {
		boolean any = false;
		for (IEntity e : game.entities) {
			if (e instanceof AbstractMotosEnemyBall) {
				AbstractMotosEnemyBall enemy = (AbstractMotosEnemyBall)e;
				if (!enemy.isMarkedForRemoval()) {
					any = true;
					break;
				}
			}
		}
		if (!any) {
			this.levelNum++;
			this.addBaddies();
		}
	}
}
