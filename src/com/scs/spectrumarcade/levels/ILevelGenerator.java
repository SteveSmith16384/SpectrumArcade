package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.SpectrumArcade;

public interface ILevelGenerator {

	void setGame(SpectrumArcade game);
	
	void generateLevel(SpectrumArcade game) throws FileNotFoundException, IOException, URISyntaxException;
	
	Vector3f getAvatarStartPos();
	
	Avatar createAndPositionAvatar();
	
	ColorRGBA getBackgroundColour();
	
	void process(float tpfSecs);
	
	String getHUDText();
	
	void setInitialCameraDir(Camera cam);
	
}
