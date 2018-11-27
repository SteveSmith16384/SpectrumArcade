package com.scs.spectrumarcade;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.abilities.IAbility;

public interface IAvatar extends IProcessable {//extends AbstractPhysicalEntity implements IProcessable  { 
/*
	private HashMap<Integer, IAbility> abilities = new HashMap<>();

	public Avatar(SpectrumArcade _game, String _name) {
		super(_game, _name);
	}
*/
	void onAction(String binding, boolean isPressed, float tpf);

	void warp(Vector3f vec);
	
	void clearForces();

	void setCameraLocation(Camera cam);
	
	//void setAbility(int num, IAbility a);
	
	//void activateAbility(int num);
	
/*
	public void setAbility(int num, IAbility a) {
		this.abilities.put(num, a);
	}


	public void activateAbility(int num) {
		IAbility a = abilities.get(1);
		if (a != null) {
			a.activate();
		}
	}*/
}
