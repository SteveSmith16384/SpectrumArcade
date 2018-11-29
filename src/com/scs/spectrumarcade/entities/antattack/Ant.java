package com.scs.spectrumarcade.entities.antattack;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.models.AntModel;

import ssmith.lang.NumberFunctions;

public class Ant extends AbstractPhysicalEntity implements ICausesHarmOnContact, INotifiedOfCollision, IProcessable {

	private static final int MODE_TOWARDS_PLAYER = 0;
	private static final int MODE_FWDS = 1;
	private static final int MODE_TURNING = 2;
	private static final int MODE_AWAY_FROM_PLAYER = 3;
	
	private static final float TURN_SPEED = 1f;

	private Vector3f turnDir = new Vector3f();
	private long timeUntilNextMode = 0;
	private int mode = -1;
	public BetterCharacterControl playerControl;

	public Ant(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Ant");

		AntModel geometry = new AntModel(_game.getAssetManager());
		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		Vector3f dir = JMEAngleFunctions.getRandomDirection_8();
		JMEAngleFunctions.rotateToWorldDirection(this.mainNode, dir);

		playerControl = new BetterCharacterControl(Settings.PLAYER_RAD, Settings.PLAYER_HEIGHT, 1f);
		playerControl.setJumpForce(new Vector3f(0, 5f, 0)); 
		playerControl.setGravity(new Vector3f(0, 1f, 0));
		this.getMainNode().addControl(playerControl);

		/*
		srb = new RigidBodyControl(10f);
		mainNode.addControl(srb);
		srb.setAngularDamping(0.8f);
		srb.setFriction(.6f);
		srb.setRestitution(0);
		 */
		geometry.walkAnim();

		this.setMode(MODE_TOWARDS_PLAYER);
	}


	@Override
	public float getDamageCaused() {
		return 1;
	}


	@Override
	public void process(float tpfSecs) {
		//Globals.p("Ant pos: " + this.getMainNode().getWorldTranslation());
		if (this.getMainNode().getWorldTranslation().y < -5) {
			Globals.pe("ANT OFF EDGE");
		}

		if (mode > 0) {
			if (this.timeUntilNextMode < System.currentTimeMillis()) {
				this.setMode(mode-1);
			}
		}

		switch (mode) {
		case MODE_TOWARDS_PLAYER:
			turnTowardsPlayer();
			moveFwds();
			break;
		case MODE_FWDS:
			moveFwds();
			break;
		case MODE_TURNING:
			break;
		case MODE_AWAY_FROM_PLAYER:
			turnAwayFromPlayer();
			break;
		default:
			throw new RuntimeException("Unknown mode: " + mode);
		}
	}


	private void moveFwds() {
		/*
		Vector3f dir = this.getMainNode().getLocalRotation().getRotationColumn(2);
		//dir.y = -.1f;
		Vector3f force = dir.mult(4);
		//Globals.p("Ant force: " + dir);
		this.srb.setLinearVelocity(force); // todo - need this every frame?*/
		Vector3f walkDirection = this.mainNode.getWorldRotation().getRotationColumn(2);
		playerControl.setWalkDirection(walkDirection);

	}


	private void turnTowardsPlayer() {
		float leftDist = this.leftNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		float rightDist = this.rightNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		if (leftDist > rightDist) {
			JMEAngleFunctions.turnSpatialLeft(this.mainNode, TURN_SPEED);
		} else {
			JMEAngleFunctions.turnSpatialLeft(this.mainNode, -TURN_SPEED);
		}
		this.srb.applyTorqueImpulse(turnDir);
	}


	private void turnAwayFromPlayer() {
		float leftDist = this.leftNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		float rightDist = this.rightNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		if (leftDist < rightDist) {
			JMEAngleFunctions.turnSpatialLeft(this.mainNode, TURN_SPEED);
		} else {
			JMEAngleFunctions.turnSpatialLeft(this.mainNode, -TURN_SPEED);
		}
		this.srb.applyTorqueImpulse(turnDir);
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		if (collidedWith instanceof FloorOrCeiling) {
			// Do nothing
		} else if (collidedWith instanceof VoxelTerrainEntity || collidedWith instanceof Ant) {
			this.srb.setLinearVelocity(Vector3f.ZERO);
			/*if (timeUntilNextTurn < System.currentTimeMillis()) {
				timeUntilNextTurn = System.currentTimeMillis() + TURN_INTERVAL;
				if (NumberFunctions.rnd(1,  2) == 1) {*/
			turnDir.set(0, 1, 0).multLocal(2);//7.6f);
			/*} else {
					turnDir = new Vector3f(0, -1, 0).multLocal(2.6f);
				}
			}*/
			Globals.p("Ant collided with " + collidedWith + " and is turning");
			this.srb.applyTorqueImpulse(turnDir);
			this.setMode(MODE_TURNING);
		}
	}


	public void hitByBomb() {
		Globals.p("Ant hit!");
		this.srb.applyTorqueImpulse(new Vector3f(0, 1, 0).multLocal(1f));
		//dontMoveTowardsPlayerUntil = System.currentTimeMillis() + 10000;
		this.setMode(MODE_AWAY_FROM_PLAYER);
	}


	private void setMode(int m) {
		Globals.p("New Ant mode: " + m);
		mode = m;
		switch (mode) {
		case MODE_TOWARDS_PLAYER:
			moveFwds();
			// Do nothing
			break;
		case MODE_FWDS:
			moveFwds();
			this.timeUntilNextMode = System.currentTimeMillis() + 3000;//5000;
			break;
		case MODE_TURNING:
			this.srb.setLinearVelocity(new Vector3f());
			this.timeUntilNextMode = System.currentTimeMillis() + 1000;//1500; 
			break;
		case MODE_AWAY_FROM_PLAYER:
			moveFwds();
			this.timeUntilNextMode = System.currentTimeMillis() + 10000;
			break;
		default:
			throw new RuntimeException("Unknown mode: " + mode);
		}
	}


	@Override
	public void actuallyRemove() {
		super.actuallyRemove();
		if (playerControl != null) {
			this.game.bulletAppState.getPhysicsSpace().remove(this.playerControl);
		}

	}


}
