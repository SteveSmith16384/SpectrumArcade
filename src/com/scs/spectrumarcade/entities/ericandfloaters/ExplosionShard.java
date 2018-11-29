package com.scs.spectrumarcade.entities.ericandfloaters;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

import ssmith.lang.NumberFunctions;

public class ExplosionShard extends AbstractPhysicalEntity  implements IProcessable {

	public static final float SPEED = 10f;
	
	private long removalTime = System.currentTimeMillis() + 3000;
	
	public ExplosionShard(SpectrumArcade _game, float x, float y, float z, float size, String tex) {
		super(_game, "ExplosionShard");

		Box box1 = new Box(size, size, size);
		Geometry geometry = new Geometry("BombSphere", box1);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, tex);//);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(.2f);
		mainNode.addControl(srb);
		
		Vector3f dir = new Vector3f(NumberFunctions.rndFloat(-1,  1), 1, NumberFunctions.rndFloat(-1,  1)).normalizeLocal().multLocal(15);
		srb.applyCentralForce(dir);

	}


	@Override
	public void process(float tpfSecs) {
		if (removalTime < System.currentTimeMillis()) {
			this.markForRemoval();
		}
	}


}
