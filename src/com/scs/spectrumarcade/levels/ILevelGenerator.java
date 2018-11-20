package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.SpectrumArcade;

public interface ILevelGenerator {

	void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException;
	
	//void moveAvatarToStartPosition(Avatar avatar);
	Avatar createAndPositionAvatar();
	
	ColorRGBA getBackgroundColour();
	
	void process(float tpfSecs);
	
	String getHUDText();
	
}
