package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.motos.AbstractMotosEnemyBall;
import com.scs.spectrumarcade.entities.motos.MotosAvatar;
import com.scs.spectrumarcade.entities.motos.MotosHeavyEnemy;
import com.scs.spectrumarcade.entities.motos.MotosSimpleEnemy;
import com.scs.spectrumarcade.entities.motos.MotosSuperHeavyEnemy;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class MotosLevel extends AbstractLevel implements ILevelGenerator {

	public static final boolean FOLLOW_CAM = true;

	private static final int MAP_SIZE_BLOCKS = 22;
	public static final int SEGMENT_SIZE = 2;
	public static final float FALL_DIST = -20f;

	private int levelNum;
	private VoxelTerrainEntity terrainUDG;
	private int boardsSizeActual;
	private RealtimeInterval checkEndfLevelInt = new RealtimeInterval(4000);
	private CameraSystem camSys;

	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		camSys = new CameraSystem(game, FOLLOW_CAM, -1f);
		if (FOLLOW_CAM) {
			camSys.setupFollowCam(3, 0);
		}

		boardsSizeActual = MAP_SIZE_BLOCKS * SEGMENT_SIZE;
		int gridSize = MAP_SIZE_BLOCKS;

		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, gridSize, SEGMENT_SIZE);
		game.addEntity(terrainUDG);

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
			MotosSimpleEnemy mse = new MotosSimpleEnemy(game, this, boardsSizeActual/4, boardsSizeActual/4);
			game.addEntity(mse);
			MotosSimpleEnemy mse2 = new MotosSimpleEnemy(game, this, boardsSizeActual * .75f, boardsSizeActual * .75f);
			game.addEntity(mse2);
			MotosSimpleEnemy mse3 = new MotosSimpleEnemy(game, this, boardsSizeActual * .75f, boardsSizeActual /4);
			game.addEntity(mse3);
			break;

		case 2:
			MotosHeavyEnemy mseb3 = new MotosHeavyEnemy(game, this, boardsSizeActual/4, boardsSizeActual/4);
			game.addEntity(mseb3);
			break;

		case 3:
			MotosSuperHeavyEnemy mssb3 = new MotosSuperHeavyEnemy(game, this, boardsSizeActual/4, boardsSizeActual/4);
			game.addEntity(mssb3);
			break;

		default:
			throw new RuntimeException("No such level: " + this.levelNum);
		}

		//if (levelNum > 1) { todo - re-add
			for (int i=0 ; i<levelNum*5 ; i++) {
				int x = NumberFunctions.rnd(1, MAP_SIZE_BLOCKS-2);
				int z = NumberFunctions.rnd(1, MAP_SIZE_BLOCKS-2);
				terrainUDG.removeBlock(new Vector3Int(x, 0, z));

			}
		//}

	}


	@Override
	public Vector3f getAvatarStartPos() {
		float pos = boardsSizeActual / 2;
		return new Vector3f(pos, 4, pos);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		float pos = boardsSizeActual / 2;
		MotosAvatar wp = new MotosAvatar(game, pos, 4f, pos, FOLLOW_CAM);
		return wp;
	}



	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}


	@Override
	public void process(float tpfSecs) {
		camSys.process(game.getCamera(), game.player);
		
		if (checkEndfLevelInt.hitInterval()) {
			this.checkIfAllBaddiesDead();
		}
	}


	@Override
	public String getHUDText() {
		return "Level " + this.levelNum;
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		// Do nothing
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
			if (this.game.player.getMainNode().getWorldTranslation().y > -1) { //Check player not fallen off edge
				this.levelNum++;
				// Restart in-place
				game.setLevel(this.getClass(), levelNum);
				this.addBaddies();
			}
		}
	}
}
