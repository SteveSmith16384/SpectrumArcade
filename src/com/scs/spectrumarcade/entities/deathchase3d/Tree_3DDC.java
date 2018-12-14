package com.scs.spectrumarcade.entities.deathchase3d;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class Tree_3DDC extends AbstractPhysicalEntity implements ICausesHarmOnContact, IProcessable {

	public Tree_3DDC(SpectrumArcade _game, float x, float z) {
		super(_game, "Tree");

		Box box = new Box(.2f, 2.5f, .2f);
		Geometry geometry = new Geometry("Tree", box);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/floater.png");
		geometry.setLocalTranslation(0,  .5f, 0);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, 0, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);

	}


	@Override
	public float getDamageCaused() {
		return 1;
	}


	@Override
	public void process(float tpfSecs) {
	}


}
