package com.scs.spectrumarcade.entities.krakatoa;

import com.jme3.material.RenderState.BlendMode;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

import ssmith.lang.NumberFunctions;

public class Cloud extends AbstractPhysicalEntity implements IProcessable {

	private Spatial geometry;

	public Cloud(SpectrumArcade _game, float x, float y, float z, float scale) {
		super(_game, "Cloud");

		geometry = game.getAssetManager().loadModel("Models/RocksFlowersGrassPack/OBJ/rock1.obj");
		geometry.setLocalScale(scale);
		//JMEAngleFunctions.turnOnXAxis(geometry, NumberFunctions.rndFloat(0f,  (float)Math.PI));
		//JMEModelFunctions.scaleModelToWidth(geometry, NumberFunctions.rndFloat(1f, 2f));
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/krakatoa/cloud.png", true);
		geometry.setShadowMode(ShadowMode.Off);

		JMEModelFunctions.moveYOriginTo(geometry, 0f);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();
	}


	@Override
	public void process(float tpfSecs) {
		// todo - move cloud, wrap around
		
	}

}
