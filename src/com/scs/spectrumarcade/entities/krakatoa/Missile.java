package com.scs.spectrumarcade.entities.krakatoa;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

import ssmith.lang.NumberFunctions;

public class Missile extends AbstractPhysicalEntity implements ICausesHarmOnContact, PhysicsTickListener, INotifiedOfCollision {

	private Spatial geometry;
	private boolean launched = false;

	public Missile(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Missile");

		geometry = game.getAssetManager().loadModel("Models/cimpletoon/cruise_missile01.blend");
		JMEModelFunctions.scaleModelToWidth(geometry, NumberFunctions.rndFloat(1f, 2f));
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/krakatoa/missile.png");
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		JMEAngleFunctions.rotateToWorldDirection(geometry, new Vector3f(0, 0, -1)); // Point model fwds

		JMEModelFunctions.moveYOriginTo(geometry, 0f);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);
		srb.setGravity(new Vector3f(0, -.001f, 0));
		
	}


	@Override
	public float getDamageCaused() {
		return 1;
	}


	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {

	}


	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		if (!launched) {
			launched = true;
			Vector3f dir = new Vector3f(0, 0, -1);
			dir.normalizeLocal();
			Vector3f force = dir.mult(NumberFunctions.rnd(30,  50));
			srb.setLinearVelocity(force);
			//Globals.p("Force=" + force);
		}

	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		this.markForRemoval();
	}



}
