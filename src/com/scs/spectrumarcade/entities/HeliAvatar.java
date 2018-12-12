package com.scs.spectrumarcade.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.SpectrumArcade;

public class HeliAvatar extends AbstractPhysicalEntity implements IAvatar {

	// Our movement speed
	//private float upDownSpeed = 0;
	private float turnSpeed = 0;
	private float anglerads = 0;
	private Vector3f fwdSpeed = new Vector3f();

	//private Vector3f walkDirection = new Vector3f();
	private boolean left = false, right = false, fwd = false, backwards = false, up = false, down = false;

	public HeliAvatar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Player");

		/** Create a box to use as our player model */
		Box box1 = new Box(1, 2, 1);
		Geometry playerGeometry = new Geometry("Player", box1);
		playerGeometry.setCullHint(CullHint.Always); // todo
		this.getMainNode().attachChild(playerGeometry);

		this.getMainNode().setLocalTranslation(x, y, z);

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);
		srb.setKinematic(true);

	}


	@Override
	public void process(float tpf) {
		Camera cam = game.getCamera();

		//camDir.set(cam.getDirection()).multLocal(speed, 0.0f, speed);
		//camLeft.set(cam.getLeft()).multLocal(strafeSpeed);
		//walkDirection.set(0, 0, 0);
		if (left) {
			turnSpeed -= .1f;
		}
		if (right) {
			turnSpeed += .1f;
		}
		if (fwd) {
			double x = Math.cos(anglerads);
			double z = Math.sin(anglerads);
			fwdSpeed.x += x * .2f;
			fwdSpeed.z += z * .2f;
		}
		if (backwards) {
			double x = Math.cos(anglerads);
			double z = Math.sin(anglerads);
			fwdSpeed.x -= x * .2f;
			fwdSpeed.z -= z * .2f;
		}
		if (up) {
			fwdSpeed.y += .05f;
		}
		if (down) {
			fwdSpeed.y -= .05f;
		}

		// Gravity
		fwdSpeed.y -= .005f;

		// Drag
		this.fwdSpeed.multLocal(.999f);

		this.anglerads += turnSpeed * tpf;  // todo - loop

		this.getMainNode().move(fwdSpeed.mult(tpf));

		if (this.getMainNode().getWorldTranslation().y <= 0.1f) {
			this.getMainNode().getLocalTranslation().y = 0.1f;
		}

	}


	@Override
	public void onAction(String binding, boolean isPressed, float tpf) {
		if (binding.equals("Left")) {
			left = isPressed;
		} else if (binding.equals("Right")) {
			right = isPressed;
		} else if (binding.equals("Fwd")) {
			fwd = isPressed;
		} else if (binding.equals("Backwards")) {
			backwards = isPressed;
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
