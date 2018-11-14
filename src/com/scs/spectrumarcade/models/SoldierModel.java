package com.scs.spectrumarcade.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class SoldierModel extends Node {
	
	public SoldierModel(AssetManager assetManager) {
		super("SoldierModel");
		
		Spatial s = assetManager.loadModel("Models/simpleMan2.6.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, s, "/Models/lik_glavni_color_mapa.png");
		JMEModelFunctions.scaleModelToHeight(s, Settings.PLAYER_HEIGHT + 0.1f);
		JMEModelFunctions.moveYOriginTo(s, 0);
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
	
		this.attachChild(s);
	}

}
