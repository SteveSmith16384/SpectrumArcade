package com.scs.spectrumarcade.entities.motos;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.ForceData;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.MotosLevel;

public class MotosAvatar extends AbstractPhysicalEntity implements IAvatar, PhysicsTickListener {

	private static final float FORCE = 15f;

	private Vector3f camPos = new Vector3f();
	private boolean left = false, right = false, up = false, down = false, jump = false;
	private Vector3f forceDirFwd = new Vector3f();
	private Vector3f forceDirLeft = new Vector3f();
	private boolean clearForces;

	private Geometry geometry;
	

	public MotosAvatar(SpectrumArcade _game, float x, float y, float z, boolean followCam) {
		super(_game, "MotosAvatar");

		Mesh sphere = new Sphere(16, 16, 1, true, false);

		geometry = new Geometry("MotosPlayerEntitySphere", sphere);
		if (!followCam) {
			geometry.setCullHint(CullHint.Always);
		} else {
			JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/motos/avatar.png");
			geometry.setShadowMode(ShadowMode.CastAndReceive);
		}

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);
		//srb.setRestitution(.5f);
	}


	public Spatial getPhysicsNode() {
		return geometry;
	}


	@Override
	public void process(float tpfSecs) {
		//Globals.p("Player: " + this.getMainNode().getWorldTranslation());

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
		} else if (binding.equals("Fwd")) {
			up = isPressed;
		} else if (binding.equals("Backwards")) {
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
		if (!MotosLevel.FOLLOW_CAM) {
		camPos.set(getMainNode().getWorldTranslation());
		cam.setLocation(camPos);
		}
	}


	@Override
	public void clearForces() {
		srb.clearForces();
		//srb.setLinearVelocity(new Vector3f());
		//game.addForce(this, ForceData.LINEAR_VELOCITY, new Vector3f());
	}


	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {
		
	}


	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		if (this.clearForces) {
			this.clearForces = false;
			srb.clearForces();
			srb.setLinearVelocity(new Vector3f());
		}
		//walking = up || down || left || right;
		forceDirFwd.set(game.getCamera().getDirection());
		//forceDirFwd.y = 0.5f;
		forceDirFwd.normalizeLocal();

		forceDirLeft.set(game.getCamera().getLeft());
		//forceDirLeft.y = 0.2f;
		forceDirLeft.normalizeLocal();

		if (up) {
			this.srb.applyCentralForce(forceDirFwd.mult(FORCE));
			//game.addForce(this, ForceData.CENTRAL_FORCE, game.getCamera().getDirection().mult(FORCE));
		}
		if (down) {
			this.srb.applyCentralForce(forceDirFwd.mult(-FORCE*2));
			//game.addForce(this, ForceData.CENTRAL_FORCE, game.getCamera().getDirection().mult(-FORCE));
		}
		if (left) {
			this.srb.applyCentralForce(forceDirLeft.mult(FORCE));
			//game.addForce(this, ForceData.CENTRAL_FORCE, dir.mult(FORCE));
		}
		if (right) {
			this.srb.applyCentralForce(forceDirLeft.mult(-FORCE));
			//game.addForce(this, ForceData.CENTRAL_FORCE, dir.mult(-FORCE));
		}
		if (jump) {
			Globals.p("Jumping");
			this.srb.applyCentralForce(new Vector3f(0, 250f, 0));
			//game.addForce(this, ForceData.CENTRAL_FORCE, new Vector3f(0, 150f, 0));
			jump = false;
		}
		
	}

}
