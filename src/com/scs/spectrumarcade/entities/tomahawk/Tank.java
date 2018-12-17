package com.scs.spectrumarcade.entities.tomahawk;

import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class Tank extends AbstractPhysicalEntity implements IProcessable, INotifiedOfCollision {

	public Tank(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Tank");

		Spatial model = game.getAssetManager().loadModel("Models/AbstractRTSModels/Tank1.obj");
		//model.setLocalScale(4f);
		//JMEModelFunctions.scaleModelToWidth(geometry, 1f);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), model, "Models/AbstractRTSModels/textures/Tank1_diffuse.png");
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

	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		// TODO - turn
		
	}


}
