package com.scs.spectrumarcade;

public interface IEntity {

	void markForRemoval();
	
	void actuallyRemove();

	void process(float tpfSecs);
}
