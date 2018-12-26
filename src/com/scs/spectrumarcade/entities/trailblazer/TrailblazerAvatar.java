package com.scs.spectrumarcade.entities.trailblazer;

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
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.MotosLevel;
import com.scs.spectrumarcade.levels.TrailblazerLevel;

public class TrailblazerAvatar extends AbstractPhysicalEntity implements IAvatar, PhysicsTickListener {

	private static final float RAD = .4f;
	private static final float FORCE = 3f;
	//private static final float JUMP_FORCE = 3f;

	private TrailblazerLevel level;
	private int lastCheckX, lastCheckZ;

	private boolean left = false, right = false, up = false, down = false, jump = false;
	private Vector3f forceDirFwd = new Vector3f();
	private Vector3f forceDirLeft = new Vector3f();
	private boolean clearForces;

	private Geometry geometry;
	private Vector3f tmpPos = new Vector3f();
	private Vector3f warpPos;

	public TrailblazerAvatar(SpectrumArcade _game, TrailblazerLevel _level, float x, float y, float z) {
		super(_game, "TrailblazerAvatar");

		level = _level;

		Mesh sphere = new Sphere(16, 16, RAD, true, false);
		geometry = new Geometry("TrailblazerAvatarEntitySphere", sphere);
			JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/trailblazer/avatar.png");
			geometry.setShadowMode(ShadowMode.CastAndReceive);


		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);

		srb = new RigidBodyControl(1);
		geometry.addControl(srb);
	}

	
	public Spatial getPhysicsNode() {
		return geometry;
	}


	@Override
	public void process(float tpfSecs) {
		//this.getMainNode().setLocalTranslation(this.geometry.getWorldTranslation());
		this.getMainNode().setLocalTranslation(tmpPos); // Move main node to physics node
		
		//Globals.p("Player: " + this.getMainNode().getWorldTranslation());
		if (this.getMainNode().getWorldTranslation().y < -2) {
			game.playerKilled("Falling");
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
			if (isPressed && !jump) {
				if (this.getMainNode().getWorldTranslation().y <= 1+RAD+0.1f) {
					jump = true;
				}
			}
		}

	}


	@Override
	public void warp(Vector3f vec) {
		warpPos = vec.clone();
	}


	@Override
	public void clearForces() {
		clearForces = true;
		//srb.clearForces();
		//srb.setLinearVelocity(new Vector3f());
	}


	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {
		tmpPos.set(this.srb.getPhysicsLocation());

	}


	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		if (this.warpPos != null) {
			this.srb.setPhysicsLocation(warpPos);
			warpPos = null;
		}
		
		if (this.clearForces) {
			this.clearForces = false;
			srb.clearForces();
			srb.setLinearVelocity(new Vector3f());
		}
		//walking = up || down || left || right;
		forceDirFwd.set(game.getCamera().getDirection());
		forceDirFwd.y = 0f;
		forceDirFwd.normalizeLocal();

		forceDirLeft.set(game.getCamera().getLeft());
		forceDirLeft.y = 0f;
		forceDirLeft.normalizeLocal();

		if (up) {
			this.srb.applyCentralForce(forceDirFwd.mult(FORCE));
		}
		if (down) {
			this.srb.applyCentralForce(forceDirFwd.mult(-FORCE*2));
		}
		if (left) {
			this.srb.applyCentralForce(forceDirLeft.mult(FORCE*2));
		}
		if (right) {
			this.srb.applyCentralForce(forceDirLeft.mult(-FORCE*2));
		}
		if (jump) {
			Globals.p("Jumping");
			this.srb.applyCentralForce(new Vector3f(0, 250f, 0));
			jump = false;
		}

		Vector3f pos = this.mainNode.getWorldTranslation();
		if (pos.y <= 1+RAD+0.5f) {
			if ((int)pos.x != lastCheckX || (int)pos.z != lastCheckZ) {
				//Globals.p("Checking square " + (int)pos.x + "," + (int)pos.z);
				lastCheckX = (int)pos.x;
				lastCheckZ = (int)pos.z;
				handleSquare(lastCheckX, lastCheckZ);
			}
		}


	}


	private void handleSquare(int x, int z) {
		if (Settings.TEST_BALL_ROLLING) {
			return;
		}
		try {
			if (level.map[x][z] >= 3) {
				//Globals.p("Hitting !" +level.map[x][z]);
				//if (x >= 0 && x < MAP_SIZE_X && z >= 0 && z < MAP_SIZE_Z) {
				switch (level.map[x][z]) {
				/*case 0:
			case TrailblazerLevel.MAP_HOLE:
			case TrailblazerLevel.MAP_WALL:
				// Do nothing
				break;*/
				case TrailblazerLevel.MAP_SPEED_UP:
					Globals.p("Speeding up!");
					this.srb.applyCentralForce(forceDirFwd.mult(200));
					//game.addForce(this, ForceData.CENTRAL_FORCE, game.getCamera().getDirection().mult(FORCE*3));
					break;
				case TrailblazerLevel.MAP_SLOW_DOWN:
					Globals.p("Slow down!");
					this.srb.applyCentralForce(forceDirFwd.mult(-200));
					//game.addForce(this, ForceData.CENTRAL_FORCE, game.getCamera().getDirection().mult(-FORCE*2));
					break;
				case TrailblazerLevel.MAP_JUMP:
					Globals.p("Jump!");
					this.srb.applyCentralForce(new Vector3f(0, 400f, 0));
					//game.addForce(this, ForceData.CENTRAL_FORCE, new Vector3f(0, JUMP_FORCE*2, 0));
					break;
				case TrailblazerLevel.MAP_NUDGE_LEFT:
					Globals.p("Nudge left!");
					this.srb.applyCentralForce(forceDirLeft.mult(100));
					//game.addForce(this, ForceData.CENTRAL_FORCE, game.getCamera().getLeft().mult(FORCE));
					break;
				case TrailblazerLevel.MAP_NUDGE_RIGHT:
					Globals.p("Nudge right!");
					this.srb.applyCentralForce(forceDirLeft.mult(-100));
					//game.addForce(this, ForceData.CENTRAL_FORCE, game.getCamera().getLeft().mult(-FORCE));
					break;
				default:
					Globals.p("Unhandle map square: " + level.map[x][z]);
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			// Do nothing
		}
	}


	@Override
	public void setAvatarVisible(boolean b) {
		if (b) {
			geometry.setCullHint(CullHint.Never);
		} else {
			geometry.setCullHint(CullHint.Always);
		}

	}


	@Override
	public float getCameraHeight() {
		return RAD;
	}


	@Override
	public void showKilledAnim() {
		
	}

}
