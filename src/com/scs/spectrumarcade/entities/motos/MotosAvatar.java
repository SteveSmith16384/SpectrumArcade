package com.scs.spectrumarcade.entities.motos;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;

public class MotosAvatar extends Avatar {

	private boolean left = false, right = false, up = false, down = false;
	public boolean walking = false;

	public MotosAvatar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "SpectrumArcade _game, String _name");

		Mesh sphere = new Sphere(16, 16, 2, true, false);
		Geometry geometry = new Geometry("MotosPlayerEntitySphere", sphere);
		geometry.setCullHint(CullHint.Always);
		//geometry.setShadowMode(ShadowMode.CastAndReceive);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);
	}


	@Override
	public void process(float tpfSecs) {
		Globals.p("Player: " + this.getMainNode().getWorldTranslation());
		if (!game.isGameOver()) {
			walking = up || down || left || right;
			/*if (left) {
				walkDirection.addLocal(camLeft);
			}
			if (right) {
				walkDirection.addLocal(camLeft.negate());
			}*/
			if (up) {
				this.srb.applyCentralForce(game.getCamera().getDirection().mult(10));
			}
			if (down) {
				this.srb.applyCentralForce(game.getCamera().getDirection().mult(-1));
			}

			if (walking) {
			} else {
				//time_until_next_footstep_sfx = 0;
			}
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
		} else if (binding.equals("Jump")) {
			/*if (canJump) {
				if (isPressed) { 
					jump(); 
				}
			}*/
		}

	}


	@Override
	public void warp(Vector3f vec) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCameraLocation(Camera cam) {
		//cam.setLocation(new Vector3f(10, 5, 10f));
		Vector3f vec = getMainNode().getWorldTranslation();
		cam.setLocation(new Vector3f(vec.x, vec.y, vec.z)); // Drop cam slightly so we're looking out of our eye level - todo - don't create each time

	}

}
