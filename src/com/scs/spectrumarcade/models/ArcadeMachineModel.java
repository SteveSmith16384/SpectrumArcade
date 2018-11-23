package com.scs.spectrumarcade.models;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class ArcadeMachineModel extends Node {

	public ArcadeMachineModel(AssetManager assetManager, String folder) {
		Spatial model = assetManager.loadModel("Models/" + folder + "/Arcade.obj");
		JMEModelFunctions.scaleModelToHeight(model, Settings.PLAYER_HEIGHT);
		model.setShadowMode(ShadowMode.CastAndReceive);
		this.attachChild(model);
	}

}
