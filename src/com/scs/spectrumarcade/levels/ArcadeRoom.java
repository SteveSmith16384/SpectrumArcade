package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.ArcadeMachine;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.Magazine;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.Wall;
import com.scs.spectrumarcade.entities.manicminer.Key;

public class ArcadeRoom extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;

	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/carpet1.jpg");
		game.addEntity(floor);
		
		// Back wall
		Wall wall1 = new Wall(game, 0, 0, 0, MAP_SIZE, 4, .1f, "Textures/lliella_funinthesun_paper1.jpg", true);
		game.addEntity(wall1);

		// Side wall
		Wall wall2 = new Wall(game, 0, 0, 0, .1f, 4, MAP_SIZE, "Textures/lliella_funinthesun_paper1.jpg", true);
		game.addEntity(wall2);

		ArcadeMachine machine = new ArcadeMachine(game, 2, 0, 2, "ArcadeMachine_AntAttack", AntAttackLevel.class);
		game.addEntity(machine);

		ArcadeMachine machine3 = new ArcadeMachine(game, 6, 0, 2, "ArcadeMachine_EricAndTheFloaters", EricAndTheFloatersLevel.class);
		game.addEntity(machine3);
/*
		ArcadeMachine machine2 = new ArcadeMachine(game, 4, 0, 2, SplatLevel.class);
		game.addEntity(machine2);


		ArcadeMachine machine4 = new ArcadeMachine(game, 8, 0, 2, MinedOutLevel.class);
		game.addEntity(machine4);
		*/
		
		Key key = new Key(game, 5, 1, 5);
		game.addEntity(key);
		
		Magazine mag = new Magazine(game, 3, .3f, 4, "Textures/SU/Sinclair_User_cover_24.jpg");
		game.addEntity(mag);
	}

/*
	@Override
	public void moveAvatarToStartPosition(Avatar avatar) {
		avatar.warp(new Vector3f(10f, 20f, 10f));
	}
*/

	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(MAP_SIZE/2, 3f, 2f);
	}

	
	@Override
	public Avatar createAndPositionAvatar() {
		return new WalkingPlayer(game, 10, 3, 10f, true);
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

}