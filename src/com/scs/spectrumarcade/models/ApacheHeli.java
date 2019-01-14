package com.scs.spectrumarcade.models;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class ApacheHeli extends Node {

	public ApacheHeli(AssetManager assetManager, String tex) {
		Node model = (Node)assetManager.loadModel("Models/Apache/helic.obj");
		//((Node)model.getChild(0)).getChild(1).removeFromParent(); // Remove the cube
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, tex);
		model.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.scaleModelToHeight(model, 2f);
		JMEModelFunctions.moveYOriginTo(model, 0f);
		
		//JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(1, 0, 0)); // Point model fwds

		this.attachChild(model); // Need to have an extra node to keep model's relative position
		
	}
	
}
