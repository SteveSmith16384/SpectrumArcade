package com.scs.spectrumarcade.entities.ericandfloaters;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.EricAndTheFloatersLevel;

public class Floater extends AbstractPhysicalEntity implements ICausesHarmOnContact, INotifiedOfCollision {

	public static final float SPEED = 10f;
	private Vector3f dir;

	public Floater(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Floater");

		Mesh sphere = new Sphere(8, 8, EricAndTheFloatersLevel.SEGMENT_SIZE/3, true, false);
		Geometry geometry = new Geometry("FloaterSphere", sphere);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/yellowsun.jpg");

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);

		dir = JMEAngleFunctions.getRandomDirection_8();
		
	}


	@Override
	public void process(float tpfSecs) {
		//Globals.p("Floater pos: " + this.getMainNode().getWorldTranslation());
		Vector3f force = dir.mult(SPEED);
		//Globals.p("Floater force: " + dir);
		this.srb.applyCentralForce(force);
	}


	@Override
	public float getDamageCaused() {
		return 1;
	}


	@Override
	public void notifiedOfCollision(IEntity collidedWith) {
		if (collidedWith instanceof FloorOrCeiling == false) {
			Globals.p("Floater collided with " + collidedWith + " and is turning");
			// todo - turn the other way as well
			//this.srb.setAngularVelocity(new Vector3f(0, -1, 0).multLocal(2));
			dir = JMEAngleFunctions.getRandomDirection_8();
			//this.srb.applyTorqueImpulse(new Vector3f(0, -1, 0).multLocal(.5f));
		}		
	}


}
