package com.scs.spectrumarcade.components;

public interface IPlayerCollectable {

	void collected(IAvatar avatar);
	
	void markForRemoval();
}
