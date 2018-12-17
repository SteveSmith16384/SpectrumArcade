package com.scs.spectrumarcade.entities.krakatoa;

import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.models.Helicopter;

public class KrakatoaHeliAvatar extends AbstractPhysicalEntity implements IAvatar, INotifiedOfCollision {

	// Our movement speed
	private float turnSpeed = 0;
	private float angleRads = 0;
	private float tiltDiff = 0;
	private Vector3f fwdSpeed = new Vector3f();
	private Helicopter heli;
	private boolean left = false, right = false, fwd = false, backwards = false, up = false, down = false;

	public KrakatoaHeliAvatar(SpectrumArcade _game, float x, float y, float z, String tex) {
		super(_game, "Player");

		/** Create a box to use as our player model */
		Box box1 = new Box(1, 2, 1);
		Geometry playerGeometry = new Geometry("Player", box1);
		playerGeometry.setCullHint(CullHint.Always); // todo
		this.getMainNode().attachChild(playerGeometry);
		
		heli = new Helicopter(game.getAssetManager(), tex);
		this.getMainNode().attachChild(heli);
		
		this.getMainNode().setLocalTranslation(x, y, z);

		BoundingBox bb = (BoundingBox) heli.getWorldBound();
		heli.setLocalTranslation(0, -bb.getYExtent(), 0);
		BoxCollisionShape bcs = new BoxCollisionShape(new Vector3f(bb.getXExtent(), bb.getYExtent(), bb.getZExtent()));
		srb = new RigidBodyControl(bcs);
		mainNode.addControl(srb);
		srb.setKinematic(true);

	}


	@Override
	public void process(float tpfSecs) {
		double x = Math.cos(angleRads);
		double z = Math.sin(angleRads);

		if (left) {
			turnSpeed -= .01f;
		}
		if (right) {
			turnSpeed += .01f;
		}
		if (fwd) {
			if (tiltDiff > -4) {
				tiltDiff -= tpfSecs *.2;
				fwdSpeed.x += x * .2f;
				fwdSpeed.z += z * .2f;
			}
		}
		if (backwards) {
			if (tiltDiff < 4) {
				tiltDiff += tpfSecs * .2f;
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
		if (fwdSpeed.y < 0) {
			fwdSpeed.y = fwdSpeed.y * 1.005f;
		} else {
			//fwdSpeed.y -= .005f;
		}
		fwdSpeed.y -= .005f;

		// Drag
		this.fwdSpeed.multLocal(.999f);
		if (Math.abs(turnSpeed) < 0.01f) {
			turnSpeed = 0;
		}
		turnSpeed = turnSpeed * .999f;
		tiltDiff = tiltDiff - (tiltDiff * .5f * tpfSecs);

		//Move
		this.angleRads += turnSpeed * tpfSecs;
		while (angleRads > Math.PI) {
			this.angleRads -= Math.PI*2;
		}
		while (angleRads < -Math.PI) {
			this.angleRads += Math.PI*2;
		}
		this.getMainNode().move(fwdSpeed.mult(tpfSecs));

		x = Math.cos(angleRads);
		z = Math.sin(angleRads);

		Vector3f lookat = this.getMainNode().getWorldTranslation().clone();
		lookat.x += x;
		lookat.y += tiltDiff;
		lookat.z += z;
		this.getMainNode().lookAt(lookat, Vector3f.UNIT_Y);
		if (!Settings.FREE_CAM) {
			game.getCamera().lookAt(lookat, Vector3f.UNIT_Y);
		}
		// Prevent falling through floor
		/*		if (this.getMainNode().getWorldTranslation().y <= 1.2f) {
			this.getMainNode().getLocalTranslation().y = 1.2f;
		}
		 */
		
		//Globals.p("Pos" + this.getMainNode().getWorldTranslation() + ", ang=" + this.angleRads);

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
		this.getMainNode().setLocalTranslation(v);
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
		fwdSpeed.y = 0;
	}

	
	@Override
	public void setAvatarVisible(boolean b) {
		if (b) {
			heli.setCullHint(CullHint.Never);
		} else {
			heli.setCullHint(CullHint.Always);
		}

	}



}
