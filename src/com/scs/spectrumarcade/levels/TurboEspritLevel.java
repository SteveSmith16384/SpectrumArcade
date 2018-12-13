package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.turboesprit.ParkedCar;
import com.scs.spectrumarcade.entities.turboesprit.Pedestrian;
import com.scs.spectrumarcade.entities.turboesprit.SimpleCity;
import com.scs.spectrumarcade.entities.turboesprit.TurboEspritAvatar;

public class TurboEspritLevel extends AbstractLevel implements ILevelGenerator {

	private TurboEspritAvatar car;


	@Override
	public void generateLevel(SpectrumArcade game, int levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		//FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/road2.png");
		//game.addEntity(floor);

		SimpleCity city = new SimpleCity(game);
		city.setup();

		Pedestrian pedestrian = new Pedestrian(game, 25, 25);
		game.addEntity(pedestrian);

		ParkedCar p = new ParkedCar(game, 17, 1, 17);
		game.addEntity(p);

	}


	@Override
	public IAvatar createAndPositionAvatar() {
		car = new TurboEspritAvatar(game, 12f, 1f, 12f);
		return car;
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(12f, 1f, 12f);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Cyan;
	}


	@Override
	public void process(float tpfSecs) {
	}



	@Override
	public String getHUDText() {
		return "Speed: " + this.car.vehicle.getCurrentVehicleSpeedKmHour();
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		// No neeed
	}


	public void setupCameraSystem(CameraSystem sys) {
		sys.setupCam(true, 3f, 0, false, 2f);
	}


}
