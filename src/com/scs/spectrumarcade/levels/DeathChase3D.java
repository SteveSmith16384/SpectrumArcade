package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.turboesprit.TurboEspritAvatar;

public class DeathChase3D extends AbstractLevel implements ILevelGenerator {

	private TurboEspritAvatar car;

	private static final int MAP_SIZE = 128;

	public DeathChase3D() {
		super();
	}


	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/white.png");
		game.addEntity(floor);
		
		

	}


	@Override
	public Avatar createAndPositionAvatar() {
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
	}}
