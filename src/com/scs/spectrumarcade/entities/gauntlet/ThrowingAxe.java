package com.scs.spectrumarcade.entities.gauntlet;

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

public class ThrowingAxe extends AbstractPhysicalEntity implements IProcessable, INotifiedOfCollision {

	public static final float SPEED = 10f;

	private long removeTime = System.currentTimeMillis() + 3000;

	public ThrowingAxe(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "ThrowingAxe");

		if (y < .3f) {
			y = .3f;
		}

		Mesh sphere = new Sphere(8, 8, .2f, true, false);
		Geometry geometry = new Geometry("ThrowingAxe", sphere);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/black.png");

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(.1f);
		mainNode.addControl(srb);
		srb.setGravity(new Vector3f());
	}


	@Override
	public void process(float tpfSecs) {
		if (removeTime < System.currentTimeMillis()) {
			this.markForRemoval();
		}
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		if (collidedWith instanceof Ghost_Gauntlet) {
			collidedWith.markForRemoval();
		} else {
			this.markForRemoval();
		}
	}



}
