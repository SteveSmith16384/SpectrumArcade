package com.scs.spectrumarcade.entities;

import java.util.ArrayList;
import java.util.List;

import com.jme3.audio.AudioNode;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.models.GenericWalkingAvatar;

public class HeliAvatar extends AbstractPhysicalEntity implements IAvatar {

	// Our movement speed
	public static final float speed = 4;
	private static final float strafeSpeed = 4f;

	private Vector3f walkDirection = new Vector3f();
	private boolean left = false, right = false, up = false, down = false;

	public HeliAvatar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Player");

		/** Create a box to use as our player model */
		Box box1 = new Box(1, 2, 1);
		Geometry playerGeometry = new Geometry("Player", box1);
		//playerGeometry.setCullHint(CullHint.Always);
		this.getMainNode().attachChild(playerGeometry);

		this.getMainNode().setLocalTranslation(x, y, z);

	}


	@Override
	public void process(float tpf) {
		Camera cam = game.getCamera();

		//camDir.set(cam.getDirection()).multLocal(speed, 0.0f, speed);
		//camLeft.set(cam.getLeft()).multLocal(strafeSpeed);
		walkDirection.set(0, 0, 0);
		//walking = up || down || left || right;
		if (left) {
			//walkDirection.addLocal(camLeft);
		}
		if (right) {
			//walkDirection.addLocal(camLeft.negate());
		}
		if (up) {
			//walkDirection.addLocal(camDir);
		}
		if (down) {
			//walkDirection.addLocal(camDir.negate());
		}
		//playerControl.setWalkDirection(walkDirection);

	}


	@Override
	public void onAction(String binding, boolean isPressed, float tpf) {
		if (binding.equals("Left")) {
			left = isPressed;
		} else if (binding.equals("Right")) {
			right = isPressed;
		} else if (binding.equals("Up")) {
			up = isPressed;
		} else if (binding.equals("Down")) {
			down = isPressed;
		}

	}


	@Override
	public void warp(Vector3f v) {
		//playerControl.warp(v);
	}


	@Override
	public void setCameraLocation(Camera cam) {
/*		Vector3f vec = getMainNode().getWorldTranslation();
		if (!Settings.FREE_CAM) {
			if (!followCam) {
				cam.setLocation(new Vector3f(vec.x, vec.y + Settings.PLAYER_HEIGHT * .8f, vec.z)); // Drop cam slightly so we're looking out of our eye level - todo - don't create each time
			} else {
				// Camera system in level handles it
			}
		} else {
			cam.setLocation(new Vector3f(vec.x, vec.y + 15f, vec.z));
		}
*/
	}


	@Override
	public void clearForces() {
	}


}
