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
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.abilities.GauntletAxeThrower;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.gauntlet.Ghost;

import mygame.util.Vector3Int;

public class GauntletLevel extends AbstractLevel implements ILevelGenerator {

	public GauntletLevel() {
		super();
	}


	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		BufferedImage image = ImageIO.read(new File("gauntlet_level1.png"));

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, image.getWidth(), 1, image.getHeight(), "Textures/black.png");
		game.addEntity(floor);

		VoxelTerrainEntity terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, image.getWidth(), 1f);
		game.addEntity(terrainUDG);

		for (int z=0 ; z<image.getHeight() ; z++) {
			for (int x=0 ; x<image.getHeight() ; x++) {
				int col = image.getRGB(x, z);
				switch (col) {
				case -12566528:
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.EATF_SOLID);
					break;
				case -6258592: // floor					
					break;
				case -4145152: // door?
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.EATF_WEAK);
					break;
				case -16744448: // Monster gen?
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.ANT_ATTACK);
					break;
				case -16715776: //treasure?
					break;
				case -16744336: // food?
					break;
				case -1048576: // dunno
					break;
				case -16744352: // dunno
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
				Ghost g = new Ghost(game, x, z);
				game.addEntity(g);
			}
		}
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(2f, 2f, 3f);
	}


	@Override
	public Avatar createAndPositionAvatar() {
		WalkingPlayer wp = new WalkingPlayer(game, 2, 2f, 3f, true);
		wp.setAbility(1, new GauntletAxeThrower(game));
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
