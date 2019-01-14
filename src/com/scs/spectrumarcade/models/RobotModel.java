package com.scs.spectrumarcade.models;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class RobotModel extends Node {

	public static final float MODEL_WIDTH = 0.8f;
	public static final float MODEL_HEIGHT = 2f;

	private AssetManager assetManager;
	private Spatial node;

	public RobotModel(AssetManager _assetManager) {
		assetManager = _assetManager;

		node = new Node("RobotNode");
		Spatial model = assetManager.loadModel("Models/Prototipo1RoboPrincipalBranco.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Textures/yellowsun.jpg");
		model.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);
		((Node)node).attachChild(model); // Need to have an extra node to keep model's relative position
		//JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(0, 0, 0)); // Point model fwds
		//return node; //node.getLocalTranslation()
	}


}

