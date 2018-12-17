package com.scs.spectrumarcade.entities.krakatoa;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class House extends AbstractPhysicalEntity {

	public House(SpectrumArcade _game, float x, float y, float z, boolean rot) {
		super(_game, "House");

		Spatial model = game.getAssetManager().loadModel("Models/Suburban pack Vol.2 by Quaternius/Blends/SimpleHouse.blend");
		//JMEModelFunctions.scaleModelToWidth(geometry, 1f);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), model, "Models/Suburban pack Vol.2 by Quaternius/Blends/Textures/HouseTexture.png");
		model.setShadowMode(ShadowMode.CastAndReceive);

		JMEModelFunctions.moveYOriginTo(model, 0f);
		if (rot) {
			JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(0, 0, -1)); // Point model fwds
		}

		this.mainNode.attachChild(model);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);

	}


}
