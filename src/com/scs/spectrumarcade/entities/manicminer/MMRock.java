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

public class MMRock extends AbstractPhysicalEntity implements ICausesHarmOnContact {

	public MMRock(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Rock");

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

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);
		
	}


	@Override
	public float getDamageCaused() {
		return 1;
	}


}
