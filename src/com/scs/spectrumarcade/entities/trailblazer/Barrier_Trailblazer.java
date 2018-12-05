package com.scs.spectrumarcade.entities.trailblazer;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class Barrier_Trailblazer extends AbstractPhysicalEntity {

	public Barrier_Trailblazer(SpectrumArcade _game, float x, float z) {
		super(_game, "Barrier_Trailblazer");

		Box box = new Box(.45f, .5f, .45f); // Make slightly smaller to allow space
		Geometry geometry = new Geometry("Barrier_TrailblazerGeom", box);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/blocks/trailblazer_red.png");
		geometry.setLocalTranslation(.5f,  .5f, .5f);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, 1.1f, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1f);
		mainNode.addControl(srb);

	}


}
