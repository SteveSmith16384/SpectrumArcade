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

	public AbstractMotosEnemyBall(SpectrumArcade _game, String name, float x, float z, float rad, float mass) {
		super(_game, name);

		Mesh sphere = new Sphere(8, 8, 2, true, false);
		Geometry geometry = new Geometry("MotosPlayerEntitySphere", sphere);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/antattack.png");
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, MotosLevel.SEGMENT_SIZE + rad+0.1f, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(mass);
		mainNode.addControl(srb);
	}


	@Override
	public void notifiedOfCollision(IEntity collidedWith) {
		// Make sound
	}
	

	@Override
	public void process(float tpfSecs) {
		turnTowardsPlayer();		

		Vector3f dir = this.getMainNode().getLocalRotation().getRotationColumn(2);
		Vector3f force = dir.mult(2);
		//Globals.p("Ant force: " + dir);
		this.srb.applyCentralForce(force);
	}
	
	private void turnTowardsPlayer() {
		float leftDist = this.leftNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		float rightDist = this.rightNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		if (leftDist > rightDist) {
			turnDir.set(0, 1, 0).multLocal(1.7f);
		} else {
			turnDir.set(0, -1, 0).multLocal(1.7f);
		}
		this.srb.applyTorqueImpulse(turnDir);
	}




}
