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

	public static final float SPEED = 1f;

	public Ant(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Ant");

		Node geometry = new AntModel(_game.getAssetManager());
		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		Vector3f dir = JMEAngleFunctions.getRandomDirection_8();
		JMEAngleFunctions.rotateToWorldDirection(this.mainNode, dir);

		srb = new RigidBodyControl(1f);
		mainNode.addControl(srb);
		srb.setAngularDamping(0.8f);
		//srb.setRestitution(.01f);
		//srb.setKinematic(true);

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
		//Globals.p("Ant pos: " + this.getMainNode().getWorldTranslation());
		Vector3f dir = this.getMainNode().getLocalRotation().getRotationColumn(2);

		Vector3f force = dir.mult(10);
		//Globals.p("Ant force: " + dir);
		this.srb.applyCentralForce(force);
	}


	@Override
	public void notifiedOfCollision(IEntity collidedWith) {
		if (collidedWith instanceof FloorOrCeiling == false) {
			Globals.p("Ant collided with " + collidedWith + " and is turning");
			// todo - keep turning same direction for a while
			if (NumberFunctions.rnd(1,  2) == 1) {
				//this.srb.setAngularVelocity(new Vector3f(0, -1, 0).multLocal(2));
				this.srb.applyTorqueImpulse(new Vector3f(0, -1, 0).multLocal(1.5f));
			} else {
				this.srb.applyTorqueImpulse(new Vector3f(0, 1, 0).multLocal(1.5f));
			}
		}		
	}

}
