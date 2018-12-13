package com.scs.spectrumarcade.levels;

import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.SpectrumArcade;

public abstract class AbstractLevel implements ILevelGenerator {
	
	protected SpectrumArcade game;

	public AbstractLevel() {
		// Need this for reflection!
	}

	
	public void setGame(SpectrumArcade _game) {
		game = _game;
	}


	public void setupCameraSystem(CameraSystem sys) {
		sys.setupCam(true, 3f, .1f, true, 1f);
	}
	


}
