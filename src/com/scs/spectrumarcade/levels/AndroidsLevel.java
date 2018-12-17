package com.scs.spectrumarcade.levels;

import java.awt.image.BufferedImage;
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
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.androids.GSquare;
import com.scs.spectrumarcade.entities.androids.SSquare;
import com.scs.spectrumarcade.models.GenericWalkingAvatar;

import mygame.util.Vector3Int;

public class AndroidsLevel extends AbstractLevel implements ILevelGenerator {

	//public static final boolean FOLLOW_CAM = true;
	private static final int WALL_HEIGHT = 2;
	private static final int BLOCK_SIZE = 2;

	private Vector3f startpos;
	private int levelNum;

	public AndroidsLevel() {
		super();
	}


	@Override
	public void generateLevel(SpectrumArcade game, int _levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		levelNum = _levelNum;

		InputStream inputStream = ClassLoader.getSystemClassLoader().getSystemResourceAsStream("maps/androids_map.png");
		BufferedImage image = ImageIO.read(inputStream);

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, BLOCK_SIZE*image.getWidth()/16, 1, BLOCK_SIZE*image.getHeight()/16, "Textures/blocks/white.png");
		game.addEntity(floor);

		FloorOrCeiling ceiling = new FloorOrCeiling(game, 0, WALL_HEIGHT+1, 0, BLOCK_SIZE*image.getWidth()/16, 1, BLOCK_SIZE*image.getHeight()/16, "Textures/blocks/white.png");
		//game.addEntity(ceiling);

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, image.getWidth(), 16, BLOCK_SIZE, 1f);
		game.addEntity(terrainUDG);

		int mapZ = 0;
		for (int z=8 ; z<image.getHeight() ; z+=16) {
			int mapX = 0;
			for (int x=8 ; x<image.getWidth() ; x+=16) {
				int col = image.getRGB(x, z);
				switch (col) {
				case -16777216: // Wall
				case -16711680: 
				case -16776960:
				case -16777214:
				case -16659759:
					//terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.EATF_SOLID);
					terrainUDG.addRectRange_Blocks(BlockCodes.ANDROIDS_WALL, new Vector3Int(mapX, 0, mapZ), new Vector3Int(1, WALL_HEIGHT, 1));
					break;

				case -3224368: // floor
					// Do nothing
					break;

				case -15808822: // door?
					//terrainUDG.addRectRange_Blocks(BlockCodes.GAUNTLET_DOOR, new Vector3Int(mapX, 0, mapZ), new Vector3Int(1, WALL_HEIGHT, 1));
					break;

				case -11550134: // baddy?
				case -7024943:
					//terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(mapX, 0, mapZ), new Vector3Int(1, WALL_HEIGHT, 1));
					break;

				case -15067309: // G square
				case -6666598:
					GSquare gs = new GSquare(game, mapX*BLOCK_SIZE, mapZ*BLOCK_SIZE);
					game.addEntity(gs);
					break;

				case -15462508: // start
					startpos = new Vector3f(mapX*BLOCK_SIZE+.5f, .2f, mapZ*BLOCK_SIZE+.5f);
					break;

				case -606219: // S square
					SSquare ss = new SSquare(game, mapX*BLOCK_SIZE, mapZ*BLOCK_SIZE);
					game.addEntity(ss);
					break;

				default:
					Globals.p("Unknown colour at " + mapX + "," + mapZ + ":" + col);
					Globals.p("Unknown colour at " + mapX + "," + mapZ + ":" + col);
				}
				mapX++;
			}
			mapZ++;
		}
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return startpos;//new Vector3f(.5f*BLOCK_SIZE, 1.2f, 2.5f*BLOCK_SIZE);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		WalkingPlayer wp = new WalkingPlayer(game, startpos.x, startpos.y, startpos.z, 0f, new GenericWalkingAvatar(game.getAssetManager(), "Textures/androids/avatar_blue.png"));
		//game.setAbility(1, new LaserRifle(game));
		return wp;
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Green; // todo - black
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
