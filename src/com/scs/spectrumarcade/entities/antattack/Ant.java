package com.scs.spectrumarcade.entities.antattack;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.IProcessable;
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

	private static final long TURN_INTERVAL = 2000;

	private long timeUntilNextTurn = 0;
	private Vector3f turnDir;
	private long dontMoveTowardsPlayerUntil = 0;
	private long dontMoveUntil = 0;
	//private boolean goTowardsPlayer = true;
	//private boolean goAwayFromPlayer = true;
	private boolean onFloor = false;

	public Ant(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Ant");

		AntModel geometry = new AntModel(_game.getAssetManager());
		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		Vector3f dir = JMEAngleFunctions.getRandomDirection_8();
		JMEAngleFunctions.rotateToWorldDirection(this.mainNode, dir);

		srb = new RigidBodyControl(100f);
		mainNode.addControl(srb);
		srb.setAngularDamping(0.8f);
		srb.setFriction(.6f);
		srb.setRestitution(0);

		geometry.walkAnim();
	}


	@Override
	public float getDamageCaused() {
		return 1;
	}


	@Override
	public void process(float tpfSecs) {
		//Globals.p("Ant pos: " + this.getMainNode().getWorldTranslation());
		if (this.getMainNode().getWorldTranslation().y < -5) {
			//Globals.pe("ANT OFF EDGE");
		}

		if (this.onFloor) {
			if (this.dontMoveUntil < System.currentTimeMillis()) {
				if (dontMoveTowardsPlayerUntil < System.currentTimeMillis()) {
					turnTowardsPlayer();
				}
				// Walk forwards
				Vector3f dir = this.getMainNode().getLocalRotation().getRotationColumn(2);
				//dir.y = -.1f;
				Vector3f force = dir.mult(2);
				//Globals.p("Ant force: " + dir);
				this.srb.setLinearVelocity(force);
			}
		}
	}


	private void turnTowardsPlayer() {
		float leftDist = this.leftNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		float rightDist = this.rightNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		if (leftDist > rightDist) {
			turnDir = new Vector3f(0, 1, 0).multLocal(1.7f);
		} else {
			turnDir = new Vector3f(0, -1, 0).multLocal(1.7f);
		}
		this.srb.applyTorqueImpulse(turnDir);
	}

	
	@Override
	public void notifiedOfCollision(IEntity collidedWith) {
		onFloor = true;
		if (collidedWith instanceof FloorOrCeiling) {
			// Do nothing
		} else if (collidedWith instanceof VoxelTerrainEntity || collidedWith instanceof Ant) {
			this.srb.setLinearVelocity(Vector3f.ZERO);
			if (timeUntilNextTurn < System.currentTimeMillis()) {
				timeUntilNextTurn = System.currentTimeMillis() + TURN_INTERVAL;
				if (NumberFunctions.rnd(1,  2) == 1) {
					turnDir = new Vector3f(0, 1, 0).multLocal(0.6f);
				} else {
					turnDir = new Vector3f(0, -1, 0).multLocal(0.6f);
				}
			}
			//Globals.p("Ant collided with " + collidedWith + " and is turning");
			this.srb.applyTorqueImpulse(turnDir);
			dontMoveUntil = System.currentTimeMillis() + 1500;
			dontMoveTowardsPlayerUntil = System.currentTimeMillis() + 4000;
		}
	}

	
	public void hitByBomb() {
		Globals.p("Ant hit!");
		this.srb.applyTorqueImpulse(new Vector3f(0, 1, 0).multLocal(1f));
		dontMoveTowardsPlayerUntil = System.currentTimeMillis() + 10000;
	}
}
