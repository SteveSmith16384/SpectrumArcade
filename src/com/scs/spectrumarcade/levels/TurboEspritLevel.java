package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.turboesprit.EspritAvatar;

import mygame.util.Vector3Int;

public class TurboEspritLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 60;
	
	public TurboEspritLevel(SpectrumArcade _game) {
		super(_game);
	}

	
	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/moonbase_ceiling.png");
		game.addEntity(floor);

		
	}

	
	
	private void addBuilding() {
		
	}
	
	
	@Override
	public Avatar createAndPositionAvatar() {
		return new EspritAvatar(game, MAP_SIZE/2, 3f, MAP_SIZE/2);
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
