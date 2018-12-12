package com.scs.spectrumarcade;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.abilities.IAbility;

public interface IAvatar extends IProcessable { 

	void onAction(String binding, boolean isPressed, float tpf);

	void warp(Vector3f vec);
	
	void clearForces();

	void setCameraLocation(Camera cam); // todo - remove?
	
}
