package com.scs.spectrumarcade.entities.krakatoa;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class Tanker extends AbstractPhysicalEntity {

	public Tanker(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Tanker");

		Spatial model = game.getAssetManager().loadModel("Models/cimpletoon/transport.blend");
		//model.setLocalScale(4f);
		model.setLocalScale(8, 4, 4);
		//JMEModelFunctions.scaleModelToWidth(geometry, 1f);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), model, "Textures/krakatoa/tanker.png");
		JMEModelFunctions.moveYOriginTo(model, 0f);

		model.setShadowMode(ShadowMode.CastAndReceive);

		this.mainNode.attachChild(model);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);
		
	}


}
