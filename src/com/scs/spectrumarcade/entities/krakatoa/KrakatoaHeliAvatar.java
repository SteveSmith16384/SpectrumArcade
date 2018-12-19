package com.scs.spectrumarcade.entities.krakatoa;

import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.models.KrakatoaHelicopter;

public class KrakatoaHeliAvatar extends AbstractPhysicalEntity implements IAvatar, INotifiedOfCollision {

	private float tiltDiff = 0;
	private KrakatoaHelicopter heli;
	private boolean left = false, right = false, fwd = false, backwards = false, up = false, down = false;
	private Vector3f tempAvatarDir = new Vector3f();
	private Vector3f lastSafePos = new Vector3f();
	
	public KrakatoaHeliAvatar(SpectrumArcade _game, float x, float y, float z, String tex) {
		super(_game, "Player");

		/** Create a box to use as our player model */
		/*Box box1 = new Box(1, 2, 1);
		Geometry playerGeometry = new Geometry("Player", box1);
		playerGeometry.setCullHint(CullHint.Always); // todo
		this.getMainNode().attachChild(playerGeometry);
		*/
		
		heli = new KrakatoaHelicopter(game.getAssetManager(), tex);
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
		this.lastSafePos.set(this.getMainNode().getLocalTranslation());
		
		tempAvatarDir.set(game.getCamera().getDirection());
		tempAvatarDir.y = 0;
		//tempAvatarDir.multLocal(-1);
		JMEAngleFunctions.rotateToWorldDirection((Spatial)this.getMainNode(), tempAvatarDir);

		//fwdSpeed.set(0, 0, 0);
/*		
		double x = Math.cos(angleRads);
		double z = Math.sin(angleRads);

		if (left) {
			this.angleRads -= 1f * tpfSecs;
		}
		if (right) {
			this.angleRads += 1f * tpfSecs;
		}
				while (angleRads > Math.PI) {
			this.angleRads -= Math.PI*2;
		}
		while (angleRads < -Math.PI) {
			this.angleRads += Math.PI*2;
		}
 
 */
		if (fwd) {
			if (tiltDiff > -4) {
				tiltDiff -= tpfSecs *.2;
				this.moveFwds(tpfSecs);
				//fwdSpeed.x += x * 10f;
				//fwdSpeed.z += z * 10f;
			}
		}
		if (backwards) {
			if (tiltDiff < 4) {
				tiltDiff += tpfSecs * .2f;
				// todo
				//fwdSpeed.x -= x * 10f;
				//fwdSpeed.z -= z * 10f;
			}
		}
		if (up) {
			//fwdSpeed.y += 5f;
			moveUp(1, tpfSecs);
		}
		if (down) {
			//fwdSpeed.y -= 5f;
			moveUp(-1, tpfSecs);
		}

		// Drag
		tiltDiff = tiltDiff - (tiltDiff * .5f * tpfSecs);

		//Move
		//this.angleRads += turnSpeed * tpfSecs;
		//this.getMainNode().move(fwdSpeed.mult(tpfSecs));

		//x = Math.cos(angleRads);
		//z = Math.sin(angleRads);

		/*
		Vector3f lookat = this.getMainNode().getWorldTranslation().clone();
		lookat.x += x;
		lookat.y += tiltDiff;
		lookat.z += z;
		this.getMainNode().lookAt(lookat, Vector3f.UNIT_Y);
		*/
		
		/*if (!Settings.FREE_CAM) {
			game.getCamera().lookAt(lookat, Vector3f.UNIT_Y);
		}*/

		//Globals.p("Pos" + this.getMainNode().getWorldTranslation() + ", ang=" + this.angleRads);

	}


	private void moveFwds(float tpfSecs) {
		Vector3f dir = this.getMainNode().getLocalRotation().getRotationColumn(2);
		Vector3f force = dir.mult(5 * tpfSecs);
		this.getMainNode().move(force);
	}
	
	
	private void moveUp(float diff, float tpfSecs) {
		//Vector3f dir = this.getMainNode().getLocalRotation().getRotationColumn(2);
		//Vector3f force = dir.mult(1 * tpfSecs);
		this.getMainNode().move(0, diff*tpfSecs*5f, 0);
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
	public void clearForces() {
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		Globals.p("heli collided with " + collidedWith);
		
		this.getMainNode().setLocalTranslation(this.lastSafePos);
		//fwdSpeed.y = 0;
	}

	
	@Override
	public void setAvatarVisible(boolean b) {
		if (b) {
			heli.setCullHint(CullHint.Never);
		} else {
			heli.setCullHint(CullHint.Always);
		}

	}


	@Override
	public float getCameraHeight() {
		return 1f;
	}


	@Override
	public void showKilledAnim() {
		// TODO Auto-generated method stub
		
	}

}
