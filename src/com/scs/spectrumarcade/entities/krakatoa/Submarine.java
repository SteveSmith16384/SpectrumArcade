package com.scs.spectrumarcade.entities.krakatoa;

import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class Submarine extends AbstractPhysicalEntity implements IProcessable {

	public Submarine(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Submarine");

		Spatial model = game.getAssetManager().loadModel("Models/cimpletoon/submarine.blend");
		//model.setLocalScale(4f);
		//JMEModelFunctions.scaleModelToWidth(geometry, 1f);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), model, "Textures/krakatoa/submarine_yellow.png");
		JMEModelFunctions.moveYOriginTo(model, 0f);
		//JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(1, 0, 0));

		model.setShadowMode(ShadowMode.CastAndReceive);

		this.mainNode.attachChild(model);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

	}

	@Override
	public void process(float tpfSecs) {
		JMEAngleFunctions.moveForwards(this.getMainNode(), tpfSecs * 1f);
		JMEAngleFunctions.turnSpatialLeft(this.getMainNode(), tpfSecs * 1.8f);
		
		// todo - shoot at player?
		
	}


}
