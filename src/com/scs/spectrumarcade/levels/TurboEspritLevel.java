package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.turboesprit.TurboEspritAvatar;
import com.scs.spectrumarcade.entities.turboesprit.SimpleCity;

public class TurboEspritLevel extends AbstractLevel implements ILevelGenerator {

	private TurboEspritAvatar car;
	
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
		car = new TurboEspritAvatar(game, 12f, 3f, 12f);
		return car;
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(12f, 3f, 12f);
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
		return "Speed: " + this.car.vehicle.getCurrentVehicleSpeedKmHour();
	}

}
