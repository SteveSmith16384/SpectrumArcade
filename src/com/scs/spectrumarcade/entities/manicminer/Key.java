package com.scs.spectrumarcade.entities.manicminer;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IPlayerCollectable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class Key extends AbstractPhysicalEntity implements IPlayerCollectable, IProcessable  {

	//private float rotDegrees = 0;
	//private Geometry geometry;

	public Key(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Key");

		Geometry geometry = (Geometry)game.getAssetManager().loadModel("Models/key.obj");
		JMEModelFunctions.scaleModelToWidth(geometry, 1f);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/yellowsun.jpg");
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		JMEModelFunctions.moveYOriginTo(geometry, 0f);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(0);
		this.mainNode.addControl(srb);
		this.getMainNode().addControl(srb);

	}


	@Override
	public void process(float tpfSecs) {
		this.getMainNode().rotate(0, tpfSecs*2, 0);

	}


	@Override
	public void collected(WalkingPlayer avatar) {
		game.keyCollected();
	}

}
