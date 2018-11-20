package com.scs.spectrumarcade;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;

public abstract class Avatar extends AbstractPhysicalEntity {

	public Avatar(SpectrumArcade _game, String _name) {
		super(_game, _name);
	}

	public abstract void onAction(String binding, boolean isPressed, float tpf);
	
	public abstract void warp(Vector3f vec);
	
	public abstract void setCameraLocation(Camera cam);
}
