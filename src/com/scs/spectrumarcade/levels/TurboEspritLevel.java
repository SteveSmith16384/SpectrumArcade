package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.turboesprit.TurboEspritAvatar;
import com.scs.spectrumarcade.entities.turboesprit.SimpleCity;

public class TurboEspritLevel extends AbstractLevel implements ILevelGenerator {

	//private static final int MAP_SIZE = 120;

	public TurboEspritLevel(SpectrumArcade _game) {
		super(_game);
	}


	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		//FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/road2.png");
		//game.addEntity(floor);
		
		SimpleCity city = new SimpleCity(game);
		city.setup();

	}


	@Override
	public Avatar createAndPositionAvatar() {
		return new TurboEspritAvatar(game, 10f, 30f, 10f);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}


	@Override
	public void process(float tpfSecs) {
	}



	@Override
	public String getHUDText() {
		return "";
	}

}
