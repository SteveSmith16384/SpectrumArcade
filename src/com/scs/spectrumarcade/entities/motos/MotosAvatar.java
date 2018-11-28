package com.scs.spectrumarcade.entities.motos;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.abilities.IAbility;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.levels.MotosLevel;

public class MotosAvatar extends AbstractPhysicalEntity implements IAvatar {

	private static final float FORCE = 15f;

	private Vector3f camPos = new Vector3f();
	
	private boolean left = false, right = false, up = false, down = false;

	public MotosAvatar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "MotosAvatar");

		Mesh sphere = new Sphere(16, 16, 1, true, false);
		Geometry geometry = new Geometry("MotosPlayerEntitySphere", sphere);
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
				Vector3f dir = game.getCamera().getDirection();
				dir = JMEAngleFunctions.turnLeft(dir);
				this.srb.applyCentralForce(dir.mult(FORCE));
			}
			if (right) {
				Vector3f dir = game.getCamera().getDirection();
				dir = JMEAngleFunctions.turnRight(dir);
				this.srb.applyCentralForce(dir.mult(FORCE));
			}

		}

		if (this.getMainNode().getWorldTranslation().y < MotosLevel.FALL_DIST) {
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
		this.srb.setPhysicsLocation(vec.clone());
	}
	

	@Override
	public void setCameraLocation(Camera cam) {
		//cam.setLocation(new Vector3f(10, 5, 10f));
		camPos.set(getMainNode().getWorldTranslation());
		cam.setLocation(camPos);

	}
/*

	@Override
	public void setAbility(int num, IAbility a) {
		
	}


	@Override
	public void activateAbility(int num) {
		
	}
*/

	@Override
	public void clearForces() {
		srb.clearForces();
	}

}
