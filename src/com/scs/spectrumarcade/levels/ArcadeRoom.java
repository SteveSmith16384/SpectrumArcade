package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.ArcadeMachine;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.Player;

public class ArcadeRoom implements ILevelGenerator {

	private static final int MAP_SIZE = 30;

	public ArcadeRoom() {
	}


	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/white.png");
		game.addEntity(floor);
		
		ArcadeMachine machine = new ArcadeMachine(game, 2, 0, 2);
		game.addEntity(machine);
	}


	@Override
	public void moveAvatarToStartPosition(Player avatar) {
		avatar.playerControl.warp(new Vector3f(10, 20f, 10f));
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}

}