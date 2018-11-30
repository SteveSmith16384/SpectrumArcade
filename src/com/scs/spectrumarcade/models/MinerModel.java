package com.scs.spectrumarcade.models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class MinerModel extends Node {

	public static final float MODEL_HEIGHT = 1.7f;
	private static final float MODEL_WIDTH = 0.6f;
	private static final float MODEL_DEPTH = 0.6f;

	//private AssetManager assetManager;
	//private Spatial model;
	private AnimChannel channel;
	//public boolean isJumping = false;
	//private int levelCode;

	public MinerModel(AssetManager assetManager) {
		super("MinerModel");
		//assetManager = _assetManager;

		Spatial model = assetManager.loadModel("Models/AnimatedHuman/Animated Human.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/AnimatedHuman/Textures/ClothedLightSkin.png");
		this.attachChild(model);

		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);

		AnimControl control = JMEModelFunctions.getNodeWithControls(null, (Node)model);
		channel = control.createChannel();

		model.setShadowMode(ShadowMode.Cast);
		
	}
	
	
	public void walkAnim() {
		channel.setLoopMode(LoopMode.Loop);
		channel.setAnim("Walk");
	}

	public void idleAnim() {
		channel.setLoopMode(LoopMode.Loop);
		//todo - re-add channel.setAnim("Idle");
	}

	/*
	public void setAnim(int animCode) {
		if (currAnimCode == animCode) {
			return;			
		}

		if (Globals.DEBUG_JUMP_ANIM) {
			Globals.p("Jump will end in " + (jumpEndTime - System.currentTimeMillis()) + "ms");
		}

		boolean jumpEnded = this.jumpEndTime < System.currentTimeMillis();
		if (this.isJumping && !jumpEnded && animCode != AbstractAvatar.ANIM_DIED) {
			// Do nothing; only dying can stop a jumping anim
			return;
		}

		switch (animCode) {
		case AbstractAvatar.ANIM_DIED:
			channel.setAnim("Death");
			channel.setLoopMode(LoopMode.DontLoop);
			break;

		case AbstractAvatar.ANIM_IDLE:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("Idle");
			break;

		case AbstractAvatar.ANIM_WALKING:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("Walk");
			break;

		case AbstractAvatar.ANIM_RUNNING:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("Run");
			break;

		case AbstractAvatar.ANIM_SHOOTING:
			channel.setLoopMode(LoopMode.DontLoop);
			channel.setAnim("Punch");
			break;

		case AbstractAvatar.ANIM_JUMP:
			channel.setLoopMode(LoopMode.DontLoop);
			channel.setAnim("Jump");
			isJumping = true;
			jumpEndTime = System.currentTimeMillis() + (long)(channel.getAnimMaxTime() * 1000); // System.currentTimeMillis() - jumpEndTime
			jumpEndTime = jumpEndTime - 200; // Time is too long otherwise
			break;

		default:
			Globals.pe(this.getClass().getSimpleName() + ": Unable to show anim " + animCode);
		}

		currAnimCode = animCode;
	}
	 */
}
