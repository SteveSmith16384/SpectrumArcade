package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.entities.Wall;
import com.scs.spectrumarcade.entities.arcaderoom.ArcadeMachine;
import com.scs.spectrumarcade.entities.arcaderoom.Magazine;

public class ArcadeRoom extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 30;

	public static Vector3f lastPos = null;

	@Override
	public void generateLevel(SpectrumArcade game, int levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/arcaderoom/carpet1.jpg");
		game.addEntity(floor);

		// Back wall
		Wall wall1 = new Wall(game, 0, 0, 0, MAP_SIZE, 4, .1f, "Textures/arcaderoom/lliella_funinthesun_paper1.jpg", true);
		game.addEntity(wall1);

		// Side wall
		Wall wall2 = new Wall(game, 0, 0, 0, .1f, 4, MAP_SIZE, "Textures/arcaderoom/lliella_funinthesun_paper1.jpg", true);
		game.addEntity(wall2);

		Magazine mag = new Magazine(game, 3, .3f, 4, "Textures/mags/Sinclair_User_cover_24.jpg");
		game.addEntity(mag);
		mag = new Magazine(game, 5, .3f, 3, "Textures/mags/Crash_Magazine_Cover_Issue_1.jpg");
		game.addEntity(mag);
		mag = new Magazine(game, 7, .3f, 2, "Textures/mags/Yscover1.jpg");
		game.addEntity(mag);

		// Arcade machines
		ArcadeMachine machine = new ArcadeMachine(game, this, 2, 0, 2, "ArcadeMachine_AntAttack", AntAttackLevel.class);
		game.addEntity(machine);

		ArcadeMachine machine3 = new ArcadeMachine(game, this, 3, 0, 2, "ArcadeMachine_EricAndTheFloaters", EricAndTheFloatersLevel.class);
		game.addEntity(machine3);

		ArcadeMachine machine4 = new ArcadeMachine(game, this, 4, 0, 2, "ArcadeMachine_MinedOut", MinedOutLevel.class);
		game.addEntity(machine4);

		ArcadeMachine machine5 = new ArcadeMachine(game, this, 5, 0, 2, "ArcadeMachine_Motos", MotosLevel.class);
		game.addEntity(machine5);

		ArcadeMachine machine6 = new ArcadeMachine(game, this, 6, 0, 2, "ArcadeMachine_StockCar", StockCarChamp3DLevel.class);
		game.addEntity(machine6);

		ArcadeMachine machine7 = new ArcadeMachine(game, this, 7, 0, 2, "ArcadeMachine_Trailblazer", TrailblazerLevel.class);
		game.addEntity(machine7);

		ArcadeMachine machine8 = new ArcadeMachine(game, this, 8, 0, 2, "ArcadeMachine_ManicMiner", ManicMinerCentralCavern.class);
		game.addEntity(machine8);

		ArcadeMachine machine9 = new ArcadeMachine(game, this, 9, 0, 2, "ArcadeMachine_Androids", AndroidsLevel.class);
		game.addEntity(machine9);

		ArcadeMachine machine2 = new ArcadeMachine(game, this, 10, 0, 2, "ArcadeMachine_TurboEsprit", TurboEspritLevel.class);
		game.addEntity(machine2);

		//ArcadeMachine machine7 = new ArcadeMachine(game, this, 9, 0, 2, "ArcadeMachine_Gauntlet", GauntletLevel.class);
		//game.addEntity(machine7);

	}


	@Override
	public Vector3f getAvatarStartPos() {
		if (this.lastPos != null) {
			return lastPos.add(0, 0, 1f);
		}
		return new Vector3f(5, 1f, 5f);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		Vector3f pos = this.getAvatarStartPos();
		return new WalkingPlayer(game, pos.x, pos.y, pos.z, 3f, false, null);
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
		return "LOADING...";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, -1)), Vector3f.UNIT_Y);
	}

}