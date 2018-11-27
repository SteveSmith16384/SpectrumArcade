package com.scs.spectrumarcade.components;

import com.scs.spectrumarcade.IAvatar;

public interface IPlayerCollectable {

	void collected(IAvatar avatar);
	
	void markForRemoval();
}
