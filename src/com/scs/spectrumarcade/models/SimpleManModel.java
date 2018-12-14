package com.scs.spectrumarcade.models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.components.IAvatarModel;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class SimpleManModel extends Node implements IAvatarModel {
	
	private boolean isJumping = false;
	private long jumpEndTime;
	private AnimChannel channel;

	public SimpleManModel(AssetManager assetManager) {
		super("SimpleMan");
		
		Spatial model = assetManager.loadModel("Models/simpleMan2.6.blend");
		JMEModelFunctions.scaleModelToHeight(model, Settings.PLAYER_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0);
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
	
		AnimControl control = JMEModelFunctions.getNodeWithControls(null, (Node)model);
		channel = control.createChannel();

		model.setShadowMode(ShadowMode.CastAndReceive);

		this.attachChild(model);
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
		// Cannot jump
	}
}
