package com.scs.spectrumarcade;

import java.util.HashMap;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.abilities.IAbility;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;

public abstract class Avatar extends AbstractPhysicalEntity {
	
	private HashMap<Integer, IAbility> abilities = new HashMap<>();

	public Avatar(SpectrumArcade _game, String _name) {
		super(_game, _name);
	}

	public abstract void onAction(String binding, boolean isPressed, float tpf);
	
	public abstract void warp(Vector3f vec);
	
	public abstract void setCameraLocation(Camera cam);
	
	public void setAbility(int num, IAbility a) {
		this.abilities.put(num, a);
	}
	
	
	public void activateAbility(int num) {
		abilities.get(1).acivate();
	}
}
