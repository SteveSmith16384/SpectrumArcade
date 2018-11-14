package com.scs.spectrumarcade.entities.manicminer;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IPlayerCollectable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.Player;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class Key extends AbstractPhysicalEntity implements IPlayerCollectable {

	private float rotDegrees = 0;
	private Geometry geometry;

	public Key(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "todo");

		geometry = (Geometry)game.getAssetManager().loadModel("Models/key.obj");
		JMEModelFunctions.scaleModelToWidth(geometry, 1f);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/yellowsun.jpg");
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		JMEModelFunctions.moveYOriginTo(geometry, 0f);

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
		/*rotDegrees += (tpfSecs * 0.005f);
		while (rotDegrees > 360) {
			rotDegrees -= 360;
		}*/
		float rads = (float)Math.toRadians((tpfSecs * 0.05f));
		geometry.rotate(0, rads, 0);

	}


	@Override
	public void collected(Player avatar) {
		this.remove();
	}

}
