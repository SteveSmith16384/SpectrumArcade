package com.scs.spectrumarcade.models;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class ArcadeMachineModel extends Node {

	public ArcadeMachineModel(AssetManager assetManager) {
		Spatial model = assetManager.loadModel("Models/ArcadeMachine_AntAttack/Arcade.obj");
		JMEModelFunctions.scaleModelToHeight(model, 2f);
		model.setShadowMode(ShadowMode.CastAndReceive);
		this.attachChild(model);
	}

}
