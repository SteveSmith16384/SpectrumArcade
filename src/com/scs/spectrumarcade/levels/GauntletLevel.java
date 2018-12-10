package com.scs.spectrumarcade.levels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.abilities.GauntletAxeThrower;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.gauntlet.Ghost_Gauntlet;
import com.scs.spectrumarcade.entities.manicminer.Key;

import mygame.util.Vector3Int;

public class GauntletLevel extends AbstractLevel implements ILevelGenerator {

	public static final boolean FOLLOW_CAM = true;
	private static final int WALL_HEIGHT = 4;

	private int levelNum;

	public GauntletLevel() {
		super();
	}


	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		BufferedImage image = ImageIO.read(new File("gauntlet_level" + levelNum + ".png"));

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, image.getWidth(), 1, image.getHeight(), "Textures/mud.png");
		game.addEntity(floor);

		FloorOrCeiling ceiling = new FloorOrCeiling(game, 0, WALL_HEIGHT+1, 0, image.getWidth(), 1, image.getHeight(), "Textures/mud.png");
		game.addEntity(ceiling);

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, image.getWidth(), 16, 1f, 1f);
		game.addEntity(terrainUDG);

		for (int z=0 ; z<image.getHeight() ; z++) {
			for (int x=0 ; x<image.getHeight() ; x++) {
				int col = image.getRGB(x, z);
				switch (col) {
				case -12566528: // Wall
					//terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.EATF_SOLID);
					terrainUDG.addRectRange_Blocks(BlockCodes.GAUNTLET_WALL, new Vector3Int(x, 0, z), new Vector3Int(1, WALL_HEIGHT, 1));
					break;
				case -6258592: // floor					
					break;
				case -4145152: // door?
					//terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.GAUNTLET_DOOR);
					terrainUDG.addRectRange_Blocks(BlockCodes.GAUNTLET_DOOR, new Vector3Int(x, 0, z), new Vector3Int(1, WALL_HEIGHT, 1));
					break;
				case -16744448: // Monster gen?
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.ANT_ATTACK);
					break;
				case -16715776: //treasure?
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.CONVEYOR);
					break;
				case -16744336: // food?
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.STOCK_CAR_WALL_CYAN);
					break;
				case -1048576: // dunno
					//terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.);
					break;
				case -16744352: // dunno
					Key key = new Key(game, x, 1, z);
					game.addEntity(key);
					break;
				case -16744368: // dunno
					break;
				case -16760832:  // dunno
					break;
				default:
					Globals.p("Unknown colour at " + x + "," + z + ":" + col);
				}

			}
		}

		for (int z=5 ; z<10 ; z++) {
			for (int x=5 ; x<10 ; x++) {
				Ghost_Gauntlet g = new Ghost_Gauntlet(game, x, z);
				game.addEntity(g);
			}
		}
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(2f, 2f, 3f);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		WalkingPlayer wp = new WalkingPlayer(game, 2, 2f, 3f, 5f, FOLLOW_CAM, "Textures/gauntlet/todo.png");
		game.setAbility(1, new GauntletAxeThrower(game));
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
		cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, 1)), Vector3f.UNIT_Y);
	}

}
