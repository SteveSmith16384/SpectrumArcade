package com.scs.spectrumarcade.entities.manicminer;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class PoisonousPlant extends AbstractPhysicalEntity implements ICausesHarmOnContact {

	public PoisonousPlant(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "todo");

		float w = 1f;
		float h = 1f;
		float d = 1f;

		Spatial geometry = null;

		geometry = (Geometry)game.getAssetManager().loadModel("Models/RocksFlowersGrassPack/OBJ/flower_2.obj");
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Models/RocksFlowersGrassPack/OBJ/textures/palette_32x32.png");
		JMEModelFunctions.scaleModelToWidth(geometry, w);
		JMEModelFunctions.moveYOriginTo(geometry, 0f);
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		JMEModelFunctions.moveYOriginTo(geometry, 0f);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1f);
		mainNode.addControl(srb);
		//game.bulletAppState.getPhysicsSpace().add(srb);
		srb.setKinematic(true);

		
	}

	@Override
	public float getDamageCaused() {
		return 1;
	}

	@Override
	public void process(float tpfSecs) {
		// TODO Auto-generated method stub

	}


}
