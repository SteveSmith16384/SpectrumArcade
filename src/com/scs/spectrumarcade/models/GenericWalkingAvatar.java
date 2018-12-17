package com.scs.spectrumarcade.models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.components.IAvatarModel;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class GenericWalkingAvatar extends Node implements IAvatarModel {

	public static final float MODEL_HEIGHT = 1.7f;

	private boolean isJumping = false;
	private long jumpEndTime;
	private AnimChannel channel;

	public GenericWalkingAvatar(AssetManager assetManager, String tex) {
		super("MinerModel");

		Spatial model = assetManager.loadModel("Models/AnimatedHuman/Animated Human.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, tex);//"Models/AnimatedHuman/Textures/ClothedLightSkin.png");
		this.attachChild(model);

		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);

		AnimControl control = JMEModelFunctions.getNodeWithControls(null, (Node)model);
		channel = control.createChannel();

		model.setShadowMode(ShadowMode.CastAndReceive);

	}


	public void walkAnim() {
		if (!this.isJumping || this.jumpEndTime < System.currentTimeMillis()) {
			if (channel.getAnimationName() == null || !channel.getAnimationName().equals("Walk")) {
				channel.setLoopMode(LoopMode.Loop);
				channel.setAnim("Walk");
			}
		}
	}


	public void idleAnim() {
		if (!this.isJumping || this.jumpEndTime < System.currentTimeMillis()) {
			if (channel.getAnimationName() == null || !channel.getAnimationName().equals("Idle")) {
				channel.setLoopMode(LoopMode.Loop);
				channel.setAnim("Idle");
			}
		}
	}


	public void jumpAnim() {
		if (channel.getAnimationName() == null || !channel.getAnimationName().equals("Jump")) {
			channel.setLoopMode(LoopMode.DontLoop);
			channel.setAnim("Jump");
			isJumping = true;
			jumpEndTime = System.currentTimeMillis() + (long)(channel.getAnimMaxTime() * 1000) - 300;
		}
	}


	public void diedAnim() {
		//if (channel.getAnimationName() == null || !channel.getAnimationName().equals("Jump")) {
		channel.setLoopMode(LoopMode.DontLoop);
		channel.setAnim("Died");
		//isJumping = true;
		//jumpEndTime = System.currentTimeMillis() + (long)(channel.getAnimMaxTime() * 1000) - 300;
	}
}

