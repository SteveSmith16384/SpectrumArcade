package com.scs.spectrumarcade.levels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

		InputStream inputStream = ClassLoader.getSystemClassLoader().getSystemResourceAsStream("maps/androids_map.png");
		BufferedImage image = ImageIO.read(inputStream);

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, image.getWidth()/16, 1, image.getHeight()/16, "Textures/mud.png"); // todo - tex and size
		game.addEntity(floor);

		FloorOrCeiling ceiling = new FloorOrCeiling(game, 0, WALL_HEIGHT+1, 0, image.getWidth()/16, 1, image.getHeight()/16, "Textures/mud.png"); // todo
		game.addEntity(ceiling);

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, image.getWidth(), 1f);
		game.addEntity(terrainUDG);

		for (int z=8 ; z<image.getHeight() ; z+=16) {
			for (int x=8 ; x<image.getWidth() ; x+=16) {
				int col = image.getRGB(x, z);//(x*16)+8, (z*16)+8);
				switch (col) {
				case -16777216: // Wall
				case -16711680: 
				case -16776960:
				case -16777214:
				case -16659759:
					//terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.EATF_SOLID);
					terrainUDG.addRectRange_Blocks(BlockCodes.GAUNTLET_WALL, new Vector3Int(x, 0, z), new Vector3Int(1, WALL_HEIGHT, 1));
					break;
					
				case -3290162: // floor
					break;
					
				case -3159092: // door?
					terrainUDG.addRectRange_Blocks(BlockCodes.GAUNTLET_DOOR, new Vector3Int(x, 0, z), new Vector3Int(1, WALL_HEIGHT, 1));
					break;
					
				case -3355442: // baddy?
					terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(x, 0, z), new Vector3Int(1, WALL_HEIGHT, 1));
					break;
					
				case -12345791:
					terrainUDG.addRectRange_Blocks(BlockCodes.EXIT, new Vector3Int(x, 0, z), new Vector3Int(1, WALL_HEIGHT, 1));
					break;

				default:
					Globals.p("Unknown colour at " + x/16 + "," + z/16 + ":" + col);
					Globals.p("Unknown colour at " + x/16 + "," + z/16 + ":" + col);
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
		WalkingPlayer wp = new WalkingPlayer(game, 2, 2f, 3f, 0f, FOLLOW_CAM, "Textures/androids/todo.png");
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