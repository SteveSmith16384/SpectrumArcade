package com.scs.spectrumarcade.abilities;

import com.scs.spectrumarcade.SpectrumArcade;

public abstract class AbstractAbility {

	protected SpectrumArcade game;
	
	public AbstractAbility(SpectrumArcade _game) {
		game =_game;
	}

}
