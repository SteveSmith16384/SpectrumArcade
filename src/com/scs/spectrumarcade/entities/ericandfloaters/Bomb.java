package com.scs.spectrumarcade.entities.ericandfloaters;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class Bomb extends AbstractPhysicalEntity {

	public static final float SPEED = 10f;
	
	private long explodeTime = System.currentTimeMillis() + 3000;
	
	public Bomb(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Bomb");

		Mesh sphere = new Sphere(8, 8, .5f, true, false);
		Geometry geometry = new Geometry("BombSphere", sphere);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/floater.png");

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);

	}


	@Override
	public void process(float tpfSecs) {
		if (explodeTime < System.currentTimeMillis()) {
			// todo
			this.markForRemoval();
		}
	}


}
