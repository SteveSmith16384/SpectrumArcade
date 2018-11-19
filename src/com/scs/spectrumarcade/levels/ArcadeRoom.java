package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.ArcadeMachine;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.Player;
import com.scs.spectrumarcade.entities.manicminer.Key;

public class ArcadeRoom extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;

	public ArcadeRoom(SpectrumArcade _game) {
		super(_game);
	}


	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/carpet1.jpg");
		game.addEntity(floor);

		ArcadeMachine machine = new ArcadeMachine(game, 2, 0, 2, AntAttackLevel.class);
		game.addEntity(machine);
/*
		ArcadeMachine machine2 = new ArcadeMachine(game, 4, 0, 2, SplatLevel.class);
		game.addEntity(machine2);

		ArcadeMachine machine3 = new ArcadeMachine(game, 6, 0, 2, EricAndTheFloatersLevel.class);
		game.addEntity(machine3);

		ArcadeMachine machine4 = new ArcadeMachine(game, 8, 0, 2, MinedOutLevel.class);
		game.addEntity(machine4);
		*/
		
		Key key = new Key(game, 5, 1, 5);
		game.addEntity(key);
	}

/*
	@Override
	public void moveAvatarToStartPosition(Avatar avatar) {
		avatar.warp(new Vector3f(10f, 20f, 10f));
	}
*/

	@Override
	public Avatar createAndPositionAvatar() {
		return new Player(game, 10, 3, 10f);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}


	@Override
	public void process(float tpfSecs) {
		// Do nothing
	}


}