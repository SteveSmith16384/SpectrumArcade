package com.scs.spectrumarcade.entities.manicminer;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class Rock extends AbstractPhysicalEntity implements ICausesHarmOnContact {

	public Rock(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Rock");

		float w = 1f;
		float h = 1f;
		float d = 1f;

		Spatial geometry = null;
		geometry = (Geometry)game.getAssetManager().loadModel("Models/RocksFlowersGrassPack/OBJ/rock3.obj");
		JMEAngleFunctions.turnOnXAxis(geometry, (float)Math.PI);
		JMEModelFunctions.scaleModelToWidth(geometry, 1f);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/manicminer/mm_rock.png");
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		JMEModelFunctions.moveYOriginTo(geometry, 0f);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1f);
		mainNode.addControl(srb);
		//srb.setKinematic(true);
		
		
	}


	@Override
	public float getDamageCaused() {
		return 1;
	}


}
