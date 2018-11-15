package com.scs.spectrumarcade.entities.ericandfloaters;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.EricAndTheFloatersLevel;

public class Floater extends AbstractPhysicalEntity implements ICausesHarmOnContact {

	//private Geometry geometry;

	public Floater(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Floater");

		Mesh sphere = new Sphere(8, 8, EricAndTheFloatersLevel.SEGMENT_SIZE - 0.2f, true, false);
		Geometry geometry = new Geometry("DebuggingSphere", sphere);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/yellowsun.jpg");

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(0);
		geometry.addControl(srb);
		game.bulletAppState.getPhysicsSpace().add(srb);
		srb.setKinematic(true);

	}


	@Override
	public void process(float tpfSecs) {
		// todo
	}


	@Override
	public float getDamageCaused() {
		return 1;
	}


}
