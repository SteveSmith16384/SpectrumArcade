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
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.abilities.LaserRifle;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;

import mygame.util.Vector3Int;

public class AndroidsLevel extends AbstractLevel implements ILevelGenerator {

	public static final boolean FOLLOW_CAM = true;
	private static final int WALL_HEIGHT = 4;

	private int levelNum;

	public AndroidsLevel() {
		super();
	}


	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		BufferedImage image = ImageIO.read(new File("androids_map.png"));

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, image.getWidth(), 1, image.getHeight(), "Textures/mud.png"); // todo - tex and size
		game.addEntity(floor);

		FloorOrCeiling ceiling = new FloorOrCeiling(game, 0, WALL_HEIGHT+1, 0, image.getWidth(), 1, image.getHeight(), "Textures/mud.png"); // todo
		game.addEntity(ceiling);

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, image.getWidth(), 1f);
		game.addEntity(terrainUDG);

		for (int z=0 ; z<image.getHeight() ; z++) {
			for (int x=0 ; x<image.getHeight() ; x++) {
				int col = image.getRGB((x*20)+10, (z*20)+10);
				switch (col) {
				case -12566528: // Wall
					//terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.EATF_SOLID);
					terrainUDG.addRectRange_Blocks(BlockCodes.GAUNTLET_WALL, new Vector3Int(x, 0, z), new Vector3Int(1, WALL_HEIGHT, 1));
					break;

				default:
					Globals.p("Unknown colour at " + x + "," + z + ":" + col);
				}

			}
		}
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(2f, 2f, 3f); // todo
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		WalkingPlayer wp = new WalkingPlayer(game, 2, 2f, 3f, true, FOLLOW_CAM); // todo
		game.setAbility(1, new LaserRifle(game));
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
