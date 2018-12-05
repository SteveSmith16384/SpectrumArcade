package com.scs.spectrumarcade.levels;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.abilities.BombGun_AA;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.TextBillboardEntity;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.antattack.Ant;
import com.scs.spectrumarcade.entities.antattack.Damsel;

import mygame.util.Vector3Int;
import ssmith.lang.Functions;
import ssmith.lang.NumberFunctions;

public class AntAttackLevel extends AbstractLevel implements ILevelGenerator {

	public static final boolean FOLLOW_CAM = true;

	private static int MAP_BORDER = 10;
	private static int MAP_SIZE = 128;

	private CameraSystem camSys;

	public AntAttackLevel() {
		super();
	}


	@Override
	public void generateLevel(SpectrumArcade game, int levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		if (Settings.TEST_ANT_AI) {
			MAP_SIZE = 20;
		}

		camSys = new CameraSystem(game, FOLLOW_CAM, 2f);
		if (FOLLOW_CAM) {
			camSys.setupFollowCam(3, 0, true);
		}

		FloorOrCeiling floor = new FloorOrCeiling(game, -MAP_BORDER, 0, -MAP_BORDER, MAP_SIZE+(MAP_BORDER*2), 2, MAP_SIZE+(MAP_BORDER*2), "Textures/blocks/white.png");
		game.addEntity(floor);

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE+2, 1f);
		game.addEntity(terrainUDG);

		if (Settings.TEST_ANT_AI) {
			// Border
			terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, 1));
			terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
			terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, MAP_SIZE-1), new Vector3Int(MAP_SIZE, 1, 1));
			terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(MAP_SIZE, 0, 0), new Vector3Int(1, 1, MAP_SIZE));

			// Platform
			terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(5, 0, 5), new Vector3Int(2, 1, 2));

			// Add ants
			Ant ant = new Ant(game, 10, 2, 10); // Make height unique to stop collisions at start
			game.addEntity(ant);

		} else {
			String text = Functions.readAllFileFromJar("maps/antattack_map.txt");
			String[] lines = text.split("\n");

			for (String line : lines) {
				String[] parts = line.split(",");
				int x = Integer.parseInt(parts[0]);
				int y = Integer.parseInt(parts[1]);
				int z = Integer.parseInt(parts[2]);
				terrainUDG.addBlock_Block(new Vector3Int(x, y, z), BlockCodes.ANT_ATTACK);
			}


			// Add ants
			for (int i=0 ; i<5 ; i++) {
				int x = NumberFunctions.rnd(10, MAP_SIZE-10);
				int z = NumberFunctions.rnd(10, MAP_SIZE-10);
				Ant ant = new Ant(game, x, 9, z); // Make height unique to stop collisions at start
				game.addEntity(ant);
			}
		}

		// Add damsel
		int x = NumberFunctions.rnd(3, MAP_SIZE-4); // todo - use this
		int z = NumberFunctions.rnd(3, MAP_SIZE-4);
		Damsel key = new Damsel(game, 54, 110);
		game.addEntity(key);
		
		if (Settings.TEST_BILLBOARD) {
			TextBillboardEntity be = new TextBillboardEntity(game, 54, 2f, 124f);
			game.addEntity(be);
		}


	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(54, 2f, 124f);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		if (Settings.AA_FIND_START) {
			WalkingPlayer wp = new WalkingPlayer(game, 0, 2f, 0, true, FOLLOW_CAM);
			game.setAbility(1, new BombGun_AA(game));
			return wp;
		} else {
			WalkingPlayer wp = new WalkingPlayer(game, 54, 2f, 124, true, FOLLOW_CAM);
			game.setAbility(1, new BombGun_AA(game));
			return wp;
		}
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Cyan;
	}


	@Override
	public void process(float tpfSecs) {
		camSys.process(game.getCamera(), game.player);

		if (Settings.AA_FIND_START) {
			Globals.p("Avatar Pos: " + game.player.getMainNode().getWorldTranslation());
		}

	}


	@Override
	public String getHUDText() {
		return "";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, -1)), Vector3f.UNIT_Y);
	}

}
