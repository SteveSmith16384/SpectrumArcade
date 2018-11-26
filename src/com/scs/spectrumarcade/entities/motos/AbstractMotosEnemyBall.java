package com.scs.spectrumarcade.entities.motos;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.MotosLevel;

public abstract class AbstractMotosEnemyBall extends AbstractPhysicalEntity implements INotifiedOfCollision, IProcessable {

	private Vector3f turnDir = new Vector3f();
	private float force;
	private float turnSpeed;
	private MotosLevel level;
	
	public AbstractMotosEnemyBall(SpectrumArcade _game, MotosLevel _level, String name, float x, float z, float rad, float mass, float _force, float _turnSpeed) {
		super(_game, name);

		force = _force;
		turnSpeed = _turnSpeed;
		level = _level;
		
		Mesh sphere = new Sphere(16, 16, rad, true, false);
		Geometry geometry = new Geometry("AbstractMotosEnemyBall", sphere);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/antattack.png");
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
		//turnTowardsPlayer();

		//Vector3f dir = this.getMainNode().getLocalRotation().getRotationColumn(2);
		//Vector3f forceDir = dir.mult(15);
		//Globals.p("Ant force: " + dir);

		Vector3f dir = game.getCamera().getLocation().subtract(this.getMainNode().getWorldTranslation());
		Vector3f forceDir = dir.mult(force);

		this.srb.applyCentralForce(forceDir);
		
		if (this.getMainNode().getWorldTranslation().y < -10) {
			this.markForRemoval();
			level.checkIfAllBaddiesDead();
		}

	}
	
	/*
	private void turnTowardsPlayer() {
		float leftDist = this.leftNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		float rightDist = this.rightNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		if (leftDist > rightDist) {
			turnDir.set(0, 1, 0).multLocal(turnSpeed); // todo - make param
		} else {
			turnDir.set(0, -1, 0).multLocal(turnSpeed); // todo - make param
		}
		this.srb.applyTorqueImpulse(turnDir);
	}
*/


}
