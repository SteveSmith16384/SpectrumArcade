package com.scs.spectrumarcade.entities.motos;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.ForceData;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.MotosLevel;

public abstract class AbstractMotosEnemyBall extends AbstractPhysicalEntity implements INotifiedOfCollision, IProcessable, PhysicsTickListener {

	private float force;
	private MotosLevel level;
	
	public AbstractMotosEnemyBall(SpectrumArcade _game, MotosLevel _level, String name, float x, float z, float rad, float mass, float _force) {
		super(_game, name);

		force = _force;
		level = _level;
		
		Mesh sphere = new Sphere(16, 16, rad, true, false);
		Geometry geometry = new Geometry("AbstractMotosEnemyBall", sphere);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/motos/avatar.png");
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		this.mainNode.attachChild(geometry);
		//mainNode.setLocalTranslation(x, MotosLevel.SEGMENT_SIZE + rad+0.1f, z);
		mainNode.setLocalTranslation(x, 10, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(mass);
		mainNode.addControl(srb);
		
		srb.setFriction(1f);  // todo - make param
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		// Make sound
	}
	

	@Override
	public void process(float tpfSecs) {
		if (this.getMainNode().getWorldTranslation().y < MotosLevel.FALL_DIST) {
			this.markForRemoval();
			level.checkIfAllBaddiesDead();
			return;
		}

	}


	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		Vector3f dir = game.getCamera().getLocation().subtract(this.getMainNode().getWorldTranslation());
		Vector3f forceDir = dir.mult(force);

		this.srb.applyCentralForce(forceDir);
		//game.addForce(this, ForceData.CENTRAL_FORCE, forceDir);
		
		
	}


}
