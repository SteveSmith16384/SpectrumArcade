package com.scs.spectrumarcade.levels;

import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.SpectrumArcade;

public abstract class AbstractLevel implements ILevelGenerator {
	
	protected SpectrumArcade game;

	public AbstractLevel() {
		// Need this for reflection!
	}

	
	@Override
	public void setGame(SpectrumArcade _game) {
		game = _game;
	}


	@Override
	public void setupCameraSystem(CameraSystem sys) {
		sys.setupCam(3f, .1f, true, 1f);
	}
	


}
