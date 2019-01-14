package com.scs.spectrumarcade.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class SimpleWomanModel extends Node {
	
	public SimpleWomanModel(AssetManager assetManager) {
		super("Woman1");
		
		Spatial s = assetManager.loadModel("Models/simple_girl2.6.blend");
		JMEModelFunctions.scaleModelToHeight(s, Settings.PLAYER_HEIGHT);
		JMEModelFunctions.moveYOriginTo(s, 0);
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
	
		this.attachChild(s);
	}

}
