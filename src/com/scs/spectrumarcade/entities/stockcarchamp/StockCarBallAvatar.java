package com.scs.spectrumarcade.entities.stockcarchamp;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.SpectrumArcade;

public class StockCarBallAvatar extends Avatar {

	private static final float FORCE = 15f;

	private Vector3f camPos = new Vector3f();

	private boolean left = false, right = false, up = false, down = false;

	public StockCarBallAvatar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "StockCarBallAvatar");

		Mesh sphere = new Sphere(16, 16, 1, true, false);
		Geometry geometry = new Geometry("StockCarBallAvatarSphere", sphere);
		geometry.setCullHint(CullHint.Always);
		//geometry.setShadowMode(ShadowMode.CastAndReceive);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);
		//srb.setRestitution(.5f);
	}


	@Override
	public void process(float tpfSecs) {
		//Globals.p("Player: " + this.getMainNode().getWorldTranslation());
		if (!game.isGameOver()) {
			//walking = up || down || left || right;
			if (up) {
				this.srb.applyCentralForce(game.getCamera().getDirection().mult(FORCE));
			}
			if (down) {
				this.srb.applyCentralForce(game.getCamera().getDirection().mult(-FORCE*2));
			}
			if (left) {
				Vector3f turnDir = new Vector3f(0, 1, 0).multLocal(1.7f); // todo - dont create every time
				this.srb.applyTorqueImpulse(turnDir);
			} else if (right) {
				Vector3f turnDir = new Vector3f(0, -1, 0).multLocal(1.7f); // todo - dont create every time
				this.srb.applyTorqueImpulse(turnDir);
			}

		}

		if (this.getMainNode().getWorldTranslation().y < -10) {
			game.playerKilled();
		}
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
	public void warp(Vector3f vec) {
		this.srb.setPhysicsLocation(vec.clone());
	}


	@Override
	public void setCameraLocation(Camera cam) {
		//cam.setLocation(new Vector3f(10, 5, 10f));
		camPos.set(getMainNode().getWorldTranslation());
		cam.setLocation(camPos);

	}

}
