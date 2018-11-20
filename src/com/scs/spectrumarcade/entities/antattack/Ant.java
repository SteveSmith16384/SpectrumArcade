package com.scs.spectrumarcade.entities.antattack;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.models.AntModel;

import ssmith.lang.NumberFunctions;

public class Ant extends AbstractPhysicalEntity implements ICausesHarmOnContact, INotifiedOfCollision {

	private static final float SPEED = 1f;
	private static final long TURN_INTERVAL = 2000;

	private long timeUntilNextTurn = 0;
	private Vector3f turnDir;
	private long dontMoveUntil = 0;
	private AntModel geometry;

	public Ant(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Ant");

		geometry = new AntModel(_game.getAssetManager());
		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		Vector3f dir = JMEAngleFunctions.getRandomDirection_8();
		JMEAngleFunctions.rotateToWorldDirection(this.mainNode, dir);

		srb = new RigidBodyControl(1f);
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
		if (this.getMainNode().getWorldTranslation().y < -5) {
			Globals.pe("ANT OFF EDGE");
		}

		if (this.dontMoveUntil < System.currentTimeMillis()) {
			//Globals.p("Ant pos: " + this.getMainNode().getWorldTranslation());
			Vector3f dir = this.getMainNode().getLocalRotation().getRotationColumn(2);
			Vector3f force = dir.mult(5);
			//Globals.p("Ant force: " + dir);
			//this.srb.applyCentralForce(force);
			this.srb.setLinearVelocity(force);
			//geometry.walkAnim();
		} else {
			//geometry.idleAnim();
		}
	}


	@Override
	public void notifiedOfCollision(IEntity collidedWith) {
		if (collidedWith instanceof FloorOrCeiling == false) {
			this.srb.setLinearVelocity(Vector3f.ZERO);
			if (timeUntilNextTurn < System.currentTimeMillis()) {
				timeUntilNextTurn = System.currentTimeMillis() + TURN_INTERVAL;
				if (NumberFunctions.rnd(1,  2) == 1) {
					turnDir = new Vector3f(0, 1, 0).multLocal(0.9f);
				} else {
					turnDir = new Vector3f(0, -1, 0).multLocal(0.9f);
				}
			}
			Globals.p("Ant collided with " + collidedWith + " and is turning");
			this.srb.applyTorqueImpulse(turnDir);
			dontMoveUntil = System.currentTimeMillis() + 1500;
		}		
	}

}
