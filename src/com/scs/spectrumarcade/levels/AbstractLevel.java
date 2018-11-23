package com.scs.spectrumarcade.levels;

import com.scs.spectrumarcade.SpectrumArcade;

public abstract class AbstractLevel {
	
	protected SpectrumArcade game;

	public AbstractLevel() {
		// Need this for reflection!
	}

	
	public void setGame(SpectrumArcade _game) {
		game = _game;
	}
	
	

}
