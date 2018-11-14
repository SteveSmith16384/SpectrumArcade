package com.scs.spectrumarcade.components;

import com.scs.spectrumarcade.entities.Player;

public interface IPlayerCollectable {

	void collected(Player avatar);
	
	void remove();
}
