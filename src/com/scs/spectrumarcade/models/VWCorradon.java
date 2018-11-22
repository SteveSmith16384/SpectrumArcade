package com.scs.spectrumarcade.models;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class VWCorradon extends Node {

	public static final float MODEL_WIDTH = 2.2f;
	public static final float MODEL_HEIGHT = 0.7f;

	public VWCorradon(AssetManager assetManager) {
		Spatial model = assetManager.loadModel("Models/car/car.obj");
		model.scale(.01f);
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/car/corradon.png");
		model.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);
		
		JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(1, 0, 0)); // Point model fwds

		this.attachChild(model); // Need to have an extra node to keep model's relative position

		JMEModelFunctions.centreXZ(model);
		
		//Globals.p("VWCorradon size=" + model.getWorldBound());
	}
	
}
