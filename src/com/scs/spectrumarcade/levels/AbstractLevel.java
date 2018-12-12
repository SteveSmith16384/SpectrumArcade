package com.scs.spectrumarcade.levels;

import com.scs.spectrumarcade.SpectrumArcade;

public abstract class AbstractLevel implements ILevelGenerator {
	
	protected SpectrumArcade game;

	public AbstractLevel() {
		// Need this for reflection!
	}

	
	public void setGame(SpectrumArcade _game) {
		game = _game;
	}
	
	
	@Override
	public boolean isFollowCam() {
		return true;
	}


	@Override
	public boolean isCamInCharge() {
		return true;
	}



}
