package com.scs.spectrumarcade.components;

import com.scs.spectrumarcade.entities.WalkingPlayer;

public interface IPlayerCollectable {

	void collected(WalkingPlayer avatar);
	
	void markForRemoval();
}
