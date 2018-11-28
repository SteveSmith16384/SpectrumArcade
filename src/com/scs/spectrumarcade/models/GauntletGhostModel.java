package com.scs.spectrumarcade.models;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class GauntletGhostModel extends Node {

	private static final float MODEL_HEIGHT = 1f;

	public GauntletGhostModel(AssetManager assetManager) {
		//assetManager = _assetManager;

		Spatial model = assetManager.loadModel("Models/ManCover/ManCover.obj");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/ManCover/MB_ManCoverTexturb.png");
		model.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);
		
		JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(-1, 0, 0)); // Point model fwds

		this.attachChild(model); // Need to have an extra node to keep model's relative position
		
	}
	
	
	
}

