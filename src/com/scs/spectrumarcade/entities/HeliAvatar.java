package com.scs.spectrumarcade.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;

public class HeliAvatar extends AbstractPhysicalEntity implements IAvatar, INotifiedOfCollision {

	// Our movement speed
	private float turnSpeed = 0;
	private float angleRads = 0;
	private float tiltDiff = 0;
	private Vector3f fwdSpeed = new Vector3f();

	private boolean left = false, right = false, fwd = false, backwards = false, up = false, down = false;

	public HeliAvatar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Player");

		/** Create a box to use as our player model */
		Box box1 = new Box(1, 2, 1);
		Geometry playerGeometry = new Geometry("Player", box1);
		playerGeometry.setCullHint(CullHint.Always); // todo
		this.getMainNode().attachChild(playerGeometry);

		this.getMainNode().setLocalTranslation(x, y, z);

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);
		srb.setKinematic(true);

	}


	@Override
	public void process(float tpf) {
		Camera cam = game.getCamera();

		double x = Math.cos(angleRads);
		double z = Math.sin(angleRads);

		//camDir.set(cam.getDirection()).multLocal(speed, 0.0f, speed);
		//camLeft.set(cam.getLeft()).multLocal(strafeSpeed);
		//walkDirection.set(0, 0, 0);
		if (left) {
			turnSpeed -= .05f;
		}
		if (right) {
			turnSpeed += .05f;
		}
		if (fwd) {
			if (tiltDiff > -4) {
				tiltDiff -= tpf*.5;
				fwdSpeed.x += x * .2f;
				fwdSpeed.z += z * .2f;
			}
		}
		if (backwards) {
			if (tiltDiff < 4) {
				tiltDiff += tpf*.5f;
				fwdSpeed.x -= x * .2f;
				fwdSpeed.z -= z * .2f;
			}
		}
		if (up) {
			fwdSpeed.y += .05f;
		}
		if (down) {
			fwdSpeed.y -= .05f;
		}

		// Gravity
		fwdSpeed.y -= .005f;
		if (fwdSpeed.y < 0) {
			fwdSpeed.y = fwdSpeed.y * 1.005f;
		}

		// Drag
		this.fwdSpeed.multLocal(.99f);
		turnSpeed = turnSpeed * .99f;
		tiltDiff = tiltDiff * .99f;

		//Move
		this.angleRads += turnSpeed * tpf;
		while (angleRads > Math.PI) {
			this.angleRads -= Math.PI*2;
		}
		while (angleRads < -Math.PI) {
			this.angleRads += Math.PI*2;
		}
		this.getMainNode().move(fwdSpeed.mult(tpf));

		x = Math.cos(angleRads);
		z = Math.sin(angleRads);

		Vector3f lookat = this.getMainNode().getWorldTranslation().clone();
		lookat.x += x;
		lookat.y += tiltDiff;
		lookat.z += z;
		this.getMainNode().lookAt(lookat, Vector3f.UNIT_Y);
		game.getCamera().lookAt(lookat, Vector3f.UNIT_Y);

		// Prevent falling through floor
		/*		if (this.getMainNode().getWorldTranslation().y <= 1.2f) {
			this.getMainNode().getLocalTranslation().y = 1.2f;
		}
		 */
		Globals.p("Pos" + this.getMainNode().getWorldTranslation() + ", ang=" + this.angleRads);

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
	}


	@Override
	public void clearForces() {
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		Globals.p("heli collided with " + collidedWith);
		this.getMainNode().getLocalTranslation().y += .01f;

	}


}
