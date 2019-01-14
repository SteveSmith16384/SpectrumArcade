package com.scs.spectrumarcade.components;

import com.jme3.math.Vector3f;

public interface IAvatar extends IProcessable { 

	float getCameraHeight();
	
	void onAction(String binding, boolean isPressed, float tpf);
	
	void setAvatarVisible(boolean b);

	void warp(Vector3f vec);
	
	void clearForces();
	
	void showKilledAnim();

}
