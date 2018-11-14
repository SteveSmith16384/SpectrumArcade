package com.scs.spectrumarcade.models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class AntModel extends Node {

	public static final float MODEL_WIDTH = 2.2f;
	public static final float MODEL_HEIGHT = 0.7f;

	private AssetManager assetManager;
	private Spatial node;

	// Anim
	private AnimChannel channel;
	private int currAnimCode = -1;

	public AntModel(AssetManager _assetManager) {
		assetManager = _assetManager;


		node = new Node("RobotNode");
		Spatial model = assetManager.loadModel("Models/spider/Spider.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/spider/Spider.png");
		model.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);
		
		JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(1, 0, 0)); // Point model fwds

		AnimControl control = JMEModelFunctions.getNodeWithControls("Sphere (Node)", (Node)model);
		channel = control.createChannel();

		((Node)node).attachChild(model); // Need to have an extra node to keep model's relative position
	}
	
	/*
	@Override
	public void setAnim(int animCode) {
		if (currAnimCode == animCode) {
			return;			
		}

		switch (animCode) {
		case AbstractAvatar.ANIM_IDLE:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("SpiderStand");
			break;

		case AbstractAvatar.ANIM_WALKING:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("SpiderWalk");
			break;

		case AbstractAvatar.ANIM_ATTACK:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("SpiderWalk");
			break;

		default:
			Globals.pe(this.getClass().getSimpleName() + ": Unable to show anim " + animCode);
		}

		currAnimCode = animCode;

	}
*/
}

