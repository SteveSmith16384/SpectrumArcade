package com.scs.spectrumarcade.models;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class FordFocusModel extends Node {

	public static final float MODEL_WIDTH = 2.2f;
	public static final float MODEL_HEIGHT = 0.7f;

	public FordFocusModel(AssetManager assetManager, int texNum) {
		super("FordFocusModel");
		
		Spatial model = assetManager.loadModel("Models/jkm_fordfocus/fordfocus128.obj");
		model.scale(.01f);
		model.setCullHint(CullHint.Never); // otherwise, the car is invisible when upside down
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/jkm_fordfocus/fordfocusnew128.bmp");
		model.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);
		
		JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(1, 0, 0)); // Point model fwds

		this.attachChild(model); // Need to have an extra node to keep model's relative position

		JMEModelFunctions.centreXZ(model);
		
		//Globals.p("VWCorradon size=" + model.getWorldBound());
	}
	
}
