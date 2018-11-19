package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.PlayerCar;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;

import mygame.util.Vector3Int;

public class TurboEspritLevel extends AbstractLevel implements ILevelGenerator {

	private static final int MAP_SIZE = 60;
	
	private VoxelTerrainEntity terrainUDG;

	public TurboEspritLevel(SpectrumArcade _game) {
		super(_game);
	}

	
	@Override
	public void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException {
		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE, 1f);
		game.addEntity(terrainUDG);
		
		//Floor
		terrainUDG.addRectRange_Blocks(BlockCodes.ANT_ATTACK, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, MAP_SIZE));

		//  outer walls
		terrainUDG.addRectRange_Blocks(BlockCodes.EATF_OUTER_WALL, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.EATF_OUTER_WALL, new Vector3Int(0, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
		terrainUDG.addRectRange_Blocks(BlockCodes.EATF_OUTER_WALL, new Vector3Int(0, 0, MAP_SIZE), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.EATF_OUTER_WALL, new Vector3Int(MAP_SIZE, 0, 0), new Vector3Int(1, 1, MAP_SIZE));


		
	}
/*
	@Override
	public void moveAvatarToStartPosition(Avatar avatar) {
		avatar.warp(new Vector3f(MAP_SIZE/2, 3f, MAP_SIZE/2));
		
	}
*/

	@Override
	public Avatar createAndPositionAvatar() {
		return new PlayerCar(game, MAP_SIZE/2, 3f, MAP_SIZE/2);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Black;
	}


	@Override
	public void process(float tpfSecs) {
		//game.getCamera().lookAt(game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
	}

}
