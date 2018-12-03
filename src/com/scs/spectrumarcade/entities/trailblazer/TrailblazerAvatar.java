package com.scs.spectrumarcade.entities.trailblazer;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.MotosLevel;
import com.scs.spectrumarcade.levels.TrailblazerLevel;

public class TrailblazerAvatar extends AbstractPhysicalEntity implements IAvatar {

	private static final float RAD = .4f;
	private static final float FORCE = 5f;
	private static final float JUMP_FORCE = 3f;

	private TrailblazerLevel level;
	private Vector3f camPos = new Vector3f();
	private int lastCheckX, lastCheckZ;

	private boolean left = false, right = false, up = false, down = false, jump = false;

	public TrailblazerAvatar(SpectrumArcade _game, TrailblazerLevel _level, float x, float y, float z, boolean followCam) {
		super(_game, "TrailblazerAvatar");

		level = _level;

		Mesh sphere = new Sphere(32, 32, RAD, true, false);
		Geometry geometry = new Geometry("TrailblazerAvatarEntitySphere", sphere);
		if (!followCam) {
			geometry.setCullHint(CullHint.Always);
		} else {
			JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/antattack.png");
			geometry.setShadowMode(ShadowMode.CastAndReceive);
		}

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
				Vector3f dir = game.getCamera().getLeft();
				this.srb.applyCentralForce(dir.mult(FORCE));
			}
			if (right) {
				Vector3f dir = game.getCamera().getLeft().mult(-1);
				this.srb.applyCentralForce(dir.mult(FORCE));
			}
			if (right) {
				Vector3f dir = game.getCamera().getLeft().mult(-1);
				this.srb.applyCentralForce(dir.mult(FORCE));
			}
			if (jump) {
				Globals.p("Jumping");
				this.srb.applyCentralForce(new Vector3f(0, 150f, 0));
				jump = false;
			}

			Vector3f pos = this.mainNode.getWorldTranslation();
			if (pos.y <= RAD+0.1f) {
				if ((int)pos.x != lastCheckX || (int)pos.z != lastCheckZ) {
					handleSquare(lastCheckX, lastCheckZ);
					lastCheckX = (int)pos.x;
					lastCheckZ = (int)pos.z;
				}
			}
		}

		if (this.getMainNode().getWorldTranslation().y < MotosLevel.FALL_DIST) {
			game.playerKilled();
		}
	}


	public void handleSquare(int x, int z) {
		try {
			//if (x >= 0 && x < MAP_SIZE_X && z >= 0 && z < MAP_SIZE_Z) {
			switch (level.map[x][z]) {
			case 0:
			case TrailblazerLevel.MAP_HOLE:
			case TrailblazerLevel.MAP_WALL:
				// Do nothing
				break;
			case TrailblazerLevel.MAP_SPEED_UP:
				this.srb.applyCentralForce(game.getCamera().getDirection().mult(FORCE*3));
				break;
			case TrailblazerLevel.MAP_SLOW_DOWN:
				this.srb.applyCentralForce(game.getCamera().getDirection().mult(-1).multLocal(FORCE*3));
				break;
			case TrailblazerLevel.MAP_JUMP:
				this.srb.applyCentralForce(new Vector3f(0, JUMP_FORCE*2, 0));
				break;
			case TrailblazerLevel.MAP_NUDGE_LEFT:
				this.srb.applyCentralForce(game.getCamera().getLeft().mult(FORCE));
				break;
			case TrailblazerLevel.MAP_NUDGE_RIGHT:
				this.srb.applyCentralForce(game.getCamera().getLeft().mult(-1).multLocal(FORCE));
				break;
			default:
				Globals.p("Unhandle map square: " + level.map[x][z]);
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			// Do nothing
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
			if (isPressed && !jump) {
				if (this.getMainNode().getWorldTranslation().y <= 1+RAD+0.1f) {
					jump = true;
				}
			}
		}

	}


	@Override
	public void warp(Vector3f vec) {
		this.srb.setPhysicsLocation(vec.clone());
	}


	@Override
	public void setCameraLocation(Camera cam) {
		if (!TrailblazerLevel.FOLLOW_CAM) {
			camPos.set(getMainNode().getWorldTranslation());
			cam.setLocation(camPos);
		}
	}


	@Override
	public void clearForces() {
		srb.clearForces();
		srb.setLinearVelocity(new Vector3f());
	}

}
