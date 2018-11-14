package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.Player;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;

public interface ILevelGenerator {

	void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException;
	
	void moveAvatarToStartPosition(Player avatar);
	
	ColorRGBA getBackgroundColour();
	
	int getLevelCode();
}
